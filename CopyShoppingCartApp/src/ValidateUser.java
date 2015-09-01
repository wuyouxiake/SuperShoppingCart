import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cartuser;
import postTools.DBUtil;

/**
 * Servlet implementation class ValidateUser
 */
@WebServlet("/ValidateUser")
public class ValidateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ValidateUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String user_name = request.getParameter("username");
		String tempPass = request.getParameter("password");

		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		
		
		String alert = null;

		try {
			TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Cartuser c WHERE c.username = :user_name",Long.class);
			query.setParameter("user_name", user_name);
			long totalUser = query.getSingleResult();
			if (totalUser <= 0) {
				alert = "User does not exist!";
				// Set response content type
				response.setContentType("text/html");

				request.setAttribute("alert", alert);

				getServletContext().getRequestDispatcher("/error.jsp").include(
						request, response);

			} else {
				String qString = "select c from Cartuser c where c.username = :user_name";
				TypedQuery<Cartuser> q = em.createQuery(qString, Cartuser.class);
				q.setParameter("user_name", user_name);
				Cartuser user = new Cartuser();
				user = q.getSingleResult();
				long userid=user.getUserId();
				String password = user.getPassword();
				String usertype=user.getUsertype();
				if (!password.equals(tempPass)) {
					alert = "Password not valid!";
				} else {
					alert = "Logged in";
					request.getSession().setAttribute("userid", userid);
					request.getSession().setAttribute("user_type", usertype);
				}

				// Set response content type
				response.setContentType("text/html");

				request.setAttribute("alert", alert);

				getServletContext().getRequestDispatcher("/successful.jsp")
						.forward(request, response);
			}

		} catch (Exception e) {
			System.out.println("Error!");
		} finally {
			em.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
