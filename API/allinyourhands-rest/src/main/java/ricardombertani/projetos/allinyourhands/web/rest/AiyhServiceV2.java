package ricardombertani.projetos.allinyourhands.web.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;
import ricardombertani.projetos.allinyourhands.web.locator.ServiceLocator;
import ricardombertani.projetos.allinyourhands.web.util.EncodingFixer;

@Path("/v2")
public class AiyhServiceV2 {

	
		// variavel para os logs
		private static Logger log = Logger.getLogger(AiyhService.class.getName());
		
		
			
		@GET                 // CORRIGIDA
		@Path("/statusapis")  
		@Produces("text/json")
		public String getStatusAPIs(@QueryParam("isportalweb") Boolean isAccessByPortalWeb){	 
			
			if(isAccessByPortalWeb == null){
				isAccessByPortalWeb = true;
			}
			
			String test = ServiceLocator.getInstance().getFacade().getStatusAPIs(isAccessByPortalWeb);	
			JSONObject xmlJSONObj = XML.toJSONObject(  test  );
			return xmlJSONObj.toString();
					
		}
		
		@GET              // CORRIGIDA
		@Path("/songs")
		@Produces("text/json")
		public String getAudioByQuery(@QueryParam("q") String query, @QueryParam("pagenumber") Integer songPagNumber, @QueryParam("lyric") String lyricIndicator, @QueryParam("grooveshark") Boolean groovesharkEnabled){
					
			if(query == null){
				query = "";
			}
			
			if(songPagNumber == null){
				songPagNumber = 1;
			}
			
			if(lyricIndicator == null){
				lyricIndicator = "0";
			}
			
			if(groovesharkEnabled == null){
				groovesharkEnabled = false;
			}
			
			JSONObject xmlJSONObj = XML.toJSONObject( EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getAudioByQuery(query, songPagNumber, lyricIndicator, groovesharkEnabled))  );
			return xmlJSONObj.toString();	
			
		}
		
		@GET              // INALTERADA
		@Path("/category/songs")
		@Produces("text/json")
		public String getSongCategories(){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().getSongCategories()  );
			return xmlJSONObj.toString();
			
		}
		
		@GET              // CORRIGIDA
		@Path("/category/artists")
		@Produces("text/json")
		public String getArtistsByCategory(@QueryParam("categoryname") String audiocatNam, @QueryParam("pagenumber") Integer artistsPagNumber, @QueryParam("isportalweb") Boolean isWebVersion){
			
			if(audiocatNam == null){
				audiocatNam = "";
			}
			
			if(artistsPagNumber == null){ 
				artistsPagNumber = 1;
			}
			
			if(isWebVersion == null){
				isWebVersion = true;
			}
						
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getArtistsByCategory(audiocatNam, artistsPagNumber, isWebVersion))  );
			return xmlJSONObj.toString();
			
		}
		
		@GET             // CORRIGIDA
		@Path("/artist/songs/")
		@Produces("text/json")
		public String getSongsByArtist(@QueryParam("q") String query, @QueryParam("pagenumber") Integer songByArtPagNumber, @QueryParam("lyric") String lyricIndicator){
		
			if(query == null){
				query = "";
			}
			
			if(songByArtPagNumber == null){
				songByArtPagNumber = 1;
			}
			
			if(lyricIndicator == null){
				lyricIndicator = "0";
			}
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getSongsByArtist(query, songByArtPagNumber, lyricIndicator, true))  );
			return xmlJSONObj.toString();
			
		}
		
		/*@GET             // CORRIGIDA
		@Path("/streaming/")
		@Produces("text/json")
		public String getStreamingURL(@QueryParam("songid") String streamSongId, @QueryParam("ipadress") String streamIpAdress, @QueryParam("sessionid") String streamSessionID){
			
			JSONObject xmlJSONObj = XML.toJSONObject( "<streaming_url>" + ServiceLocator.getInstance().getFacade().getStreamingURL(streamSongId, streamIpAdress, streamSessionID) + "</streaming_url>" );
			return xmlJSONObj.toString();
			
		}
		
			
		
		@PUT            // CORRIGIDA
		@Path("/streaming30sec")	
		public String markStreaming30sec(@QueryParam("streamsessionid") String streamSessionID, @QueryParam("streamkey") String streamKey, @QueryParam("streamserverid") String streamServerID ){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().markStreaming30sec(streamSessionID, streamKey, streamServerID)  );
			return xmlJSONObj.toString();
			
		}
		
		@PUT              // CORRIGIDA
		@Path("/streamingfinish")
		public String markStreamingFinish(@QueryParam("streamsessionid") String streamSessionID, @QueryParam("songid") String streamSongId, @QueryParam("streamkey") String streamKey,@QueryParam("streamserverid") String streamServerID){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().markStreamingFinish(streamSessionID, streamSongId, streamKey, streamServerID)  );
			return xmlJSONObj.toString();
			
		}*/
		
		@GET               // CORRIGIDA
		@Path("/videos")
		@Produces("text/json")
		public String getVideos(@QueryParam("q") String query, @QueryParam("categoryid") String vcatId/*, countryCode*/,@QueryParam("nextpagetoken") String vNextPgToken){
			
			if(query == null){
				query = "";
			}
			
			if(vcatId == null){
				vcatId = "";
			}
			
			if(vNextPgToken == null){
				vNextPgToken = "";
			}
			
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getVideos(query, vcatId, vNextPgToken))  );
			return xmlJSONObj.toString();
			
		}
		
				
		@GET                 // CORRIGIDA
		@Path("/category/videos")
		@Produces("text/json")
		public String getVideoCategories(@QueryParam("countrycode") String countryCode){
			
			if(countryCode == null){
				countryCode = "";
			}
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getVideoCategories(countryCode))  );
			return xmlJSONObj.toString();
			
		}
		
		
		@GET               // CORRIGIDA
		@Path("/books")
		@Produces("text/json")
		public String getBooksV2(@QueryParam("keyword") String query, @QueryParam("pagenumber") String bstartIndex, @QueryParam("countryCode") String countryCode){
			
			if(query == null){
				query = "";
			}
			
			
			if(bstartIndex == null){
				bstartIndex = "";;
			}
			
			
			if(countryCode == null){
				countryCode = "";
			}
			
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getBooks(query, bstartIndex,countryCode))  );
			return xmlJSONObj.toString();
			
		}
		
		@GET                // CORRIGIDA     
		@Path("/latitude_longitude")
		@Produces("text/json")
		public String getGeocodingAddress(@QueryParam("address") String address){
			
			if(address == null){
				address = "";
			}
			
			JSONObject xmlJSONObj = XML.toJSONObject( "<lat_long>" + ServiceLocator.getInstance().getFacade().getGeocodingAddress(address) + "</lat_long>");
			return xmlJSONObj.toString();
			
		}
		
		@GET                 // INALTERADA
		@Path("/countries")
		@Produces("text/json")
		public String getGeonames(){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().getGeonames()  );
			return xmlJSONObj.toString();
			
		}
		 
		@GET               // CORRIGIDA     
		@Path("/states")
		@Produces("text/json")
		public String getGeonamesStates(@QueryParam("countryid") String geonameId){
			
			if(geonameId == null){
				geonameId = "";
			}
			
			JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().getGeonamesStates(geonameId)  );
			return xmlJSONObj.toString();
			
		}
		
		@GET            // CORRIGIDA    
		@Path("/nearplaces")
		@Produces("text/json")
		public String getPlacesNearYou(@QueryParam("latitude_longitude") String finalAdress, @QueryParam("countrycode") String countryCode, @QueryParam("type") String section, @QueryParam("offset") String placesOffset){
		
			if(finalAdress == null){
				finalAdress = "";
			}
			
			if(countryCode == null){
				countryCode = "";
			}
			
			if(section == null){
				section = "";
			}
			
			JSONObject xmlJSONObj = XML.toJSONObject( EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getPlacesNearYou(finalAdress, countryCode, section, placesOffset))  );
			return xmlJSONObj.toString();
			
		}
		
		@GET              // CORRIGIDA    
		@Path("/suggestedplaces")
		@Produces("text/json")
		public String getPlacesSuggestedByUS(@QueryParam("latitude_longitude") String finalAdress , @QueryParam("countrycode") String countryCode, @QueryParam("type") String section,@QueryParam("offset") String placesOffset){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getPlacesSuggestedByUS(finalAdress, countryCode, section, placesOffset))  );
			return xmlJSONObj.toString();
			
		}
		
		@GET            // CORRIGIDA    
		@Path("/directions")
		@Produces("text/json")
		public String getDirection(@QueryParam("origin_address") String orig,@QueryParam("destiny_address") String dest,@QueryParam("mode") String mode,@QueryParam("countrycode") String countryCode){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getDirection(orig, dest, mode, countryCode))  );
			return xmlJSONObj.toString();
			
		}
		
		@GET              // CORRIGIDA    
		@Path("/weather/forecast")
		@Produces("text/json")
		public String getWeather(@QueryParam("latitude_longitude") String finalAdress, @QueryParam("countrycode") String countryCode){
			
			JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getWeather(finalAdress, countryCode))  );
			return xmlJSONObj.toString();
			
		}
		
		/*@GET               // CORRIGIDA    
		@Path("/url/shortner")
		@Produces("text/json")	
		public String urlShortner(@QueryParam("longURL") String longURL){
			
			JSONObject xmlJSONObj = XML.toJSONObject( "<short_url>" + ServiceLocator.getInstance().getFacade().urlShortner(longURL) + "</short_url>" );
			return xmlJSONObj.toString();
			
		}*/
		
		@POST
		@Path("/email/send")
		@Produces("text/json")
		public String sendEmail(@FormParam("subject") String subject, @FormParam("message") String message){
				
			 JSONObject xmlJSONObj = XML.toJSONObject( "<default_response>" + ServiceLocator.getInstance().getFacade().sendEmail(subject, message, "ricardomb6@gmail.com") + "</default_response>");
			 return xmlJSONObj.toString();
			
		}
			
		@POST
		@Path("/report/send")	
		@Produces("text/json")
		public String sendJmsMessage(@FormParam("message") String messageText){
			 
			if(messageText == null){
				messageText = "";
			}
			
	        if(messageText.contains("null")){
				messageText = messageText.replaceAll("null", "");
			}
			
			
			try{
				File suggestedContentsTempFile = new File(System.getProperty("ricardombertani.projetos.allinyourhands.suggestedcontents.filetemp.path"));
				if(!suggestedContentsTempFile.exists()){
					
					 if(suggestedContentsTempFile.createNewFile()){
						 log.debug("\n-->New Suggested Contents Temp File created!");
					}
									
				}							
				
				// salvando registros em arquivo
				OutputStream os = (OutputStream) new FileOutputStream(suggestedContentsTempFile,true);
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
				PrintWriter printWriter = new PrintWriter(osw);			
				printWriter.println(messageText+"¬");		
				printWriter.close();
				osw.close();
				os.close();
				log.debug("\n-->Access/SuggestedContent Register was saved with success!!");
			
			}catch(IOException e){
				e.printStackTrace();
				
				log.debug("\n--->Error during the suggested contents temp file!!");
			}

			 JSONObject xmlJSONObj = XML.toJSONObject( "<default_response>" + "please, verify the server log for more information" + "</default_response>");
			 return xmlJSONObj.toString();
		}
		
		@GET
		@Path("/contents/recommended")
		@Produces("text/json")
		public String getSuggestedContents(){
			List<SuggestedContent> suggestedContents =  ServiceLocator.getInstance().getFacade().getSuggestedContents();
			String response = "<suggestedContents>";
			if(suggestedContents != null){
				for(SuggestedContent suggestedContent : suggestedContents){
					response += "<suggestedContent>"+
					"<id>"+suggestedContent.getId()+"</id>"+
					"<image>"+substituteScapeXMLCharacters(suggestedContent.getImage())+"</image>"+
					"<name>"+substituteScapeXMLCharacters(suggestedContent.getName())+"</name>"+
					"<type>"+suggestedContent.getType()+"</type>"+
					"<artist>"+substituteScapeXMLCharacters(suggestedContent.getArtist())+"</artist>"+
					"<count>"+suggestedContent.getCount()+"</count>"+
					"<extraInformation>"+substituteScapeXMLCharacters(suggestedContent.getExtraInformation())+"</extraInformation>"+
					"<date>"+suggestedContent.getDate()+"</date>"+
					"<accessedContent>"+suggestedContent.getAccessedContent()+"</accessedContent></suggestedContent>";
				}
				
			}
			
			response+= "</suggestedContents>";
			JSONObject xmlJSONObj = XML.toJSONObject( response );        
			return xmlJSONObj.toString();
			
		}
		
		public  String substituteScapeXMLCharacters(String in) {
			 
			  return in.replaceAll("&", "&amp;").replaceAll("<", "&lt").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;");

		}  
		
		
}
