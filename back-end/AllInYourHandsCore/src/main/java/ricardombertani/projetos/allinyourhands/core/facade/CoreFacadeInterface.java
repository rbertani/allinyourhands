package ricardombertani.projetos.allinyourhands.core.facade;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;
import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;
  
/** Interface responsavel por expor a l�gica de negocio do All In Your Hands, os m�todos descritos nesta interface retornam as informacoes procuradas
 *  em forma de um xml,o qual eh completamente descrito pelo  modulo AllInYourHandsData e pode ser facilmente lido utilizando a api Xstream
 * 
 * @author Ricardo
 *  
 */  
@Remote
public interface CoreFacadeInterface {

	public String getStatusAPIs(boolean isAccessByPortalWeb);

	public String getAudioByQuery(String query, int songPagNumber, String lyricIndicator, boolean groovesharkEnabled);
	
	public String getArtistsByCategory(String audiocatNam, int artistsPagNumber, boolean isWebVersion);
	
	/*public String getStreamingURL( String streamSongId, String streamIpAdress,String streamSessionID);
	
	public String markStreaming30sec( String streamSessionID, String streamKey, String streamServerID );
	
	public String markStreamingFinish( String streamSessionID, String streamSongId, String streamKey, String streamServerID);
	*/
	public String getSongsByArtist(String query,int songByArtPagNumber, String lyricIndicator, boolean groovesharkEnabled);
	
	public String getSongCategories();
	
	public String getVideos(String query, String vcatId/*, countryCode*/, String vNextPgToken);
	
	public String getVideoCategories(String countryCode);
	
	public String getBooks(String query, String bstartIndex, String countryCode);
	
	public String getGeocodingAddress(String address);
	
	public String getGeonames();
	
	public String getGeonamesStates(String geonameId);
	
	public String getPlacesNearYou(String finalAdress, String countryCode, String section,String placesOffset);
	
	public String getPlacesSuggestedByUS(String finalAdress , String countryCode, String section, String placesOffset);
	
	public String getDirection(String orig, String dest, String mode, String countryCode);
	
	public String getWeather(String finalAdress, String countryCode);
	  
	public String getGoods(String countryCode, String query, String price_range,String goodsOffset);
	
	//Metodo generico referente ao chat
	//public String chatRoomGeneralRequest(String requestType, HashMap<String, String> parametersTable);
	
	//Metodo referente a copa do mundo(alugueis)
	//public String worldCupRentsProposalGeneralRequest(String requestType, HashMap<String, String> parametersTable);
	
	/***  Demais Servicos***/
	//public String sendSMS(String smsDest, String smsMsg);
	
	//public String urlShortner(String longURL);
	
	public String sendEmail(String subject, String message, String destMail);
	
	public String getBannersWeb(String ip, String userAgent,String browser, boolean isBannerWeb,String bannerWebImageSizePosfix);
	
	//public String sendJmsMessage(String messageText);
	
	public List<SuggestedContent> getSuggestedContents();
	
	public void updateSuggestedContents();
	
	public void deleteSuggestedContents(List<String> suggestedContentsIDs);
			
	public void deleteSuggestedContentsIntoRemoteServer(List<String> suggestedContentsIDs);
	
	public void recreateSuggestedContentsCacheFile(List<SuggestedContent> suggestedContents, File suggestedContentsCacheFile);
	
	/**** Dados para relatorios (por enquanto apenas portal web)****/
	public int getAccessCount(String accessName);  // MOBILE ACCESS, IPHONE ACCESS, WINDOWSPHONE ACCESS, BROWSER ACCESS
	
	public int getAccessCountByPeriod(String initialDate, String finalDate, String accessName);  // MOBILE ACCESS, IPHONE ACCESS, WINDOWSPHONE ACCESS, BROWSER ACCESS
	
	public int getQueryCount(String contentType);  // musics, music_categories, videos, video_categories, places, books, weather ou "" para total
	
	public List<String> getQuerysByType(String contentType);  // musics, music_categories, videos, video_categories, places, books, weather
	
	public int getQueriesCountByPeriod(String initialDate, String finalDate, String type); // musics, music_categories, videos, video_categories, places, books, weather
	
	public List<String> getQueriesByPeriod(String initialDate, String finalDate, String type); // musics, music_categories, videos, video_categories, places, books, weather

	
	// ** METODOS PARA SALVAR INFORMACOES DE NAVEGACAO EM ARQUIVO
	public void saveContentInformation(SuggestedContent currentSuggestedContent);
	  
	public void saveReportInformation(String name, String type, String artist, int accesedContent, String date);
}
