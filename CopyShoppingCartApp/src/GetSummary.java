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
@WebServlet("/GetSummary")
public class GetSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSummary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String fullList = "";
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String userid=session.getAttribute("userid").toString();
		
		String qString = "select count(u) from UserProd u where u.userId=?1";
		TypedQuery<Long> q = em.createQuery(qString, Long.class);
		q.setParameter(1, Long.parseLong(userid));
		float subtotal = 0;
		long count=q.getSingleResult();
		
		String qString2 = "select u from UserProd u where u.userId=?1";
		TypedQuery<UserProd> q2 = em.createQuery(qString2, UserProd.class);
		q2.setParameter(1, Long.parseLong(userid));
		List<UserProd> tranList=q2.getResultList();

		for(int i=0;i<count;i++)
        {
			long productid=tranList.get(i).getProdId();
			long tempQty=tranList.get(i).getQuantity();
			String qString3 = "select p from Product p where p.id=?1";
			TypedQuery<Product> q3 = em.createQuery(qString3, Product.class);
			q3.setParameter(1, productid);
			Product p=new Product();
			p=q3.getSingleResult();
			
			
			
			fullList+="<li class=\"list-group-item\"><img src=\""+p.getPhotolink()
            		+"\" style=\"width:120px;height:120px\"> <a href=\"GetProductDetail?id="
            		+p.getId()+"\">"+p.getPName()+"</a><br>  "
            		+"<b>Price: $"+p.getPrice()+"</b><br>"
            		+"Qty: "+tempQty+"<br>"
            		+"Subtotal: $"+tempQty*p.getPrice()*1.00+"<br>"
            		+"Tax: $"+tempQty*p.getPrice()*0.06+"<br>"
            		+"</li>";
            subtotal+=p.getPrice()*tempQty*1.06;
        }
		
		// Set response content type
				response.setContentType("text/html");

				request.setAttribute("fullList", fullList);
				//request.setAttribute("subtotal", subtotal);
				
				getServletContext().getRequestDispatcher("/summary.jsp")
						.forward(request, response);
				fullList = "";
				
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}