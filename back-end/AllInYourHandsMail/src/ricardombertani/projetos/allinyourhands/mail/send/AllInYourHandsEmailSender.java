package ricardombertani.projetos.allinyourhands.mail.send;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;



public class AllInYourHandsEmailSender {

	private static Logger log = Logger.getLogger(AllInYourHandsEmailSender.class.getName());
	
	public static void sendMailMessage(String subject, String message, String destMail){
		    
	         MailSender sender = new MailSender();
	         sender.sendMail(subject,   
	        		 	     message,   	                
	        		 	     destMail
	                );   
	         
	       
		}
	
	/*public static void sendRemenberMailMessage(String subject, String message, String destMail){
		   try {   
	         MailSender sender = new MailSender();
	         sender.sendMail("",   
	        		 message,   	                
	                 destMail
	                 );   
	         log.debug("\n--> Remember Mail was sent! Message [ "+message+" ] and destMail: "+destMail);
	       } catch (Throwable t) { 
	    	  
	    	   log.debug("\n--> Exception during the e-mail sent! Error: "+t.getMessage());
	       } 
		}*/
	
	 /*public static void sendAuthorizationMailMessage(String subject,String message, String destMail){
		   try {   
	         MailSender sender = new MailSender();
	         sender.sendMail("",   
	        		 message,	                
	                 destMail
	                 );   
	         
	         log.debug("\n--> Authorization Mail was sent! Message [ "+message+" ] and destMail: "+destMail);
	       } catch (Throwable t) { 
	    	  
	    	   log.debug("\n--> Exception during the e-mail sent! Error: "+t.getMessage());
	       } 
		}*/
	 
	 public static void sendAcsmFormatExplanationMailMessage(String subject,String message, String destMail){
		   try {   
	         MailSender sender = new MailSender();
	         sender.sendMail("Explanations Acsm file - All in your hands!",   
	        		 message,	                
	                 destMail
	                 );   
	         
	         log.debug("\n--> Explanations Acsm file was sent! Message [ "+message+" ] and destMail: "+destMail);
	       } catch (Throwable t) { 
	    	  
	    	   log.debug("\n--> Exception during the e-mail sent! Error: "+t.getMessage());
	       } 
		}
		
			
}
