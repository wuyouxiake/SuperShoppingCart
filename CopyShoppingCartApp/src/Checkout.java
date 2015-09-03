import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import model.Balance;
import model.Cartcomment;
import model.Myorder;
import model.Payment;
import model.Product;
import model.UserProd;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Checkout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String fullList = "";
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
//Get value from session and request		
		String userid = session.getAttribute("userid").toString();
		List<UserProd> tranList = (List<UserProd>) session.getAttribute("tranList");
		float payable=(float) session.getAttribute("payable");
//List<UserProd> tranList=(List<UserProd>) session.getAttribute("tranList");
		long cardnumber=Long.parseLong(request.getParameter("cardnumber"));
		String shippingaddress=request.getParameter("shippingaddress");
		String billingaddress=request.getParameter("billingaddress");
		Payment pay=new Payment();
//insert payment for the order, copy the value of payment ID to order ID
//payment ID and order ID for the same transaction are the same		
		pay.setCardnumber(cardnumber);
		pay.setShippingaddress(shippingaddress);
		pay.setBillingaddress(billingaddress);
		pay.setAmount(payable);
		PaymentDB.insert(pay);
//get the payment ID(The one that was just created)	and create order	
		String qString = "select max(p.id) from Payment p";
		TypedQuery<Long> q = em.createQuery(qString, Long.class);
		long paymentid = q.getSingleResult();
		System.out.println(paymentid);
		
		long orderid=paymentid;
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date orderdate = new Date();
		
		for(int i=0;i<tranList.size();i++){
			Myorder order = new Myorder();
			order.setOrderid(orderid);
			order.setOrderdate(orderdate);
			order.setUserid(tranList.get(i).getUserId());
			order.setProductid(tranList.get(i).getProdId());
			order.setQuantity(tranList.get(i).getQuantity());
			OrderDB.insert(order);
		}
		String alert="Thanks for shopping with us!";
//Empty cart
		String qString2 = "select u from UserProd u where u.userId=?1";
		TypedQuery<UserProd> q2 = em.createQuery(qString2, UserProd.class);
		q2.setParameter(1, Long.parseLong(userid));
		List<UserProd> deleteList=q2.getResultList();
		for(int i=0;i<deleteList.size();i++){
			UserProd u=new UserProd();
			u=deleteList.get(i);
			TranDB.delete(u);
		}
		
		String alert2="Cart reset!";
//Set balance to 0
		String qString4 = "select b from Balance b where b.userid=?1";
		TypedQuery<Balance> q4 = em.createQuery(qString4, Balance.class);
		q4.setParameter(1, Long.parseLong(userid));
		Balance blc=q4.getSingleResult();
		blc.setBalance(0);
		BalanceDB.update(blc);

		// Set response content type
		response.setContentType("text/html");

		request.setAttribute("alert", alert);
		request.setAttribute("alert2", alert2);

		getServletContext().getRequestDispatcher("/ordersubmitted.jsp").forward(request, response);
		fullList = "";
		request.getSession().removeAttribute("blc");
		request.getSession().removeAttribute("payable");
		request.getSession().removeAttribute("subtotal");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}