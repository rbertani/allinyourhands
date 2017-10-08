package ricardombertani.projetos.allinyourhands.core.facade;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import ricardombertani.projetos.allinyourhands.apidata.book.BooksVolumeCollection;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;
import ricardombertani.projetos.allinyourhands.core.apirequest.RequestManager;
import ricardombertani.projetos.allinyourhands.core.apiresponse.ResponseFormater;
import ricardombertani.projetos.allinyourhands.core.locator.ServiceLocator;
import ricardombertani.projetos.allinyourhands.core.util.AllInYourHandsConstants;
import ricardombertani.projetos.allinyourhands.core.util.ReportManager;
import ricardombertani.projetos.allinyourhands.core.util.ReportsManager;
import ricardombertani.projetos.allinyourhands.core.util.SuggestedContentsManager;
  

/*@WebService(name = "", targetNamespace = "allinyourhands.webservice", serviceName = "AllInYourHandsService")
@SOAPBinding(style = SOAPBinding.Style.RPC)*/
@Stateless 
public class CoreFacade implements CoreFacadeInterface{

	private static Logger log = Logger.getLogger(CoreFacade.class.getName());
	  
	 
	public String getStatusAPIs(boolean isAccessByPortalWeb) {
		
		return RequestManager.requestStatusAPIs(isAccessByPortalWeb);
		
	}

	
	 
	public String getAudioByQuery(String query, int songPagNumber, String lyricIndicator, boolean groovesharkEnabled) {
		
				
		return ResponseFormater.formaterAudioSpotify_response( RequestManager.requestAudioSpotifyAPI(query, songPagNumber) , lyricIndicator); 
		
	}

	 
	public String getArtistsByCategory(String audiocatNam, int artistsPagNumber, boolean isWebVersion) {
		
		return ResponseFormater.formaterTopArtistsByCategoryAPI_response( RequestManager.requestTopArtistsByCategoryAPI( audiocatNam, artistsPagNumber,isWebVersion ), isWebVersion );
	}
 
	 
	/*public String getStreamingURL(String streamSongId, String streamIpAdress, String streamSessionID) {
		
		return ResponseFormater.formaterStreamingKeyURL_response( RequestManager.requestStreamKeyStreamServer(streamSongId,streamIpAdress,streamSessionID  ) );
	}
	
	 
	public String markStreaming30sec(String streamSessionID, String streamKey,String streamServerID) {
		return RequestManager.requestMarkStreamKeyOver30Secs(streamSessionID, streamKey, streamServerID);
	}

	
	public String markStreamingFinish(String streamSessionID,String streamSongId, String streamKey, String streamServerID) {
		
		return RequestManager.requestMarkSongComplete(streamSessionID,streamSongId, streamKey, streamServerID);
	}
*/
	
	public String getSongsByArtist(String query, int songByArtPagNumber,String lyricIndicator, boolean groovesharkEnabled) {
		
		if(groovesharkEnabled){
			return ResponseFormater.formaterAudioAPI_response( RequestManager.requestAudioAPI( query, songByArtPagNumber ), lyricIndicator );
		}else{
			return "{}";
		}
		
	}
	
	
	public String getSongCategories() {
		
		return ResponseFormater.formaterTopCategoriesAPI_response( RequestManager.requestTopCategoriesAPI() );
	}

	
	public String getVideos(String query, String vcatId,/*countryCode,*/ String vNextPgToken) {

		return  ResponseFormater.formaterVideoAPI_response( RequestManager.requestVideoAPI( query,  vcatId,/*countryCode,*/ vNextPgToken  )  ); // pageToken dever� ser atualizado com o valor em sess�o, este valor em sess�o deve ser o valor do campo "nextPageToken" obtido na leitura do json
	}
	
	
	public String getVideoCategories(String countryCode) {
		
		return ResponseFormater.formaterVideoCategoriesAPI_response( RequestManager.requestVideoCategoriesAPI( countryCode  ) );
	}


	
	public String getBooks(String query, String bstartIndex, String countryCode) {
		
		if(countryCode.equals("en")){
			countryCode = "us";
		}
		
		BooksVolumeCollection booksVolumeCollectionAPI1 =  null;
		BooksVolumeCollection booksVolumeCollectionAPI2 = null;
				 				
		int googleBooksApiEnabled = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_BOOKS_ENABLED));
		int openLibraryEnabled = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPEN_LIBRARY_ENABLED));
		
		if(googleBooksApiEnabled == 1){
			booksVolumeCollectionAPI1 = ResponseFormater.formaterBooks1API_response( RequestManager.requestBooksAPI(  query,  bstartIndex, countryCode  )  );
			
			if(openLibraryEnabled == 1){// mistura conteudos
				
				if(booksVolumeCollectionAPI1 != null){
				
					if(booksVolumeCollectionAPI1.getBooks().size() < 4){
						booksVolumeCollectionAPI2 = ResponseFormater.formaterBooks2API_response( RequestManager.requestBooks2API( query,  bstartIndex ) ); 
						booksVolumeCollectionAPI1.getBooks().addAll(booksVolumeCollectionAPI2.getBooks());			
						Collections.sort(booksVolumeCollectionAPI1.getBooks());
					}
				
				}
			}
		}
		
		
		if(openLibraryEnabled == 1){
			booksVolumeCollectionAPI2 = ResponseFormater.formaterBooks2API_response( RequestManager.requestBooks2API( query,  bstartIndex ) ); 
			if(googleBooksApiEnabled == 1){				
				booksVolumeCollectionAPI1 = ResponseFormater.formaterBooks1API_response( RequestManager.requestBooksAPI(  query,  bstartIndex, countryCode  )  );
				booksVolumeCollectionAPI2.getBooks().addAll(booksVolumeCollectionAPI1.getBooks());			
				Collections.sort(booksVolumeCollectionAPI2.getBooks());
			}
			
		}
		
		
		return ResponseFormater.formaterBooksFinalAPI_response( (booksVolumeCollectionAPI1 == null)?booksVolumeCollectionAPI2:booksVolumeCollectionAPI1 );
	}

	
	public String getGeocodingAddress(String address) {
		 
		 String finalAdress = 	ResponseFormater.formaterPlacesGeocodingAPI_response( RequestManager.requestPlacesGeocodingAPI( address ) );
		 if(finalAdress.equals("")){
			 finalAdress = 	ResponseFormater.formaterPlacesGeocoding2API_response( RequestManager.requestPlacesGeocoding2API( address ) );
		 }
		 
		 return finalAdress;
	}
	
	
	public String getGeonames() {
			return ResponseFormater.formaterPlacesGeonamesAPI_response( RequestManager.requestPlacesGeonamesAPI() );
	}
	
	
	public String getGeonamesStates(String geonameId) {
		return ResponseFormater.formaterPlacesGeonamesStatesAPI_response( RequestManager.requestPlacesGeonamesStatesAPI(geonameId) );
	}


	
	public String getPlacesNearYou(String finalAdress, String countryCode, String section,String placesOffset) {
		
		return ResponseFormater.formaterPlacesNearOfMeAPI_response( RequestManager.requestPlacesNearOfMeAPI( finalAdress, countryCode, section, placesOffset ) );
	}

	
	public String getPlacesSuggestedByUS(String finalAdress , String countryCode, String section, String placesOffset) {
		
		return ResponseFormater.formaterPlacesSuggestionsAPI_response( RequestManager.requestPlacesSuggestionsAPI( finalAdress , countryCode, section, placesOffset ) );
		
	}

	
	public String getDirection(String orig, String dest, String mode, String countryCode) {
		
		return ResponseFormater.formaterDirectionsAPI_response( RequestManager.requestDirectionsAPI(orig, dest, mode, countryCode ) );
	
	}

	
	public String getWeather(String finalAdress, String countryCode) {
		
		return ResponseFormater.formaterWeatherAPI_response( RequestManager.requestWeatherAPI( finalAdress, countryCode), finalAdress );
	}

	
	public String getGoods(String countryCode, String query, String price_range,String goodsOffset) {
		
		return ResponseFormater.formaterSalesAPI_response( RequestManager.requestVendasAPI( countryCode, query, price_range, goodsOffset ) );
		
	}

	
	/*public String chatRoomGeneralRequest(String requestType, HashMap<String, String> parametersTable) {
		
		return  RequestManager.requestChatWeb(requestType, makeChatRoomProperties(requestType,parametersTable));
	}
	
	
	public String worldCupRentsProposalGeneralRequest(String requestType, HashMap<String, String> parametersTable) {
		
		return RequestManager.requestWorldCupWeb(requestType, makeWorldCupProperties(requestType, parametersTable));
	}


	
	public String sendSMS(String smsDest, String smsMsg) {
		
		return RequestManager.requestSendSmsAPI(smsDest, smsMsg);
	}


	
	public String urlShortner(String longURL) {
		
		return ResponseFormater.formaterShortnerUrlAPI_response( RequestManager.requestShortenerUrlAPI(longURL) );
	}*/

	
	public String sendEmail(String subject, String message, String destMail) {
		
		return RequestManager.requestMailService(subject, message, destMail);
	}
	
	
	public String getBannersWeb(String ip, String userAgent, String browser, boolean isBannerWeb, String bannerWebImageSizePosfix) {
		
		return ResponseFormater.formaterBannersWebAPI_response( RequestManager.requestPortalWebBanners(ip, userAgent, browser,isBannerWeb,bannerWebImageSizePosfix) );
				
	}
	
	
	/*public String sendJmsMessage(String messageText) {
		 MessageSender.sendMessage(messageText);
		 return "please, verify the log for more information";
	}	*/	


	public void saveSuggestedContent(String id,String image, String name, String type, String artistName, String extraInformation, String accessedContent){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
		Date currentDate = new Date();
		
		if(accessedContent == null){
			accessedContent = "1";
		}
		String suggestedContentGeneralInfo = id+"|"+image+"|"+name+"|"+type+"|"+artistName+"|"+extraInformation+"|"+sdf.format(currentDate)+"|"+accessedContent;
		
		if(suggestedContentGeneralInfo.contains("null")){
			suggestedContentGeneralInfo.replaceAll("null", "");
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
			printWriter.println(suggestedContentGeneralInfo);		
			printWriter.close();
			osw.close();
			os.close();
			log.debug("\n-->Access/SuggestedContent Register was saved with success!!");
		
		}catch(IOException e){
			e.printStackTrace();
			
			log.debug("\n--->Error during the suggested contents temp file!!");
		}
		
		
		
		
	}
	
	private  Properties makeChatRoomProperties(String chatCommandType, HashMap<String, String> parametersTable){
		 Properties properties = new Properties();
				 
		 if(chatCommandType.equals("createUser")){
			 			 
			 properties.setProperty("psw", parametersTable.get("userPsw"));
			 properties.setProperty("name", parametersTable.get("userName"));
			 properties.setProperty("mail", parametersTable.get("userMail"));
			 properties.setProperty("devId", parametersTable.get("userDevId"));
			 properties.setProperty("regId",parametersTable.get("userRegId"));
			 properties.setProperty("countryCode", parametersTable.get("countryCode"));
			 
		 }else if(chatCommandType.equals("setUserAuthorizedFlag")){
			 
			 properties.setProperty("userId", parametersTable.get("userId"));
			 properties.setProperty("countryCode", parametersTable.get("countryCode"));
			 
		 }		 
		 else if (chatCommandType.equals("userLogin")){
			 			 
			 properties.setProperty("psw", parametersTable.get("userPsw"));
			 properties.setProperty("name", parametersTable.get("userName"));
			 properties.setProperty("regId",parametersTable.get("userRegId"));
			 properties.setProperty("countryCode", parametersTable.get("countryCode"));
			 			 
		 }else if(chatCommandType.equals("authorizatedUserLogin")){
			 
			 properties.setProperty("psw", parametersTable.get("userPsw"));
			 properties.setProperty("name", parametersTable.get("userName"));
			 properties.setProperty("regId",parametersTable.get("userRegId"));
			 properties.setProperty("countryCode", parametersTable.get("countryCode"));
			 
		 }else if (chatCommandType.equals("createChatRoom")){
			 			
			 properties.setProperty("chatRoomName", parametersTable.get("chatRoomName"));
			 properties.setProperty("maxUsers", parametersTable.get("maxUsers"));
			 
			 properties.setProperty("CRIsRegionalized",  parametersTable.get("CRIsRegionalized")); 
			 properties.setProperty("CRRegionID",  parametersTable.get("CRRegionID"));
			 properties.setProperty("CRCountryName", parametersTable.get("CRCountryName"));
			 
		 }else if (chatCommandType.equals("saveMessage")){
			 
			 properties.setProperty("userId", parametersTable.get("userId"));
			 properties.setProperty("chatRoomId", parametersTable.get("chatRoomId"));
		
			 properties.setProperty("message", parametersTable.get("message") );
			 	
			 String userPosition = (String)parametersTable.get("ll");
			 String userTimeZoneID = "";
			 if(!userPosition.equals("")) {
				 userTimeZoneID = ResponseFormater.formaterTimeZoneAPI_response( RequestManager.requestDirectionsTimeZoneAPI(userPosition) );
			 }
			 		 			
			 properties.setProperty("defaultTZ", userTimeZoneID);
			 
		 }else if (chatCommandType.equals("deleteChatRoom")){
			 		
			 properties.setProperty("chatRoomId", parametersTable.get("chatRoomId"));
			 
		 }else if (chatCommandType.equals("listGeneralChatRoom")){
			 // dispensa parametros
		 }else if (chatCommandType.equals("listChatRoomWhereUserTalks")){ 
			 
			 properties.setProperty("userId", parametersTable.get("userId"));
			 
		 }else if (chatCommandType.equals("listRegionalizedChatRoom")){
			
			 properties.setProperty("CRCountryName", parametersTable.get("CRCountryName"));
			 
		 }else if (chatCommandType.equals("listRegionalizedSubChatRoom")){
			 
			 properties.setProperty("CRRegionID",  parametersTable.get("CRRegionID")); 
			 
		 }else if (chatCommandType.equals("messagesByChatRoom")){
			 
			 properties.setProperty("chatRoomId", parametersTable.get("chatRoomId"));
			 
			 String userPosition = (String)parametersTable.get("ll");
			 String userTimeZoneID = "";
			 if(!userPosition.equals("")) {
				 userTimeZoneID = ResponseFormater.formaterTimeZoneAPI_response( RequestManager.requestDirectionsTimeZoneAPI(userPosition) );
			 }
			 		 			
			 properties.setProperty("defaultTZ", userTimeZoneID);
			 
		 }else if(chatCommandType.equals("chatRemUserPsw")){
			
			 properties.setProperty("name", parametersTable.get("userName"));
			 properties.setProperty("countryCode", parametersTable.get("countryCode"));
			 
		 }else if(chatCommandType.equals("listUsersByChatRoom")){
			 
			 properties.setProperty("chatRoomId", parametersTable.get("chatRoomId"));
			 
		 }else if(chatCommandType.equals("sendDirectMessage")){
			 
			 properties.setProperty("userId", parametersTable.get("userId"));
			 properties.setProperty("directMsg", parametersTable.get("directMsg"));
			 properties.setProperty("directMsgDestRegID", parametersTable.get("directMsgDestRegID"));
			 properties.setProperty("chatRoomId", parametersTable.get("chatRoomId"));
			 
		 }
		 
		 
		 
		 return properties;
	 }
	
	 private  Properties makeWorldCupProperties(String worldCupCommandType, HashMap<String, String> parametersTable){
		 Properties properties = new Properties();
		 
		 if(worldCupCommandType.equals("saveProposal")){
			 
			 properties.setProperty("type", parametersTable.get("type"));
			 properties.setProperty("neighHood",parametersTable.get("neighHood"));
			 properties.setProperty("contactName", parametersTable.get("contactName"));
			 properties.setProperty("contactEmail", parametersTable.get("contactEmail"));
			 properties.setProperty("contactCelPhone", parametersTable.get("contactCelPhone"));
			 properties.setProperty("days", parametersTable.get("days"));
			 properties.setProperty("price", parametersTable.get("price"));
			 properties.setProperty("roomsNumber", parametersTable.get("roomsNumber"));
			 properties.setProperty("description", parametersTable.get("description"));
			 properties.setProperty("devId", parametersTable.get("userDevId"));
			 
		 }else if(worldCupCommandType.equals("listProposals")){
			 // sem parametros
			 properties.setProperty("worldLimitPage", parametersTable.get("worldLimitPage"));
			 
		 }else if(worldCupCommandType.equals("deleteRentProposal")){
			 
			 properties.setProperty("rentPropID", parametersTable.get("rentPropID"));
		 }
		
		 return properties;
		 
	 }


	
	public List<SuggestedContent> getSuggestedContents() {
		return  SuggestedContentsManager.getSuggestedContents();
	}

	
	public void updateSuggestedContents(){
		
		SuggestedContentsManager.updateSuggestedContents();
		
	}
	
	
	public void recreateSuggestedContentsCacheFile(List<SuggestedContent> suggestedContents, File suggestedContentsCacheFile){
		SuggestedContentsManager.recreateSuggestedContentsCacheFile(suggestedContents, suggestedContentsCacheFile);
	}


	
	public int getAccessCount(String accessName) {
		
		return ReportsManager.getAccessCount(accessName);
	}
	
	
	public int getAccessCountByPeriod(String initialDate, String finalDate,	String accessName) {
		
		return ReportsManager.getAccessCountByPeriod(initialDate, finalDate, accessName);
		
	}


	
	public int getQueryCount(String contentType) {
		return ReportsManager.getQueryCount(contentType);
	}


	
	public List<String> getQuerysByType(String contentType) {
		return ReportsManager.getQuerysByType(contentType);
	}


	
	public int getQueriesCountByPeriod(String initialDate, String finalDate,
			String type) {
		return ReportsManager.getQueriesCountByPeriod(initialDate, finalDate, type);
	}


	
	public List<String> getQueriesByPeriod(String initialDate,
			String finalDate, String type) {
		return ReportsManager.getQueriesByPeriod(initialDate, finalDate, type);
	}


	
	public void deleteSuggestedContents(List<String> suggestedContentsIDs) {
		
		SuggestedContentsManager.deleteSuggestedContents(suggestedContentsIDs);
		
		//ServiceLocator.getInstance().getRemoteFacade().deleteSuggestedContentsIntoRemoteServer(suggestedContentsIDs);
		
	}


	
	public void deleteSuggestedContentsIntoRemoteServer(List<String> suggestedContentsIDs) {
		
		SuggestedContentsManager.deleteSuggestedContents(suggestedContentsIDs);
		
	}



	public void saveContentInformation(SuggestedContent currentSuggestedContent) {
		
		SuggestedContentsManager.saveSuggestedContent(currentSuggestedContent);
		
	}


	public void saveReportInformation(String name, String type, String artist, int accesedContent, String date){
		ReportManager.saveReportInfo(name, type, artist, accesedContent, date);
	}
	
	
}
