package ricardombertani.projetos.allinyourhands.reports.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ricardombertani.projetos.allinyourhands.reports.dao.AccessRegisterInterfaceDAO;


@Stateless 
public class AiyhReportsFacade implements AiyhReportsFacadeInterface{

	@EJB
	AccessRegisterInterfaceDAO accessRegisterInterfaceDAO;

	@Override
	public void saveReportData(String name, String type, String artist,
			 int accessedContent, String Date) {
		accessRegisterInterfaceDAO.saveReportData(name, type, artist, accessedContent, Date);
	}

	@Override
	public int getAccessCountByPeriod(String initialDate, String finalDate,
			String accessName) {
		
		return accessRegisterInterfaceDAO.getAccessCountByPeriod(initialDate, finalDate, accessName);
		
	}

	@Override
	public int getQueriesCountByPeriod(String initialDate, String finalDate,
			String type) {
		
		return accessRegisterInterfaceDAO.getQueriesCountByPeriod(initialDate, finalDate, type);
				
	}
	
	
}
