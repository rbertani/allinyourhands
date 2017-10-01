package ricardombertani.projetos.allinyourhands.admin.locator;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

import ricardombertani.projetos.allinyourhands.core.facade.CoreFacadeInterface;
import ricardombertani.projetos.allinyourhands.core.util.AllInYourHandsConstants;
import ricardombertani.projetos.allinyourhands.reports.facade.AiyhReportsFacadeInterface;


public class ServiceLocator {


	private static ServiceLocator INSTANCE = new ServiceLocator();
	private static Logger log = Logger.getLogger(ServiceLocator.class.getName());
	   
	private ServiceLocator() {                     
				       		         
	}   
	     
	public static ServiceLocator getInstance() {
		return INSTANCE;
	}    
	
	public AiyhReportsFacadeInterface  getReportsFacade() {
		AiyhReportsFacadeInterface ejbRef = null;
		try {
			InitialContext context = new InitialContext();
			ejbRef = (AiyhReportsFacadeInterface) context.lookup("AiyhReportsFacade/remote");
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		return ejbRef;
	}
	
	public CoreFacadeInterface  getCoreFacade() {
		
		CoreFacadeInterface ejbRef = null;
		try {
			InitialContext context = new InitialContext();					
			ejbRef = (CoreFacadeInterface) context.lookup("CoreFacade/remote");
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		return ejbRef;
	}
	
	}
