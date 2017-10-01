package ricardombertani.projetos.allinyourhands.mail.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ricardombertani.projetos.allinyourhands.mail.send.AllInYourHandsEmailSender;

public class AiyhMailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(AiyhMailServlet.class.getName());

	public AiyhMailServlet(){
		super();
	}
	
	 protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
     SQLException, ClassNotFoundException, InstantiationException,
         IllegalAccessException {

		response.setContentType("text/xml;charset=UTF-8");  
		
		AllInYourHandsEmailSender.sendMailMessage(request.getParameter("subject"), request.getParameter("message"), request.getParameter("destMail"));
		
	 }
	 
	 @Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			try {
				processRequest(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			try {
				processRequest(req, resp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

}
