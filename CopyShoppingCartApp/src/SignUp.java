import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cartuser;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user_name = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String photolink = request.getParameter("photolink");
		String usertype = request.getParameter("usertype");
		Cartuser tempUser=new Cartuser();
		
		tempUser.setUsername(user_name);
		tempUser.setPassword(password);
		tempUser.setEmail(email);
		tempUser.setPhotolink(photolink);
		tempUser.setUsertype(usertype);
		
		UserDB.insert(tempUser);
		
		String alert="User created!";
		request.setAttribute("alert", alert);
		
		getServletContext().getRequestDispatcher("/successful.jsp")
		.forward(request, response);
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
