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
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddToCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String userid;
		String productid = request.getParameter("productid");
		long qty = Long.parseLong(request.getParameter("quantity"));
		
		if(session.getAttribute("userid")==null){
			response.setContentType("text/html");
			String alert = "Please log in";
			request.setAttribute("alert", alert);
			getServletContext().getRequestDispatcher("/error.jsp")
			.include(request, response);
		}else{
			userid=session.getAttribute("userid").toString();
			String qString = "select count(u) from UserProd u where u.userId=?1 and u.prodId=?2";
			TypedQuery<Long> q = em.createQuery(qString, Long.class);
			q.setParameter(1, Long.parseLong(userid));
			q.setParameter(2, Long.parseLong(productid));
			
			UserProd tran=new UserProd();
			
			if(q.getSingleResult()==0){
				tran.setProdId(Long.parseLong(productid));
				tran.setUserId(Long.parseLong(userid));
				tran.setQuantity(qty);
				TranDB.insert(tran);
				String alert = "Added to cart!";
				request.setAttribute("alert", alert);
				getServletContext().getRequestDispatcher("/successful.jsp")
						.forward(request, response);
			}else{
				String qString2= "select u from UserProd u where u.userId=?1 and u.prodId=?2";
				TypedQuery<UserProd> q2 = em.createQuery(qString2, UserProd.class);
				q2.setParameter(1, Long.parseLong(userid));
				q2.setParameter(2, Long.parseLong(productid));
				tran=q2.getSingleResult();
				tran.setQuantity(tran.getQuantity()+qty);
				TranDB.update(tran);
				String alert = "Added to cart!";
				request.setAttribute("alert", alert);
				getServletContext().getRequestDispatcher("/successful.jsp")
						.forward(request, response);
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