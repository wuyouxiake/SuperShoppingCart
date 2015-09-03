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
import model.Balance;
import model.Product;
import model.UserProd;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/SendCredit")
public class SendCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendCredit() {
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
		String username=request.getParameter("username");
		double credit=Double.parseDouble(request.getParameter("credit"));
		String qString = "select count(u) from Cartuser u where u.username=?1";
		TypedQuery<Long> q = em.createQuery(qString, Long.class);
		q.setParameter(1, username);
		if(q.getSingleResult()==0){
			String alert="User not exist.";
			response.setContentType("text/html");
			request.setAttribute("alert", alert);
			getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
		}else{
			String qString3 = "select c.userId from Cartuser c where c.username=?1";
			TypedQuery<Long> q3 = em.createQuery(qString3, Long.class);
			q3.setParameter(1, username);
			long userid=q3.getSingleResult();
			
			String qString2 = "select count(b) from Balance b where b.userid=?1";
			TypedQuery<Long> q2 = em.createQuery(qString2, Long.class);
			q2.setParameter(1, userid);
			if(q2.getSingleResult()==0){
				Balance blc = new Balance();
				blc.setUserid(userid);
				blc.setBalance(credit);
				BalanceDB.insert(blc);
				String alert="Credit is being sent to "+username;
				response.setContentType("text/html");
				request.setAttribute("alert", alert);
				getServletContext().getRequestDispatcher("/successful.jsp").forward(request, response);
			}else{
				String qString4 = "select b from Balance b where b.userid=?1";
				TypedQuery<Balance> q4 = em.createQuery(qString4, Balance.class);
				q4.setParameter(1, userid);
				Balance blc=q4.getSingleResult();
				blc.setBalance(blc.getBalance()+credit);
				BalanceDB.update(blc);
				String alert="Credit is being sent to "+username;
				response.setContentType("text/html");
				request.setAttribute("alert", alert);
				getServletContext().getRequestDispatcher("/successful.jsp").forward(request, response);
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