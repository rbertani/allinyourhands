package ricardombertani.projetos.allinyourhands.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedCollection;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class ReportsManager {

	// variavel para os logs
		private static Logger log = Logger.getLogger(ReportsManager.class.getName());

		public static synchronized void saveReportData(SuggestedContent currentReportData){
			   	    log.debug("\n-->Saving Report Data on Cache File...");
			
			   		File reportDataCacheFile = new File(	System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_REPORTSDATA_CACHEFILE_PATH));
			  		   												
					List<SuggestedContent> reportDataList = getReportDataList();
									
					if(reportDataList == null){					
						reportDataList = new LinkedList<SuggestedContent>();	
						
					}
					
					reportDataList.add(currentReportData);
						
					// recriando arquivo de cache
					recreateReportDataCacheFile(reportDataList,reportDataCacheFile);	
									
			
		}
		
		/** Quantidade de acessos por tipo de device
		 * 
		 * @param accessName
		 * @return
		 */
		public static int getAccessCount(String accessName){  // MOBILE ACCESS, IPHONE ACCESS, WINDOWSPHONE ACCESS, BROWSER ACCESS
			int totalAccessCount = 0;
			List<SuggestedContent> reportDataList = getReportDataList();
			if( reportDataList != null)
				for(SuggestedContent suggestedContent : reportDataList){
					if( suggestedContent.getExtraInformation().equals("reportPortalWeb") && suggestedContent.getName().equals(accessName) )
						totalAccessCount++;
				}
			
			return totalAccessCount;
			
		}
		
		/** Quantidade de acessos por tipo de device e período
		 * 
		 * @param accessName
		 * @return
		 */
		public static synchronized int getAccessCountByPeriod(String initialDate, String finalDate, String accessName){  // MOBILE ACCESS, IPHONE ACCESS, WINDOWSPHONE ACCESS, BROWSER ACCESS
			
			
			int totalAccessCount = 0;
			List<SuggestedContent> reportDataList = getReportDataList();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			try{
				Date initialDateValue = sdf.parse(initialDate);
				Date finalDateValue = sdf.parse(finalDate);
				if( reportDataList != null)
					for(SuggestedContent suggestedContent : reportDataList){
						Date suggestedContentDate = sdf.parse(suggestedContent.getDate());
						if( suggestedContent.getExtraInformation().equals("reportPortalWeb") && suggestedContent.getName().equals(accessName) && suggestedContentDate.after(initialDateValue) && suggestedContentDate.before(finalDateValue)){
							totalAccessCount++;							
						}
					}
			}catch(ParseException e){
				e.printStackTrace();
			}
			
			return totalAccessCount;
			
		}
		
		/** Quantia de consultas por tipo
		 * 
		 * @param contentType
		 * @return
		 */
		public static int getQueryCount(String contentType){  // musics, music_categories, videos, video_categories, places, books, weather ou "" para total
			int totalQueryCount = 0;
			List<SuggestedContent> reportDataList = getReportDataList();
			if( reportDataList != null)
				for(SuggestedContent suggestedContent : reportDataList){
					if( suggestedContent.getExtraInformation().equals("reportPortalWeb") && suggestedContent.getType().equals(contentType) )
						totalQueryCount++;
					else if(suggestedContent.getExtraInformation().equals("reportPortalWeb") && suggestedContent.getType().equals("")){
						totalQueryCount++;
					}
				}
			
			return totalQueryCount;
			
		}
		
		/** Consultas por tipo
		 * 
		 * @param contentType
		 * @return
		 */
		public static List<String> getQuerysByType(String contentType){  // musics, music_categories, videos, video_categories, places, books, weather
			List<String> queries = new LinkedList<String>();
			List<SuggestedContent> reportDataList = getReportDataList();
			if( reportDataList != null)
				for(SuggestedContent suggestedContent : reportDataList){
					if( suggestedContent.getExtraInformation().equals("reportPortalWeb") && suggestedContent.getType().equals(contentType) )
						queries.add(suggestedContent.getName());
				}
			
			return queries;
			
		}
		
		/** Obtem a quantidade de queries no periodo
		 * 
		 * @param initialDate
		 * @param finalDate
		 * @param type
		 * @return
		 */
		public static int getQueriesCountByPeriod(String initialDate, String finalDate, String type){ // musics, music_categories, videos, video_categories, places, books, weather
			int queriesCount = 0;
		
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			try{
				Date initialDateValue = sdf.parse(initialDate);
				Date finalDateValue = sdf.parse(finalDate);
				List<SuggestedContent> reportDataList = getReportDataList();
				if( reportDataList != null)
					for(SuggestedContent suggestedContent : reportDataList){
						Date suggestedContentDate = sdf.parse(suggestedContent.getDate());
						
						if(  suggestedContentDate.after(initialDateValue) && suggestedContentDate.before(finalDateValue) && suggestedContent.getType().equals(type)){
							queriesCount++;							
						}
					}
			}catch(ParseException e){
				e.printStackTrace();
			}
			
			return queriesCount;
			
		}
		
		/** Obtem as queries no periodo
		 * 
		 * @param initialDate
		 * @param finalDate
		 * @param type
		 * @return
		 */
		public static List<String> getQueriesByPeriod(String initialDate, String finalDate, String type){ // musics, music_categories, videos, video_categories, places, books, weather
			List<String> queries = new LinkedList<String>();
		
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			try{
				Date initialDateValue = sdf.parse(initialDate);
				Date finalDateValue = sdf.parse(finalDate);
				List<SuggestedContent> reportDataList = getReportDataList();
				if( reportDataList != null)
					for(SuggestedContent suggestedContent : reportDataList){
						Date suggestedContentDate = sdf.parse(suggestedContent.getDate());
						
						if(  suggestedContentDate.after(initialDateValue) && suggestedContentDate.before(finalDateValue) ){
							if(suggestedContent.getType().equals(type))
								queries.add(suggestedContent.getName());
							else if(suggestedContent.getType().equals(""))
								queries.add(suggestedContent.getName());
						}
					}
			}catch(ParseException e){
				e.printStackTrace();
			}
			
			return queries;
			
		}
		
		/** Metodo que obtem todas informacoes do relatorio
		 * 	
		 * @return
		 */
		public static List<SuggestedContent> getReportDataList(){
			
			
			File suggestedContentsCacheFile = new File(System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_REPORTSDATA_CACHEFILE_PATH));
			   String message = "";
			  
			   List<SuggestedContent> reportDataList = new LinkedList<SuggestedContent>();
			   
				try{
					BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(suggestedContentsCacheFile), "UTF8"));
				
					// loop que le e imprime todas as linhas do arquivo
					message = myBuffer.readLine();
									
					String aux = ""+message;
					while (message != null) {				
						message = myBuffer.readLine();
						if(message != null){							
							aux += message;
						}
					}
					
					aux += "</suggestedContents>";
					
					myBuffer.close();
					
					reportDataList = readReportDataFromXml(aux);
									
				}catch(IOException e){
					log.debug("\n--->Error to read file in getReportDataList: "+e.getMessage());
					e.printStackTrace();
					return null;
				}catch(Exception e){
					log.debug("\n--->Exception - Error to read file in getReportDataList: "+e.getMessage());
					e.printStackTrace();
					return null;
				}
				
				return reportDataList;
		}
		
				
		private static synchronized void recreateReportDataCacheFile(List<SuggestedContent> suggestedContents, File suggestedContentsCacheFile){
			
			try{
				if(suggestedContentsCacheFile.exists()){
					
					if(suggestedContentsCacheFile.delete()){
						 log.debug("\n-->Reports CacheFile was deleted!");
						 if(suggestedContentsCacheFile.createNewFile()){
							 log.debug("\n-->New Reports CacheFile created!");
						}
					}
					
				}else{
					 if(suggestedContentsCacheFile.createNewFile()){
						 log.debug("\n-->New Reports CacheFile created!");
					}
				}
								
				
				// salvando registros em arquivo
				OutputStream os = (OutputStream) new FileOutputStream(suggestedContentsCacheFile,true);
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
				PrintWriter printWriter = new PrintWriter(osw);
				
				printWriter.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				printWriter.println("<suggestedContents>");				
				for(SuggestedContent suggestedContent : suggestedContents){
					printWriter.println("<suggestedContent><id>" + suggestedContent.getId()
												+ "</id><image>"+substituteScapeXMLCharacters(suggestedContent.getImage())+"</image><name>" + substituteScapeXMLCharacters(suggestedContent.getName())
												+ "</name><type>"
												+ suggestedContent.getType()
												+ "</type><artist>"
												+ substituteScapeXMLCharacters(suggestedContent.getArtist()) + "</artist><count>"
												+ suggestedContent.getCount() + "</count><extraInformation>"+substituteScapeXMLCharacters(suggestedContent.getExtraInformation())+"</extraInformation><date>"+suggestedContent.getDate()+"</date></suggestedContent>");
					
				}
				printWriter.close();
				osw.close();
				os.close();
			
			}catch(IOException e){
				e.printStackTrace();
				
				log.debug("\n--->Error in recreateSuggestedContentsCacheFile() method!!");
			}
			
		}
		
		//public int quantitySearchQueries
		
		private static XStream xstreamInstance(){
			return new XStream(new DomDriver("UTF-8")){
				 @Override
		        protected MapperWrapper wrapMapper(MapperWrapper next) {
		            return new MapperWrapper(next) {
		                @Override
		                public boolean shouldSerializeMember(Class definedIn, String fieldName) {
		                 if (definedIn == Object.class) {
		                 try {
		                 return this.realClass(fieldName) != null;
		                 } catch(Exception e) {
		                 return false;
		                 }
		                 } else {
		                        return super.shouldSerializeMember(definedIn, fieldName);
		                    }
		                }
		            };
		        }
			};
		}
		
		private static List<SuggestedContent> readReportDataFromXml(String xml){
			
			log.debug("\n--->readReportDataFromXml() called");
			XStream xStream = xstreamInstance();
			
			xStream.alias("suggestedContents", SuggestedCollection.class);
			xStream.alias("suggestedContent", SuggestedContent.class);
			xStream.addImplicitCollection(SuggestedCollection.class, "suggestedContents");
			
			return ((SuggestedCollection)xStream.fromXML(xml)).getSuggestedContents();
		}
		
		private static String substituteScapeXMLCharacters(String in) {
				 
			  return in.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "");

		}  
		
		

}
