import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import postTools.DBUtil;
import model.Cartcomment;
import model.Product;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/GetProductDetail")
public class GetProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProductDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long productid = Long.parseLong(request.getParameter("id"));
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
//		
		String qString = "select p from Product p where p.id = ?1";
		TypedQuery<Product> q = em.createQuery(qString, Product.class);
		q.setParameter(1, productid);
		List<Product> productDetail;
		try{
			productDetail=q.getResultList();
			if(productDetail ==null ||productDetail.isEmpty()){
				productDetail=null;
			}
			
		String fullList = "";
		for(int i=0;i<productDetail.size();i++)
        {
			fullList="<li class=\"list-group-item\">Product Name: "+productDetail.get(i).getPName()+"</li>"
					+"<li class=\"list-group-item\">Photo: <img src=\""+productDetail.get(i).getPhotolink()+
					"\" style=\"width:120px;height:120px\"></li>"
					+"<li class=\"list-group-item\">Description: "+productDetail.get(i).getDescription()+"</li>"
					+"<li class=\"list-group-item\">Price: $"+productDetail.get(i).getPrice()
					+"</li><br><br>";
            
        }
		

		String qString2 = "select c from Cartcomment c where c.productid = ?1";
		TypedQuery<Cartcomment> q2 = em.createQuery(qString2, Cartcomment.class);
		q2.setParameter(1, productid);
		List<Cartcomment> commentList = q2.getResultList();
		
		

		//Set response content type
				response.setContentType("text/html");

				request.setAttribute("fullList", fullList);
				request.setAttribute("productid", productid);
				request.setAttribute("commentList", commentList);
				getServletContext().getRequestDispatcher("/profile.jsp")
						.forward(request, response);
				fullList = "";
				productDetail.clear();
		}catch(Exception e){
		}finally{
			em.close();
		}
	}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}