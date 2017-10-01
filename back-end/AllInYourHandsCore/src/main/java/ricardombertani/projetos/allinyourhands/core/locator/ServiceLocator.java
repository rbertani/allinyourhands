package ricardombertani.projetos.allinyourhands.core.locator;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ricardombertani.projetos.allinyourhands.core.facade.CoreFacadeInterface;
import ricardombertani.projetos.allinyourhands.core.util.AllInYourHandsConstants;
import ricardombertani.projetos.allinyourhands.reports.facade.AiyhReportsFacadeInterface;



public class ServiceLocator {                                                                                                          
	private static ServiceLocator INSTANCE = new ServiceLocator();
	   
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
	
	public CoreFacadeInterface  getRemoteFacade() {
		
		CoreFacadeInterface ejbRef = null;
		try {
			 Properties properties = new Properties();
	         properties.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	         properties.put("java.naming.factory.url.pkgs", "org.jboss.naming rg.jnp.interfaces");
	         properties.put("java.naming.provider.url", System.getProperty(AllInYourHandsConstants.PROPERTY_OTHERSERVER_REMOTECORE_IP));
					
			InitialContext context;			
			context = new InitialContext(properties);
					
			ejbRef = (CoreFacadeInterface) context.lookup("CoreFacade/remote");
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		return ejbRef;
	}
}
