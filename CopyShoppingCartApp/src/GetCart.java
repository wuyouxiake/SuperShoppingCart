import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import model.Product;
import model.UserProd;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/GetCart")
public class GetCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		long userid = 0;
		long count;
		String alert;
		if (session.getAttribute("userid") == null) {
			response.setContentType("text/html");
			alert = "Please log in";
			request.setAttribute("alert", alert);
			getServletContext().getRequestDispatcher("/error.jsp").include(
					request, response);
		} else {
			userid = (long) session.getAttribute("userid");
			String fullList = "";
			EntityManager em = DBUtil.getEmFactory().createEntityManager();
//get the number of transaction			
			TypedQuery<Long> query1 = em.createQuery("SELECT count(u) FROM UserProd u WHERE u.userId = :userid",Long.class);
			query1.setParameter("userid", userid);
			count= query1.getSingleResult();
			
			
			
			TypedQuery<UserProd> query2 = em.createQuery("SELECT u FROM UserProd u WHERE u.userId = :userid",UserProd.class);
			query2.setParameter("userid", userid);
			List<UserProd> tranList;
			try{
				tranList=query2.getResultList();
				if(tranList ==null ||tranList.isEmpty()){
					tranList=null;
					alert = "No items added!";
					// Set response content type
					response.setContentType("text/html");

					request.setAttribute("alert", alert);

					getServletContext().getRequestDispatcher("/error.jsp").include(
							request, response);
					alert = "";
				}else{
					for (int i = 0; i < count; i++) {
						long productid = tranList.get(i).getProdId();
						TypedQuery<Product> query3 = em.createQuery("SELECT p FROM Product p WHERE p.id = :productid",Product.class);
						query3.setParameter("productid", productid);
						Product p = query3.getSingleResult();
						
					
						
						fullList += "<li class=\"list-group-item\"><img src=\""
							+ p.getPhotolink()
							+ "\" style=\"width:120px;height:120px\"> <a href=\"GetProductDetail?p_name="
							+ p.getPName().replace(" ", "%20")
							+ "\">" + p.getPName() + "</a><br>  "
							+ "<b>Price: $" + p.getPrice()
							+ "</b><br>" + "Qty: "+tranList.get(i).getQuantity()
							+ "<br>" + "</li>";
					}
					fullList += "<br><input type=\"submit\" name=\"submit\" Value=\"Summary\">";

					// Set response content type
					response.setContentType("text/html");

					request.setAttribute("fullList", fullList);

					getServletContext().getRequestDispatcher("/cartForm.jsp")
						.forward(request, response);
					fullList = "";
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
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