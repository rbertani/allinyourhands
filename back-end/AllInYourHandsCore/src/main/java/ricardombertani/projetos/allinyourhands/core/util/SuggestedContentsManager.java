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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedCollection;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;

public class SuggestedContentsManager {

	// variavel para os logs
	private static Logger log = Logger.getLogger(SuggestedContentsManager.class.getName());

	public static synchronized void saveSuggestedContent(SuggestedContent currentSuggestedContent){
		   
		   log.debug("\n-->Saving Suggested Content on Cache File...");
		
		     		   
		   if( isValidXmlContent(currentSuggestedContent) ){ // verify if this xml content is valid
		   
			    log.debug("\n--->THIS CONTENT IS VALID, IT WILL BE CONSIDERED AND SAVED!");
			   			   
			    File suggestedContentsCacheFile = new File(	System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_SUGGESTED_CONTENTS_CACHEFILE_PATH));
			  		   
				try{								
					List<SuggestedContent> suggestedContents = getSuggestedContents();
					boolean addElement = true;
											
				 
					for(SuggestedContent suggestedContent : suggestedContents){
							if( (currentSuggestedContent.getId().equals(suggestedContent.getId())) && (currentSuggestedContent.getType().equals(suggestedContent.getType()))){
								int incrementValue = 1;
								if(suggestedContent.getAccessedContent() == 1){
									incrementValue = 3;
								}							
								suggestedContent.setCount( suggestedContent.getCount() + incrementValue ); // incrementando o contador do elemento na lista
								
								addElement = false;
								break;
							}else if( (currentSuggestedContent.getName().equals(suggestedContent.getName())) && (currentSuggestedContent.getType().equals(suggestedContent.getType())) ){
								
								int incrementValue = 1;
								if(suggestedContent.getAccessedContent() == 1){
									incrementValue = 3;
								}		
								suggestedContent.setCount( suggestedContent.getCount() + incrementValue ); // incrementando o contador do elemento na lista
								
								addElement = false;
								break;
							}
					}
					 
					
					if(addElement){
						log.debug("\n-->Adding new Suggested Content!");
						currentSuggestedContent.setCount(1);
						suggestedContents.add(currentSuggestedContent);
					}
					
					
										
					
					// recriando arquivo de cache
					recreateSuggestedContentsCacheFile(suggestedContents,suggestedContentsCacheFile);	
									
				}catch(Exception e){
					log.debug("\n--->Exception - Error to read file in saveSuggestedContentOnFile: "+e.getMessage());
					e.printStackTrace();
				}
				
		   }else{			   
			   log.debug("\n--->INVALID XML CONTENT, IT WILL BE DISCARTED!!");
		   }
		   
		   
	}
	
	private static boolean isValidXmlContent(SuggestedContent suggestedContent){
		// verify if the content is valid (verify if a xml is valid with this content )
		   String testXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		   testXml += "<suggestedContents>";
		   testXml += "<suggestedContent><id>" + suggestedContent.getId()
			+ "</id><image>"+substituteScapeXMLCharacters(suggestedContent.getImage())+"</image><name>" + substituteScapeXMLCharacters(suggestedContent.getName())
			+ "</name><type>"
			+ suggestedContent.getType()
			+ "</type><artist>"
			+ substituteScapeXMLCharacters(suggestedContent.getArtist()) + "</artist><count>"
			+ suggestedContent.getCount() + "</count><extraInformation>"+substituteScapeXMLCharacters(suggestedContent.getExtraInformation())+"</extraInformation><date>"+suggestedContent.getDate()+"</date></suggestedContent>";
		   testXml += "</suggestedContents>";
		   
		   boolean isValidContent = true;
		   try{ 
			   readSuggestedContents(testXml);
		   }catch(Exception e){
			   isValidContent = false;
		   }
		   
		   return isValidContent;
	}
		
	public static List<SuggestedContent> getSuggestedContents(){
		   File suggestedContentsCacheFile = new File(	System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_SUGGESTED_CONTENTS_CACHEFILE_PATH));
		   String message = "";
		  
		   List<SuggestedContent> suggestedContents = new LinkedList<SuggestedContent>();
		   
			try{
				BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(suggestedContentsCacheFile), "UTF8"));
				// loop que le e imprime todas as linhas do arquivo
				message = myBuffer.readLine();
								
				String aux = ""+message;
				while (message != null) {				
					message = myBuffer.readLine();
					if(message != null)
						aux += message;
				}
				
				aux += "</suggestedContents>";
								
				myBuffer.close();
				
				suggestedContents = readSuggestedContents(aux);
				
				if(suggestedContents != null){
					   List<SuggestedContent> validSuggestedContents = new LinkedList<SuggestedContent>();
					  	 	 
					   Collections.sort(suggestedContents,new SuggestedContentsComparator());
					   
					   for(SuggestedContent suggestedContent : suggestedContents){
						   
						   if(suggestedContent.getArtist().length() > 40){
							   suggestedContent.setArtist( suggestedContent.getArtist().substring(0,40)+"..." );
						   }						  
						  				
						   validSuggestedContents.add(suggestedContent);
						   
						   
						   
					   }
					   log.debug("\n-->SUGGEST CONTENTS WAS ORDENED!! LIST SIZE: "+validSuggestedContents.size());
					  
				   }
								
			}catch(IOException e){
				log.debug("\n--->Error to read file in getSuggestedContents: "+e.getMessage());
				e.printStackTrace();
				 
			}catch(Exception e){
				log.debug("\n--->Exception - Error to read file in getSuggestedContents: "+e.getMessage());
				e.printStackTrace();
				 
			}
			
			return suggestedContents;
	}
	
	public static synchronized void updateSuggestedContents() {
		
		 log.debug("\n---> UPDATE SUGGESTEDCONTENTS METHOD called!!");
		 
		 File suggestedContentsCacheFile = new File(System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_SUGGESTED_CONTENTS_CACHEFILE_PATH) );
		  
		 List<SuggestedContent> newSuggestedContents = new LinkedList<SuggestedContent>();
		 
		  try{								
				List<SuggestedContent> suggestedContents = getSuggestedContents();
				
				if(suggestedContents != null){
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
					for(SuggestedContent suggestedContent : suggestedContents){
						
						// obtendo a data de alguns dias atras
						Calendar c = Calendar.getInstance();
						c.setTime(new Date());
						c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 4 );
						
						Date suggestedContentDate = sdf.parse(suggestedContent.getDate());
						if(  suggestedContentDate.after(c.getTime())  ){ // conteudos recomendados mais recentes do que N dias sao mantidos
																					
							newSuggestedContents.add(suggestedContent);
							
						}else{
							log.debug("\n-->This content is not valid: "+suggestedContent.getName()+" [ "+suggestedContent.getType()+" ]!  ");
							// se nao existir quantidade suficiente deste tipo de conteudo na lista ele é mantido
							if( !areThereEnoughNumberThisSuggestedContentIntoList(suggestedContent.getType(),suggestedContents) ){
								log.debug(" - But there isn't enough count of this type, so this will be maintained!");
								newSuggestedContents.add(suggestedContent);
							}else{
								log.debug(" - it will be removed!");
							}
						}
					}
							
					if(newSuggestedContents.size() > 5){
						log.debug("\n-->OK, there are enought new Suggested Contents, recreating file...");
						// recriando arquivo de cache
						recreateSuggestedContentsCacheFile(newSuggestedContents,suggestedContentsCacheFile);
					}else{
						log.debug("\n-->OK, there aren't enought new Suggested Contents, the suggested contents will be maintained!");
					}
					
				}
				
			}catch(Exception e){
				log.debug("\n--->Exception - Error to read file in deleteSuggestedContents: "+e.getMessage());
				e.printStackTrace();
				
			}
	}
	
	public static boolean areThereEnoughNumberThisSuggestedContentIntoList(String contentType, List<SuggestedContent> suggestedContents){
		
		int suggestedContentsMinimumNumber = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_SUGGESTED_CONTENTS_MINIMUM_COUNTPERTYPE));
		int suggestedContentsCount = 0;
		
		for(SuggestedContent suggestedContent : suggestedContents){
			if(suggestedContent.getType().equals(contentType)){
				suggestedContentsCount++;
			}
		}
		
		if(suggestedContentsCount > suggestedContentsMinimumNumber){
			return true;
		}else return false;
		
	}
	
	public static synchronized void deleteSuggestedContents(List<String> suggestedContentsIDs){
		
		 File suggestedContentsCacheFile = new File(System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_SUGGESTED_CONTENTS_CACHEFILE_PATH) );
		  
		 List<SuggestedContent> newSuggestedContents = new LinkedList<SuggestedContent>();
		 
		  try{								
				List<SuggestedContent> suggestedContents = getSuggestedContents();
				
				if(suggestedContents != null){
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
					for(SuggestedContent suggestedContent : suggestedContents){
																		
						
						if( !suggestedContentsIDs.contains(suggestedContent.getId())   ){ // conteudos recomendados com ids diferentes do presente na lista de delecao sao mantidos
																					
							newSuggestedContents.add(suggestedContent);
							
						}
					}
					
					// recriando arquivo
					recreateSuggestedContentsCacheFile(newSuggestedContents,suggestedContentsCacheFile);
							
										
				}
				
			}catch(Exception e){
				log.debug("\n--->Exception - Error to read file in deleteSuggestedContents: "+e.getMessage());
				e.printStackTrace();
				
			}
	}
	
	public static synchronized void recreateSuggestedContentsCacheFile(List<SuggestedContent> suggestedContents, File suggestedContentsCacheFile){
		
		try{
			if(suggestedContentsCacheFile.exists()){
				
				if(suggestedContentsCacheFile.delete()){
					 log.debug("\n-->Suggested Contents CacheFile was deleted!");
					 if(suggestedContentsCacheFile.createNewFile()){
						 log.debug("\n-->New Suggested Contents CacheFile created!");
					}
				}
				
			}else{
				 if(suggestedContentsCacheFile.createNewFile()){
					 log.debug("\n-->New Suggested Contents CacheFile created!");
				}
			}
							
			
			// salvando registros em arquivo
			OutputStream os = (OutputStream) new FileOutputStream(suggestedContentsCacheFile,true);
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
			PrintWriter printWriter = new PrintWriter(osw);
			
			printWriter.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			printWriter.println("<suggestedContents>");		
			
			if(suggestedContents == null){
				suggestedContents = new LinkedList<SuggestedContent>();
			}
			
			for(SuggestedContent suggestedContent : suggestedContents){
				
				if(  !(""+suggestedContent.getName()).equals("null") && !(""+suggestedContent.getType()).equals("null") && !(""+suggestedContent.getArtist()).equals("null") && !(""+suggestedContent.getExtraInformation()).equals("null")){
				
					  if( !AiyhForbiddenTermsController.containsImproperTerms(substituteScapeXMLCharacters(suggestedContent.getName())) ){
						printWriter.println("<suggestedContent><id>" + suggestedContent.getId()
													+ "</id><image>"+substituteScapeXMLCharacters(suggestedContent.getImage())+"</image><name>" + substituteScapeXMLCharacters(suggestedContent.getName())
													+ "</name><type>"
													+ suggestedContent.getType()
													+ "</type><artist>"
													+ substituteScapeXMLCharacters(suggestedContent.getArtist()) + "</artist><count>"
													+ suggestedContent.getCount() + "</count><extraInformation>"+substituteScapeXMLCharacters(suggestedContent.getExtraInformation())+"</extraInformation><date>"+suggestedContent.getDate()+"</date></suggestedContent>");
						
					  }else{
						  log.debug("\n-->Forbidden Content found! It will be removed [ "+substituteScapeXMLCharacters(suggestedContent.getName()) +" ]");
					  }
				 }
			
			}
			printWriter.close();
			osw.close();
			os.close();
			
		
		}catch(IOException e){
			e.printStackTrace();
			
			log.debug("\n--->Error in recreateSuggestedContentsCacheFile() method!!");
		}
		
	}
	
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
	
	private static List<SuggestedContent> readSuggestedContents(String xml) throws Exception{
		XStream xStream = xstreamInstance();
		
		xStream.alias("suggestedContents", SuggestedCollection.class);
		xStream.alias("suggestedContent", SuggestedContent.class);
		xStream.addImplicitCollection(SuggestedCollection.class, "suggestedContents");
		
		return ((SuggestedCollection)xStream.fromXML(xml)).getSuggestedContents();
	}
	
	private static String substituteScapeXMLCharacters(String in) {
			 
		  return in.replaceAll("&", "&amp;").replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "").replaceAll("'", "");

	}  
		

}
