package ricardombertani.projetos.allinyourhands.reports.facade;

import javax.ejb.Remote;


@Remote
public interface AiyhReportsFacadeInterface {

	public void saveReportData(String name, String type, String artist,int accessedContent, String Date);
	
	public int getAccessCountByPeriod(String initialDate, String finalDate,String accessName); 
	
	public int getQueriesCountByPeriod(String initialDate, String finalDate, String type);
}
