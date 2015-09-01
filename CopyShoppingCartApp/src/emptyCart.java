import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet("/emptyCart")
public class emptyCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public emptyCart() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		String userid=session.getAttribute("userid").toString();
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		
		
		
		String qString = "select u from UserProd u where u.userId=?1";
		TypedQuery<UserProd> q = em.createQuery(qString, UserProd.class);
		q.setParameter(1, Long.parseLong(userid));
		List<UserProd> deleteList=q.getResultList();
		for(int i=0;i<deleteList.size();i++){
			UserProd u=new UserProd();
			u=deleteList.get(i);
			TranDB.delete(u);
		}
		
		String alert="Cart reset!";
		request.setAttribute("alert", alert);

		getServletContext().getRequestDispatcher("/successful.jsp").forward(
				request, response);
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