package ricardombertani.projetos.allinyourhands.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.apache.log4j.Logger;


  
public class ReportManager {

	// variavel para os logs
	private static Logger log = Logger.getLogger(ReportManager.class.getName());
		
	public static synchronized void saveReportInfo(String name, String type, String artist, int accesedContent, String date){
		   log.debug("\n-->Saving Suggested Content on Cache File...");
		
		   File reportFile = new File(	System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_REPORT_CACHEFILE_PATH));
		  		   
			try{								
				 
				if(!reportFile.exists()){
					reportFile.createNewFile();
				}
				
				OutputStream os = (OutputStream) new FileOutputStream(reportFile,true);
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
				PrintWriter printWriter = new PrintWriter(osw);
				
				printWriter.println("{\"name\" : \""+name+"\", \"type\": \""+type+"\", \"artist\": \""+artist+"\",\"accesedContent\":\""+date+"\" } , ");
				
				printWriter.close();
				osw.close();
				os.close();
				
			}catch(Exception e){
				log.debug("\n--->Exception - Error to read file in saveSuggestedContentOnFile: "+e.getMessage());
				e.printStackTrace();
			}
	}
	
}
