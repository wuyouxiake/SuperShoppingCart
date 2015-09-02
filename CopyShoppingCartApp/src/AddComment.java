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
import model.Cartcomment;
import model.Product;
import model.UserProd;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComment() {
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
		if (session.getAttribute("userid") == null) {
			response.setContentType("text/html");
			String alert = "Please log in";
			request.setAttribute("alert", alert);
			getServletContext().getRequestDispatcher("/error.jsp").include(
					request, response);
		} else{
			long userid = Long.parseLong(request.getParameter("userid"));
			long productid = Long.parseLong(request.getParameter("productid"));
			String review = request.getParameter("review");
			long star = Long.parseLong(request.getParameter("star"));

			Cartcomment myReview = new Cartcomment();
			myReview.setProductid(productid);
			myReview.setUserid(userid);
			myReview.setReview(review);
			myReview.setStar(star);
			CommentDB.insert(myReview);

			String alert = "Comment Added!";
			request.setAttribute("alert", alert);
			getServletContext().getRequestDispatcher("/successful.jsp")
					.forward(request, response);
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