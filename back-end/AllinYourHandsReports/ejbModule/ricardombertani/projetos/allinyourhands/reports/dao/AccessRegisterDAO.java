package ricardombertani.projetos.allinyourhands.reports.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import ricardombertani.projetos.allinyourhands.reports.entity.AccessRegister;

@Stateless
public class AccessRegisterDAO implements AccessRegisterInterfaceDAO {

	private static Logger log = Logger.getLogger(AccessRegisterDAO.class);

	@PersistenceContext(unitName = "reports-module")
	EntityManager em;

	@Override
	public void saveReportData(String name, String type, String artist, int accessedContent, String date) {
		
		AccessRegister accessRegister = new AccessRegister();
		accessRegister.setName(name);
		accessRegister.setArtist(artist);
				
		accessRegister.setExtraInformation(accessedContent);
				
		accessRegister.setType(type);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
		Date currentDate = new Date();
		try {
			currentDate = sdf.parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		accessRegister.setDate(currentDate);
		
		try {

			em.persist(accessRegister);
			em.flush();
			log.debug("\n--> AccessRegister was saved with Success! Details [ Name: "+name+", Artist: "+artist+", AccessedContent: "+accessedContent+", Date: "+date+" ]");
		} catch (Throwable e) {
			log.debug("\n-- >Error while Persist AccessRegister into saveReportData() method!!");			
			e.printStackTrace();
		}

	}

	@Override
	public int getAccessCount(String accessName) {
		
		Number result = 0;
		 try {
				
				Query q = em.createQuery("select count(accessRegister) from AccessRegister accessRegister where accessRegister.name = :accessName and accessRegister.extraInformation = 0");
				q.setParameter("accessName", accessName);
				
				result = (Number) q.getSingleResult();
				log.debug("\n--> getAccessCount Result: "+result);
		 } catch (Exception e) {
				log.debug("\n--> Error in query of getAccessCount in AccessRegisterDAO!!");
				e.printStackTrace();
		 }
		 
		 return result.intValue();
	}

	@Override
	public int getAccessCountByPeriod(String initialDate, String finalDate, String accessName) {
		Number result = 0;
		 try {
			 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			 	Date initialDateValue = sdf.parse(initialDate);
				Date finalDateValue = sdf.parse(finalDate);
				Query q = null;
				
				q = em.createQuery("select count(accessRegister) from AccessRegister accessRegister where accessRegister.name = :accessName and accessRegister.date >= :initialDateValue and accessRegister.date <= :finalDateValue");
			
			
				q.setParameter("accessName", accessName);
				q.setParameter("initialDateValue", initialDateValue);
				q.setParameter("finalDateValue", finalDateValue);
				
				result = (Number) q.getSingleResult();
				log.debug("\n--> getAccessCountByPeriod Result: "+result);

		 } catch (Exception e) {
				log.debug("\n-- >Error in query of getAccessCountByPeriod in AccessRegisterDAO!!");
				e.printStackTrace();
		 }
		 
		 return result.intValue();
	}

	@Override
	public int getQueryCount(String contentType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getQuerysByType(String contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getQueriesCountByPeriod(String initialDate, String finalDate,String type) {
		Number result = 0;
		 try {
			 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			 	Date initialDateValue = sdf.parse(initialDate);
				Date finalDateValue = sdf.parse(finalDate);
				Query q = null;
				
				q = em.createQuery("select count(accessRegister) from AccessRegister accessRegister where accessRegister.type = :type and accessRegister.date >= :initialDateValue and accessRegister.date <= :finalDateValue");
				
				q.setParameter("type", type);
				q.setParameter("initialDateValue", initialDateValue);
				q.setParameter("finalDateValue", finalDateValue);
				
				result = (Number) q.getSingleResult();
				log.debug("\n--> getQueriesCountByPeriod Result: "+result);
				
		 } catch (Exception e) {
				log.debug("\n-- >Error in query of getQueriesCountByPeriod in AccessRegisterDAO!!");
				e.printStackTrace();
		 }
		 
		 return result.intValue();
	}

	@Override
	public List<String> getQueriesByPeriod(String initialDate,
			String finalDate, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
