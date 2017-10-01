package ricardombertani.projetos.suggestedcontentsupdater.locator;


import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import ricardombertani.projetos.allinyourhands.core.facade.CoreFacadeInterface;
import ricardombertani.projetos.suggestedcontentsupdater.util.AiyhSuggestedContentsUpdaterConstants;


public class ServiceLocator {                                                                                                          
	private static ServiceLocator INSTANCE = new ServiceLocator();
	   
	private ServiceLocator() {                     
				       		         
	}   
	     
	public static ServiceLocator getInstance() {
		return INSTANCE;
	}    
	 
	public CoreFacadeInterface  getFacade() {
		CoreFacadeInterface ejbRef = null;
		try {
			InitialContext context = new InitialContext();
			//ejbRef = (CoreFacadeInterface) context.lookup("CoreFacade/remote");
			ejbRef = (CoreFacadeInterface) context.lookup("java:global/AllInYourHandsCore-1.0/CoreFacade!ricardombertani.projetos.allinyourhands.core.facade.CoreFacadeInterface");
			
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		
		return ejbRef;
	}
}