package ricardombertani.projetos.allinyourhands.web.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.json.XML;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;
import ricardombertani.projetos.allinyourhands.web.locator.ServiceLocator;
import ricardombertani.projetos.allinyourhands.web.util.EncodingFixer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.apache.log4j.Logger;


@Path("/")
public class AiyhService{

	// variavel para os logs
	private static Logger log = Logger.getLogger(AiyhService.class.getName());
	
	
		
	@GET
	@Path("/statusapis/{isportalweb}")
	@Produces("text/json")
	public String getStatusAPIs(@PathParam("isportalweb") boolean isAccessByPortalWeb){	
			
		String test = ServiceLocator.getInstance().getFacade().getStatusAPIs(isAccessByPortalWeb);	
		JSONObject xmlJSONObj = XML.toJSONObject(  test  );
		return xmlJSONObj.toString();
				
	}
	
	@GET
	@Path("/songs/{query}/{pagenumber}/{lyric}/{grooveshark}")
	@Produces("text/json")
	public String getAudioByQuery(@PathParam("query") String query, @PathParam("pagenumber") int songPagNumber, @PathParam("lyric") String lyricIndicator, @PathParam("grooveshark") boolean groovesharkEnabled){
				
		JSONObject xmlJSONObj = XML.toJSONObject( EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getAudioByQuery(query, songPagNumber, lyricIndicator, groovesharkEnabled))  );
		return xmlJSONObj.toString();	
		
	}
	
	@GET
	@Path("/songs/categories")
	@Produces("text/json")
	public String getSongCategories(){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().getSongCategories()  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/artists/by/category/{categoryname}/{pagenumber}/{isportalweb}")
	@Produces("text/json")
	public String getArtistsByCategory(@PathParam("categoryname") String audiocatNam, @PathParam("pagenumber") int artistsPagNumber, @PathParam("isportalweb") boolean isWebVersion){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getArtistsByCategory(audiocatNam, artistsPagNumber, isWebVersion))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/songs/by/artist/{query}/{pagenumber}/{lyric}/{grooveshark}")
	@Produces("text/json")
	public String getSongsByArtist(@PathParam("query") String query, @PathParam("pagenumber") int songByArtPagNumber,@PathParam("lyric") String lyricIndicator,@PathParam("grooveshark") boolean groovesharkEnabled){
	
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getSongsByArtist(query, songByArtPagNumber, lyricIndicator, groovesharkEnabled))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/streaming/{songid}/{ipadress}/{sessionid}")
	@Produces("text/json")
	public String getStreamingURL(@PathParam("songid") String streamSongId, @PathParam("ipadress") String streamIpAdress, @PathParam("sessionid") String streamSessionID){
		
		JSONObject xmlJSONObj = XML.toJSONObject( "<streaming_url>" + ServiceLocator.getInstance().getFacade().getStreamingURL(streamSongId, streamIpAdress, streamSessionID) + "</streaming_url>" );
		return xmlJSONObj.toString();
		
	}
	
		
	
	@PUT
	@Path("/streaming30sec/{streamsessionid}/{streamkey}/{streamserverid}")	
	public String markStreaming30sec(@PathParam("streamsessionid") String streamSessionID, @PathParam("streamkey") String streamKey, @PathParam("streamserverid") String streamServerID ){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().markStreaming30sec(streamSessionID, streamKey, streamServerID)  );
		return xmlJSONObj.toString();
		
	}
	
	@PUT
	@Path("/streamingfinish/{streamsessionid}/{songid}/{streamkey}/{streamserverid}")
	public String markStreamingFinish(@PathParam("streamsessionid") String streamSessionID, @PathParam("songid") String streamSongId, @PathParam("streamkey") String streamKey,@PathParam("streamserverid") String streamServerID){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().markStreamingFinish(streamSessionID, streamSongId, streamKey, streamServerID)  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/videos/{query}/{categoryid}/{nextpagetoken}")
	@Produces("text/json")
	public String getVideos(@PathParam("query") String query,@PathParam("categoryid") String vcatId/*, countryCode*/,@PathParam("nextpagetoken") String vNextPgToken){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getVideos(query, vcatId, vNextPgToken))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/videos/{query}")
	@Produces("text/json")
	public String getVideos_(@PathParam("query") String query){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getVideos(query, "", "")) );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/videos/categories/{countrycode}")
	@Produces("text/json")
	public String getVideoCategories(@PathParam("countrycode") String countryCode){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getVideoCategories(countryCode))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/books/{keyword}/{pagenumber}")
	@Produces("text/json")
	public String getBooks(@PathParam("keyword") String query, @PathParam("pagenumber") String bstartIndex){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getBooks(query, bstartIndex,"us"))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/booksv2/{keyword}/{pagenumber}/{countryCode}")
	@Produces("text/json")
	public String getBooksV2(@PathParam("keyword") String query, @PathParam("pagenumber") String bstartIndex,@PathParam("countryCode") String countryCode){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getBooks(query, bstartIndex,countryCode))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/latitude_longitude/{address}")
	@Produces("text/json")
	public String getGeocodingAddress(@PathParam("address") String address){
		
		JSONObject xmlJSONObj = XML.toJSONObject( "<lat_long>" + ServiceLocator.getInstance().getFacade().getGeocodingAddress(address) + "</lat_long>");
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/countries")
	@Produces("text/json")
	public String getGeonames(){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().getGeonames()  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/countries/states/{countryid}")
	@Produces("text/json")
	public String getGeonamesStates(@PathParam("countryid") String geonameId){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  ServiceLocator.getInstance().getFacade().getGeonamesStates(geonameId)  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/places/near_places/{latitude_longitude}/{countrycode}/{type}/{offset}")
	@Produces("text/json")
	public String getPlacesNearYou(@PathParam("latitude_longitude") String finalAdress,@PathParam("countrycode") String countryCode,@PathParam("type") String section,@PathParam("offset") String placesOffset){
	
		JSONObject xmlJSONObj = XML.toJSONObject( EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getPlacesNearYou(finalAdress, countryCode, section, placesOffset))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/places/recommended_places/{latitude_longitude}/{countrycode}/{type}/{offset}")
	@Produces("text/json")
	public String getPlacesSuggestedByUS(@PathParam("latitude_longitude") String finalAdress ,@PathParam("countrycode") String countryCode,@PathParam("type") String section,@PathParam("offset") String placesOffset){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getPlacesSuggestedByUS(finalAdress, countryCode, section, placesOffset))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/directions/{origin_address}/{destiny_address}/{mode}/{countrycode}")
	@Produces("text/json")
	public String getDirection(@PathParam("origin_address") String orig,@PathParam("destiny_address") String dest,@PathParam("mode") String mode,@PathParam("countrycode") String countryCode){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getDirection(orig, dest, mode, countryCode))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/weather/forecast/{latitude_longitude}/{countrycode}")
	@Produces("text/json")
	public String getWeather(@PathParam("latitude_longitude") String finalAdress,@PathParam("countrycode") String countryCode){
		
		JSONObject xmlJSONObj = XML.toJSONObject(  EncodingFixer.fixEncodingIssues(ServiceLocator.getInstance().getFacade().getWeather(finalAdress, countryCode))  );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/url/shortner/{longURL}")
	@Produces("text/json")	
	public String urlShortner(@PathParam("longURL") String longURL){
		
		JSONObject xmlJSONObj = XML.toJSONObject( "<short_url>" + ServiceLocator.getInstance().getFacade().urlShortner(longURL) + "</short_url>" );
		return xmlJSONObj.toString();
		
	}
	
	@GET
	@Path("/email/send/{subject}/{message}")
	@Produces("text/json")
	public String sendEmail(@PathParam("subject") String subject, @PathParam("message") String message){
			
		 JSONObject xmlJSONObj = XML.toJSONObject( "<default_response>" + ServiceLocator.getInstance().getFacade().sendEmail(subject, message, "your e-mail adress") + "</default_response>");
		 return xmlJSONObj.toString();
		
	}
		
	@GET
	@Path("/report/send/{message}")	
	@Produces("text/json")
	public String sendJmsMessage(@PathParam("message") String messageText){
		 
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

	
	
	//Metodo generico referente ao chat *** desfazer em forma de 1 ou mais servicos
	/*public String chatRoomGeneralRequest(String requestType, HashMap<String, String> parametersTable){
		return ServiceLocator.getInstance().getFacade().chatRoomGeneralRequest(requestType, parametersTable);
	}
		
		//Metodo referente a copa do mundo(alugueis)
		public String worldCupRentsProposalGeneralRequest(String requestType, HashMap<String, String> parametersTable);
	*/
	
		
	
}
