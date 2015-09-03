import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import postTools.DBUtil;
import model.Myorder;
import model.Payment;
import model.Product;
import model.UserProd;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/GetMyOrder")
public class GetMyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetMyOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		long userid=(long) session.getAttribute("userid");
		long count=0;
		String alert;
		String fullList = null;
		double subtotal=0;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
//get the number of orders.			
					TypedQuery<Long> query = em.createQuery("SELECT count( distinct m.orderid) FROM Myorder m WHERE m.userid = ?1",Long.class);
					query.setParameter(1, userid);
					count= query.getSingleResult();
					if(count==0){
						alert="You don't have any order.";
						// Set response content type
						response.setContentType("text/html");

						request.setAttribute("alert", alert);

						getServletContext().getRequestDispatcher("/error.jsp").include(
								request, response);
						alert = "";
					}else{
						//select unique order number of this user into a list. 
						List<Long> orderidList;
						TypedQuery<Long> query2 = em.createQuery("SELECT DISTINCT m.orderid FROM Myorder m WHERE m.userid = ?1",Long.class);
						query2.setParameter(1, userid);
						orderidList=query2.getResultList();
				//Go over the order ID list, get Order ID, and print order. 
						for(int i=0;i<orderidList.size();i++){
							long orderid=orderidList.get(i);
							TypedQuery<Myorder> tempQ = em.createQuery("SELECT m FROM Myorder m WHERE m.userid = ?1 and m.orderid=?2",Myorder.class);
							tempQ.setParameter(1, userid);
							tempQ.setParameter(2, orderid);
							List<Myorder> thisOrder=tempQ.getResultList();
							for(int j=0;j<thisOrder.size();j++){
								TypedQuery<String> tempQ2 = em.createQuery("SELECT p.pName FROM Product p WHERE p.id = ?1",String.class);
								tempQ2.setParameter(1, thisOrder.get(j).getProductid());
								String thisProductName=tempQ2.getSingleResult();
								
								TypedQuery<Long> tempQ4 = em.createQuery("SELECT p.price FROM Product p WHERE p.id = ?1",long.class);
								tempQ4.setParameter(1, thisOrder.get(j).getProductid());
								long thisProductPrice=tempQ4.getSingleResult();
								subtotal+= thisProductPrice*thisOrder.get(j).getQuantity()*1.06;
								double tax=0.06*thisProductPrice*thisOrder.get(j).getQuantity();
								fullList+="<li class=\"list-group-item\">"
										+thisProductName+"<br>"
										+"Price: "+thisProductPrice+"<br>"
										+"Qty: "+thisOrder.get(j).getQuantity()+"<br>"
										+"Sub-total: $"+thisProductPrice*thisOrder.get(j).getQuantity()+"<br>"
										+"Tax: $"+tax+"<br>"
										+"</li>";
										
							}
							Date thisOrderDate = thisOrder.get(thisOrder.size()-1).getOrderdate();
							TypedQuery<Payment> tempQ3 = em.createQuery("SELECT p FROM Payment p WHERE p.id = ?1",Payment.class);
							tempQ3.setParameter(1, orderid);
							Payment pay=tempQ3.getSingleResult();
							
							fullList+="<li class=\"list-group-item\">"
									+"<b>Card# "+pay.getCardnumber()+"</b><br>"
									+"<b>Shipping Address: </b><br>"
									+pay.getShippingaddress()+"<br>"
									+"<b>Billing Address: </b><br>"
									+pay.getBillingaddress()+"<br>"
									+"<b>Order Date: </b><br>"
									+thisOrderDate+"<br>"
									+"<b>Order Total: </b><br>"
									+"$"+subtotal
									+"</li><br>";
									
						}
					}
					// Set response content type
					response.setContentType("text/html");

					request.setAttribute("fullList", fullList);
					request.setAttribute("count", count);

					getServletContext().getRequestDispatcher("/myOrder.jsp").include(
							request, response);
					fullList = null;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}