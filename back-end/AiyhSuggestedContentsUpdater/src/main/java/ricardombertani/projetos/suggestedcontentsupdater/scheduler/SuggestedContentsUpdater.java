package ricardombertani.projetos.suggestedcontentsupdater.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;
import ricardombertani.projetos.suggestedcontentsupdater.locator.ServiceLocator;
import ricardombertani.projetos.suggestedcontentsupdater.util.AiyhSuggestedContentsUpdaterConstants;
  

//@ManagedBean
@Stateless(name="AutomaticSchedulerBean")
public class SuggestedContentsUpdater implements Serializable {

	private static Logger log = Logger.getLogger(SuggestedContentsUpdater.class.getName());

	
	
	@Schedule(hour="*", minute="*/30", persistent = false)
	public void saveNewContents() {
		
		log.debug("\n-->[SCHEDULER] saveNewContents() method CALLED!!!");
		double initialTime = System.currentTimeMillis();
				
		File suggestedContentsTempFile = new File(System.getProperty(AiyhSuggestedContentsUpdaterConstants.PROPERTY_UPDATER_SUGGESTED_CONTENTS_FILETEMP_PATH));
		if(suggestedContentsTempFile.exists()){			
			try{
				
				int moduleSenderEnabled = Integer.parseInt(System.getProperty(AiyhSuggestedContentsUpdaterConstants.PROPERTY_UPDATER_SUGGESTED_CONTENTS_MODULE_SENDER_ENABLED));
				
				if(moduleSenderEnabled == 1){
					
					FileInputStream fstream = new FileInputStream(suggestedContentsTempFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		
					String strLine;
					String allLines = "";
					
					//Read File Line By Line
					while ((strLine = br.readLine()) != null){
																	
						allLines += strLine;
						
					}
					//Close the input stream
					br.close();
					
					
					String separatedLined[] = allLines.split("¬");
					
					for(int i=0; i< separatedLined.length; i++)
						saveInfosOnFile(separatedLined[i]);
		
					
					suggestedContentsTempFile.delete();
					log.debug("\n-->SuggestedContent Temp File was deleted!!");
					 
					
				}else{
					log.debug("\n--> SuggestedContentsSender is DISABLED! No Actions...");
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			log.debug("\n-->SuggestedContent Temp File doesn't exist...");
		}
		

		log.debug("\n-->[SCHEDULER] saveNewContents() took  "+((System.currentTimeMillis() - initialTime)/1000)+" seconds to execute");

		
	}
	
	@Schedule(hour="*", minute="*/35", persistent = false)
	public void updateContents() {

		double initialTime = System.currentTimeMillis();
		log.debug("\n-->[SCHEDULER] updateContents() method CALLED!!!");

		int moduleUpdaterEnabled = Integer.parseInt(System.getProperty(AiyhSuggestedContentsUpdaterConstants.PROPERTY_UPDATER_SUGGESTED_CONTENTS_MODULE_UPDATER_ENABLED));
		
		if(moduleUpdaterEnabled == 1){
			// Atualizando a lista de conteudos sugeridos 
			ServiceLocator.getInstance().getFacade().updateSuggestedContents();
				
			List<SuggestedContent> suggestedContents = null;
			try {
				suggestedContents = ServiceLocator.getInstance().getFacade().getSuggestedContents();
				
				File suggestedContentsLocalCacheFile = new File(System.getProperty(AiyhSuggestedContentsUpdaterConstants.PROPERTY_UPDATER_SUGGESTED_CONTENTS_CACHEFILE_PATH));
				ServiceLocator.getInstance().getFacade().recreateSuggestedContentsCacheFile(suggestedContents,suggestedContentsLocalCacheFile );
				log.debug("\n-->The recommendation list was updated !!!");
			} catch (Exception e) {
					e.printStackTrace();
			}
			
			
			log.debug("\n-->[SCHEDULER] updateContents() method took "+((System.currentTimeMillis() - initialTime)/1000)+" seconds to execute");
		}else{
			log.debug("\n--> SuggestedContentsUpdater is DISABLED! No Actions...");
		}
	}
	
	
	private synchronized void saveInfosOnFile(String messageText){
   		
		   String[] fields = messageText.split("\\|");
		 
		   
		   if(fields.length == 8){
			   
			   SuggestedContent currentSuggestedContent = new SuggestedContent();
			   currentSuggestedContent.setId(fields[0]);
			   currentSuggestedContent.setImage(fields[1]);
			   currentSuggestedContent.setName(fields[2]);
			   currentSuggestedContent.setType(fields[3]);
			   currentSuggestedContent.setArtist(fields[4]);
			   currentSuggestedContent.setExtraInformation(fields[5]);
			   currentSuggestedContent.setDate(fields[6]);
			   currentSuggestedContent.setAccessedContent(Integer.parseInt( fields[7].replaceAll("}","")  ));
			   
			    if(currentSuggestedContent.getExtraInformation().equals("reportPortalWeb") || currentSuggestedContent.getExtraInformation().equals("reportMobile")){ // Relatorio
			    	//ReportsManager.saveReportData(currentSuggestedContent);
			    	//ServiceLocator.getInstance().getReportsFacade().saveReportData(currentSuggestedContent.getName(), currentSuggestedContent.getType(), currentSuggestedContent.getArtist(), currentSuggestedContent.getAccessedContent(), currentSuggestedContent.getDate());
					
			    	ServiceLocator.getInstance().getFacade().saveReportInformation(currentSuggestedContent.getName(), currentSuggestedContent.getType(), currentSuggestedContent.getArtist(), currentSuggestedContent.getAccessedContent(), currentSuggestedContent.getDate());
			    	
			    }else{ // Item recomendado
			    	
			    	ServiceLocator.getInstance().getFacade().saveContentInformation(currentSuggestedContent); //SuggestedContentsManager.saveSuggestedContent(currentSuggestedContent);
					     
				} 
			    
		    
		   }else{
			   log.debug("\n--> Access Register has a insufficient number of fields ( < 8 ) in saveInfosOnFile() method !!");
		   }
		   
		   
		 		   
		   
	}

}
