package ricardombertani.projetos.allinyourhands.core.apirequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;
import ricardombertani.projetos.allinyourhands.core.apiresponse.ResponseFormater;
import ricardombertani.projetos.allinyourhands.core.util.AllInYourHandsConstants;
import ricardombertani.projetos.allinyourhands.core.util.ApiUrlMaker;

/** Classe responsï¿½vel por fazer os requests para cada uma das APIs e retornar o resultado do request(ainda sem formataï¿½ï¿½o)
 * 
 * @author Ricardo M. Bertani
 *
 */
public class RequestManager {

	private static Logger log = Logger.getLogger(RequestManager.class.getName());
		  
	private static URL requestUrl;
	private static HttpURLConnection connection;
	public static int resultCode = 200;
	  
	public static HttpSession session;
		
	      
	public static String requestBooksAPI(String text, String startIndex, String countryCode){
		 
		return requestAPI( ApiUrlMaker.makeBooksApiURL(text, startIndex,countryCode), "UTF-8", true ); // no caso do google books serï¿½o necessï¿½rias vï¿½rias invocaï¿½ï¿½es
		                                                                   // devido ao alto numero de resultados, o startIndex deve ser incrementado 
		                                                                  // de 40 em 40 atï¿½ que o campo "totalItems" obtido no request seja igual ao
		                                                                 // numero de resultados vindos do request  (usar sessï¿½o para armazenar o ultimo 
                                                                         // "startIndex" e se houver outro request, entï¿½o incrementar o "startIndex" em 40)
		
	}
	
	public static String requestBooks2API(String text, String startIndex){
						
		return requestAPI( ApiUrlMaker.makeBooksApi2URL(text, startIndex), "UTF-8", true ) ;//ISO-8859-1
	}
	
	/** Funï¿½ï¿½o utilizada para efetuar um request para verificar se o livro em questï¿½o estï¿½ disponivel para leitura
	 * 
	 * @param bookCheckPartialURL
	 * @return
	 */
	public static String requestBooks2DetailsAPI(String editionKey){
		
		return requestAPI( ApiUrlMaker.makeBooksApi2_DetailsURL(editionKey), "UTF-8", true  );
	}
	        
	public  static String requestDirectionsAPI(String origin, String destination, String mode, String language){
		return requestAPI( ApiUrlMaker.makeDirectionsApiURL(origin, destination, mode, language), "UTF-8", true );
		
	}
	
	public  static String requestDirectionsStaticMapAPI(String initialAndFinalLatLongFormat, String pathLatLongFormat/*, int zoomValue*/){
		return /*requestAPI(*/ ApiUrlMaker.makeDirectionsStaticMapApiURL(initialAndFinalLatLongFormat, pathLatLongFormat/*, zoomValue*/); /*, "ISO-8859-1"  );*/
		
	}
	
	public  static String requestDirectionsStaticMapOnePlacePointAPI(String placeLatLong){
		return  ApiUrlMaker.makeDirectionsStaticMapOnePlacePointApiURL(placeLatLong); 
		
	}
	
	public  static String requestDirectionsStreetViewAPI(String currentPosition){
		return ApiUrlMaker.makeDirectionsStreetViewApiURL(currentPosition);
		
	}
	
	public static String requestDirectionsTimeZoneAPI(String currentPosition){
		return requestAPI( ApiUrlMaker.makeDirectionsTimeZoneApiURL(currentPosition), "UTF-8", true  );
	}
	             
	public static String requestYahooAPI(String text, String region){	
		return requestAPI( ApiUrlMaker.makeYahooApiURL(text, region), "UTF-8", true );
		
	}
	
	public static String requestWeatherAPI(String text, String lang){	
		return requestAPI( ApiUrlMaker.makeWeatherApiURL(text, lang), "UTF-8", true );
	}
	
	public static String requestVideoAPI(String text, String categoryId,/* String regionCode,*/String pageToken){	 // no caso do Youtube, como tratam-se de muitos Dados, ï¿½ necessario varias invocaï¿½ï¿½es
		                                                                     // a API para obter as diversas paginas de resultados atravï¿½s do parametro "pageToken"
		                                                                    // O parametro "pageToken" deverï¿½ receber a cada request  o valor do campo "nextPageToken"
		                                                                    // atï¿½ que este campo nï¿½o possua valor algum (usar sessï¿½o para armazenar o ultimo 
		                                                                    // "nextPageToken" e se houver outro request, entï¿½o usar esse nextPageToken)
		return requestAPI( ApiUrlMaker.makeVideoApiURL(text,categoryId,/* regionCode,*/ pageToken), "UTF-8", true );		
	}
	
	public static String requestVideoCategoriesAPI(String regionCode){	
      
       return requestAPI( ApiUrlMaker.makeVideoCategoriesApiURL(regionCode), "UTF-8", true );		
    }
	
	/************************Requests Utilizando as APIs do Grooveshark e LastFM*******/
	
	/** Mï¿½todo que obtem as mï¿½sicas atravï¿½s da api Tinysong (api terceirizada do grooveshark). Ela nï¿½o inclui imagem de preview, a qual precisa ser
	 *  obtiva via outras APIs
	 * 
	 * @param text
	 * @param pageNumber
	 * @return
	 */
	public static String requestAudioAPI(String text, int pageNumber){
				
		return requestAPI(ApiUrlMaker.makeAudioApiURL(text,pageNumber), "UTF-8", true);
	}
	
	/** Mï¿½todo que obtem as mï¿½sicas atravï¿½s da api oficial do grooveshark
	 * 
	 * @param text
	 * @param pageNumber
	 * @return
	 */
	public static String requestAudioOfficialGroovesharkAPI(String text, int pageNumber){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		String secretKey = keyVector[0];
		
		// mesma property dos mï¿½todos de streaming
	    String groovesharkOfficialApiBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
	    
	    String country = ResponseFormater.formaterStreamingGetCountry_response(requestGetCountryValue(""));
	    log.debug("\n-->Country: "+country);
	    Integer limit = pageNumber * Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_RESULTSPERPAGE_LIMIT));
	   	    
		String requestBody = "{\"method\":\"getSongSearchResults\",\"header\":{\"wsKey\":\""+keyVector[1]+"\"},\"parameters\":{\"query\":\""+text+"\", \"limit\": \""+String.valueOf(limit)+"\", \"country\": {"+country+"}}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
					
		return	requestAPI(groovesharkOfficialApiBaseURL+sig+"|"+requestBody, "UTF-8", false);
			
	}
	
	/** Mï¿½todo utilizado para obter a url direta para a mï¿½sica atravï¿½s da api oficial do grooveshark
	 * 
	 * @param songID
	 * @return
	 */
	public static String requestAudioUrlOfficialGroovesharkAPI(String songID){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		String secretKey = keyVector[0];
		
		// mesma property dos mï¿½todos de streaming
	    String groovesharkOfficialApiBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
	    
	  	    
		String requestBody = "{\"method\":\"getSongURLFromSongID\",\"header\":{\"wsKey\":\""+keyVector[1]+"\"},\"parameters\":{ \"songID\": \""+songID+"\"}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
					
		return	requestAPI(groovesharkOfficialApiBaseURL+sig+"|"+requestBody, "UTF-8", false);
			
	}
	
	public static String requestAudioSpotifyAPI(String text, int pageNumber){
		
		return requestAPI(ApiUrlMaker.makeAudioSpotifyApiURL(text, pageNumber), "UTF-8", true);
		
	}
	
	public static String requestTopArtistsByCategoryAPI(String text, int pageNumber, boolean isWebVersion){
		
		return requestAPI(ApiUrlMaker.makeTopArtistsByCategoryApiURL(text, pageNumber,isWebVersion), "UTF-8", true);
	}
	
	public static String requestTopCategoriesAPI(){
		
		return requestAPI(ApiUrlMaker.makeTopCategoriesApiURL(), "UTF-8", true);
	}

		 
	
	/*************   REQUESTS RELATIVOS AO  SERVIï¿½O DE STREAMING DO GROOVESHARK**/
	public static String requestNewSessionIdValue(){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
		String requestBody = "{\"method\":\"startSession\",\"header\":{\"wsKey\":\""+keyVector[1]+"\"}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
		
					
		return	requestAPI(streamingBaseURL+sig+"|"+requestBody, "UTF-8", false);
			
	}
	
	public static String requestGetCountryValue(String ipAdress){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
		String requestBody = "{\"method\":\"getCountry\",\"header\":{\"wsKey\":\""+keyVector[1]+"\"},\"parameters\":{\"ip\":\""+ipAdress+"\"}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
					
		return	requestAPI(streamingBaseURL+sig+"|"+requestBody, "UTF-8", false);
			
	}
	
	public static String requestStreamKeyStreamServer(String songId, String ipAdress, String sessionID){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
		
	    String country = ResponseFormater.formaterStreamingGetCountry_response(requestGetCountryValue(ipAdress));
	    log.debug("\n-->Country: "+country);
	    
	    String requestBody = "{\"method\":\"getStreamKeyStreamServer\",\"header\":{\"wsKey\":\""+keyVector[1]+"\", \"sessionID\": \""+sessionID+"\"},\""+
	    "parameters\":{\"songID\":"+songId+", \"country\": {"+country+"}}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
		
		String response = "error";
				
		int streamingEnabled = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_ENABLED));    	
    	if(streamingEnabled == 1){
    		response = requestAPI(streamingBaseURL+sig+"|"+requestBody, "UTF-8", false);
    	}
					
		return response;
	}
	
	

	public static String requestMarkStreamKeyOver30Secs(String sessionID, String streamKey, String streamServerID){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		//{"method":"markStreamKeyOver30Secs","header":{"wsKey":"portele_ricardo","sessionID":"bfe5a658be829215f252243b6762c4bd"},"parameters":{"streamKey":"96a034ccf610cf384e201cdf4413bf1359696e36_5324731c_23bc62d_2acf683_11b0b5b1b_8_0","streamServerID":"67108864","uniqueID":""}}
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
		    
	    String requestBody = "{\"method\":\"markStreamKeyOver30Secs\",\"header\":{\"wsKey\":\""+keyVector[1]+"\", \"sessionID\": \""+sessionID+"\"},\""+
	    "parameters\":{\"streamKey\":\""+streamKey+"\", \"streamServerID\": \""+streamServerID+"\"}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
				
		String response = requestAPI(streamingBaseURL+sig+"|"+requestBody, "UTF-8", false);
		return	(response.contains("success"))?"success":"error";
	}
	
	public static String requestMarkSongComplete(String sessionID, String songID, String streamKey,String streamServerID){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		//{"method":"markSongComplete","header":{"wsKey":"portele_ricardo","sessionID":"bfe5a658be829215f252243b6762c4bd"},"parameters":{"songID":"37471789","streamKey":"96a034ccf610cf384e201cdf4413bf1359696e36_5324731c_23bc62d_2acf683_11b0b5b1b_8_0","streamServerID":"67108864","autoplayState":""}
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
		    
	    String requestBody = "{\"method\":\"markStreamKeyOver30Secs\",\"header\":{\"wsKey\":\""+keyVector[1]+"\", \"sessionID\": \""+sessionID+"\"},\""+
	    "parameters\":{\"streamKey\":\""+streamKey+"\", \"streamServerID\": \""+streamServerID+"\", \"songID\": \""+songID+"\"}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
				
		String response = requestAPI(streamingBaseURL+sig+"|"+requestBody, "UTF-8", false);
		return	(response.contains("success"))?"success":"error";
	
	}
	
	// Metodos relativos ao login do serviï¿½o de streaming
	public static String requestStreamAuthenticate(String sessionID,String login, String password){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		//{"method":"authenticate","header":{"wsKey":"portele_ricardo","sessionID":"a31e7e05a1756cb43960294246d515d0"},"parameters":{"login":"blabla","password":"blabla"}}
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
	    String requestBody = "{\"method\":\"authenticate\",\"header\":{\"wsKey\":\""+keyVector[1]+"\", \"sessionID\": \""+sessionID+"\"},\""+
	    	    "parameters\":{\"login\":\""+login+"\", \"password\": \""+password+"\"}}";
	    		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
	    				
	    String response = requestAPI(streamingBaseURL+sig+"|"+requestBody, "UTF-8", false);   
	    
		return "";
	}
	
	public static String requestStreamRegisterNewUser(){
		return "";
	}
	
	
	public static String requestAudioPreviewUrlAPI(String songTitle_Artist){
		
		return requestAPI(ApiUrlMaker.makeAudioPreviewURL(songTitle_Artist), "UTF-8", true);
	}
	
	public static String requestAudioPreviewUrl2API(String songTitle_Artist){
		
		return requestAPI(ApiUrlMaker.makeAudioPreviewURL2(songTitle_Artist), "UTF-8", true);
	}
	
	public static String requestAIAPI(String text){	
		return requestAPI( ApiUrlMaker.makeAIApiURL(text), "UTF-8", true );
	}            
	
	public static String requestVendasAPI(String countryCode,String text, String priceRange, String offsetVendas ){
		return requestAPI( ApiUrlMaker.makeVendasURL(countryCode, text,priceRange,offsetVendas), "UTF-8", true );
	}
	
	public static String requestLyricsAPI(String text, String artistName){
		return requestAPI( ApiUrlMaker.makeLyricsURL(text,artistName), "UTF-8", true );
	}
	
	/*public static  String request2LyricsAPI(String track_id){
		return requestAPI( ApiUrlMaker.makeLyricsURL2(track_id), "ISO-8859-1" );
	}*/
	
	public static String requestPlacesGeocodingAPI(String address){
		return requestAPI( ApiUrlMaker.makePlacesGeocodingURL(address), "UTF-8", true );
	}
	
	public static String requestPlacesGeonamesAPI(){
		return requestAPI( ApiUrlMaker.makePlacesGeonamesURL(), "UTF-8", true );
	}
	
	public static String requestPlacesGeonamesStatesAPI(String geonameId){
		return requestAPI( ApiUrlMaker.makePlacesStatesGeonamesURL(geonameId), "UTF-8", true );
	}
	
	public static String requestPlacesGeocoding2API(String address){
		return requestAPI( ApiUrlMaker.makePlacesGeocodingBingURL(address), "UTF-8", true );
	}
	
	public static String requestPlacesSuggestionsAPI(String latAndLong, String countryCode, String section, String paginationIndex){
		return requestAPI( ApiUrlMaker.makePlacesSuggestionsURL(latAndLong, countryCode,section, paginationIndex), "UTF-8", true );
	}
	
	public static String requestPlacesNearOfMeAPI(String latAndLong,String countryCode, String section,String paginationIndex){
		return requestAPI( ApiUrlMaker.makePlacesNearOfMeURL(latAndLong,countryCode, section, paginationIndex), "UTF-8", true );
	}
	
	
	public static String requestSendSmsAPI(String dest, String message){
		
		String response = requestAPI( ApiUrlMaker.makeSendSmsURL(dest, message), "UTF-8", false ); // POST request
		
		log.debug("--> TRYING SEND SMS MESSAGE [ "+message+" ] TO DEST: "+dest+":");
		
		return response;
		
	}
	
	public static String requestShortenerUrlAPI(String longURL){
				
		String response = requestAPI( ApiUrlMaker.makeShortnerApiURL(longURL), "UTF-8", false); // POST request
		
		return response;
	}
	
	public static String requestChatWeb(String chatActionType, Properties chatParameters){
		 return requestAPI( ApiUrlMaker.makeChatWebGenericURL(chatActionType, chatParameters), "UTF-8", true );
	    	
	}
	
	public static String requestWorldCupWeb(String worldCupActionType, Properties worldCupParameters){
		return requestAPI( ApiUrlMaker.worldCupWebGenericURL(worldCupActionType, worldCupParameters), "UTF-8", true);
	}
	
	public static String requestPortalWebBanners(String ip, String userAgent,String browser, boolean isBannerWeb,String bannerWebImageSizePosfix){
		return requestAPI(ApiUrlMaker.makePortalWebBannersAPIURL(ip, userAgent, browser,isBannerWeb,bannerWebImageSizePosfix),"UTF-8", true);
	}
	
	/** Funï¿½ï¿½o que faz um request HTTP para um link genï¿½rico passado por parï¿½metro e retorna o resultado em forma de string
	 * 
	 * @param apiRequestURL
	 */
    private static String requestAPI(String apiRequestURL, String charset, boolean isGetRequest){
    	    	
        String result = "";
        String parametersURL = "";
               
        try {
        	
        
        	 if(!isGetRequest){
        		 
        		 if(!apiRequestURL.contains("urlshortener")){
        			 
        			 if(apiRequestURL.contains("ws3.php")){
        				 
        				 String[] ok = apiRequestURL.split("\\|");
        				 apiRequestURL = ok[0].replace("#", "?");
        				 log.debug("\n--> apiRequestURL: "+apiRequestURL);
        				 parametersURL = ok[1];
        				 log.debug("\n--> parametersURL: "+parametersURL);
        				 
        			 }else{
        			 
		        		 // No caso de um request post os parametros sï¿½o passados em uma string separada da url base
		        		 parametersURL = apiRequestURL.substring(apiRequestURL.indexOf("?")+1,apiRequestURL.length());
		        		 log.debug("\n--> parametersURL: "+parametersURL);
		        		 apiRequestURL = apiRequestURL.substring(0, apiRequestURL.indexOf("?"));
		        		 log.debug("\n--> apiRequestBaseURL: "+apiRequestURL);
	        		 
        			 }
        		 }else{
        			        			
        			parametersURL = apiRequestURL.substring(apiRequestURL.indexOf("|")+1,apiRequestURL.length());
        			log.debug("\n--> parametersURL: "+parametersURL);
        			apiRequestURL = apiRequestURL.substring(0, apiRequestURL.indexOf("|"));
       			 	log.debug("\n--> apiRequestBaseURL: "+apiRequestURL);
        		 }
        	 }   
        	 
        	 log.debug("\n--> INICIANDO REQUEST PARA API VIA URL:  "+apiRequestURL);
        	 
        	 requestUrl = new URL(apiRequestURL);
        	        
        	 //log.debug("\n--> URL PARA API CRIADA COM SUCESSO!!");
        	 
        	 connection =  (HttpURLConnection) requestUrl.openConnection();
             //log.debug("\n--> CONEXï¿½O COM URL DA API CRIADA COM SUCESSO!!");
        	
        	 // setamos que o metodo do request ï¿½ o GET
        	 //connection.setRequestProperty("Request-Method", "GET"); 
        	 if(isGetRequest)
        		 connection.setRequestMethod("GET");
        	 else
        		 connection.setRequestMethod("POST");
        	 
          	   
        	 connection.setRequestProperty("Accept-Charset",charset); // ISO-8859-1
        	 
        	 //Caso especï¿½fico da API de URL Shortener do Google**
        	 if(apiRequestURL.contains("urlshortener") || apiRequestURL.contains("ws3.php")){
        		 
        		 connection.setRequestProperty("Content-Type","application/json"); 
        		 
        		 if(apiRequestURL.contains("urlshortener"))
        			 parametersURL = parametersURL.replaceAll("INTER","?").replaceAll("ECOMMER", "&");
        		
        	 }
        	
        	 if(!isGetRequest){
	        	// Send post request
	        	connection.setDoOutput(true);
	     		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	     		wr.writeBytes(parametersURL);
	     		wr.flush();
	     		wr.close();
        	 }
        	 
             // seta a variavel para ler o resultado. Indicamos que queremos uma resposta (Input) e nï¿½o queremos enviar dados
        	 // (output)
            // connection.setDoInput(true);  
            // connection.setDoOutput(false);  
                               
        	
        	// connection.connect();
			 //log.debug("\n--> CONEXï¿½O COM URL DA API FEITA COM SUCESSO!!");
        	
			 InputStream connectionInputStream = connection.getInputStream();			 
			 log.debug("\n--> OBTIDO INPUTSTREAM DA CONEXï¿½O!!");
			 
			 InputStreamReader connectionInputStreamReader = new InputStreamReader(connectionInputStream);
			 log.debug("\n--> OBTIDO INPUTSTREAMREADER DA CONEXï¿½O!!");
			 
        	 BufferedReader br =  new BufferedReader(connectionInputStreamReader);
			 log.debug("\n--> CONEXï¿½O COM RESULTADO DO ACESSO A API FEITA COM SUCESSO!!");
			 
			 StringBuffer newData = new StringBuffer();//new StringBuffer(20000000); 			
		     String s = "";  
		     
		     // montando o resultado do request
		     while (((s = br.readLine())) != null) {  
				    newData.append(s);  
		    	    //result+=s;
			 }				
			 br.close();
			 connectionInputStreamReader.close();
			 connectionInputStream.close();
				
			 result = new String(newData); 
			 // imprime o codigo resultante  
		     //log.debug("\nRESULTADO DO REQUEST: "+result);  
		     
		    		     
		     // imprime o numero do resultado  
		     log.debug("\nCODIGO DE RESULTADO DO REQUEST: "  
		                + connection.getResponseCode()  
		                + "/"  
		                + connection.getResponseMessage());
		     
		     resultCode = connection.getResponseCode() ;
		     
		     connection.disconnect();
		     
		}catch (MalformedURLException e) {			
			log.error("\n--> EXCECAO DURANTE CHAMADA: MalformedURLException");
			if(apiRequestURL.contains("aiyhMailServlet")){
				return "emailError";
            }			
			return "";
			
		}catch (ConnectException e){
			log.error("\n--> EXCECAO DURANTE CHAMADA: ConnectException");
			if(apiRequestURL.contains("aiyhMailServlet")){
				return "emailError";
            }	
			return "";
			//e.printStackTrace();
		}
    	catch (IOException e) {
    		log.error("\n--> EXCECAO DURANTE CHAMADA: IOException");
			if(apiRequestURL.contains("aiyhMailServlet")){
				return "emailError";
            }	
			return "";
			//e.printStackTrace();
		} 
              
          
        return result;
    }
    
    public static String requestStatusAPIs(boolean isAccessByPortalWeb){
    	
    	log.debug("\n--> Request Status API!!");
    	String audioActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_AUDIO_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_AUDIO_ACTIVE);
    	String videoActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_VIDEO_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_VIDEO_ACTIVE);
    	String lyricActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_LYRIC_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_LYRIC_ACTIVE);
    	String bookActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_BOOK_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_BOOK_ACTIVE);
    	String placeSugActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACE_SUG_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACE_SUG_ACTIVE);
    	String placeNearActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACE_NEAR_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACE_NEAR_ACTIVE);
    	String weatherActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_WEATHER_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_WEATHER_ACTIVE);
    	String directionsActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_ACTIVE);
    	String goodsActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_API_GOODS_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_API_GOODS_ACTIVE);
    	String chatActive = (System.getProperty(AllInYourHandsConstants.PROPERTY_CHAT_WEB_ACTIVE) == null)?"0": System.getProperty(AllInYourHandsConstants.PROPERTY_CHAT_WEB_ACTIVE);
    	
    	int streamingEnabled = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_ENABLED));
    	String streamingSessionID = "";
    	if(streamingEnabled == 1){
    		//if(!isAccessByPortalWeb)
    		  //	 streamingSessionID = ResponseFormater.formaterStreamingSessionID_response(RequestManager.requestNewSessionIdValue());
    		//DEVIDO AO FIM DO GROOVESHARK...investigando outra API substituta
    	}
    	
    	return  "<statusAPIs>"+
    			 "<audio>"+ audioActive  +"</audio>"+
    	         "<video>"+ videoActive +"</video>"+
    			 "<lyric>" + lyricActive + "</lyric>"+
    	         "<book>" + bookActive +"</book>"+
    	         "<placeSug>" + placeSugActive + "</placeSug>" +
    	         "<placeNear>" +  placeNearActive + "</placeNear>" +
    	         "<weather>" + weatherActive + "</weather>" +
    	         "<directions>" + directionsActive + "</directions>" +
    	         "<goods>" + goodsActive + "</goods>" +
    	         "<chat>" + chatActive + "</chat>" +
    	         "<streamingSessionID>" +streamingSessionID+ "</streamingSessionID>" +
    	         "<billingKey>"+System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_BLIG_KEY)+"</billingKey>"+
    	         "<gcmProjectNumber>"+System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_GCM_PROJECT_NUMBER)+"</gcmProjectNumber>"+
    	         "</statusAPIs>";
    	       
    	    	
    }
          
    
	public static String requestMailService(String subject, String message, String destMail){
		
		message = message.replaceAll("\n"," ").replaceAll("//","");
		String result = requestAPI(ApiUrlMaker.makeMailServiceURL(subject, message, destMail),"UTF-8",true);
		if(result.equals("emailError")){
		    
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
			Date currentDate = new Date();
			// escrevemos um arquivo com os emails com falha no envio			
			try{
				File emailErrorFile = new File(System.getProperty("ricardombertani.projetos.allinyourhands.emailerror.file.path"));
				if(!emailErrorFile.exists()){
					
					 if(emailErrorFile.createNewFile()){
						 log.debug("\n-->New Email Error File created!");
					}
									
				}							
				
				// salvando registros em arquivo
				OutputStream os = (OutputStream) new FileOutputStream(emailErrorFile,true);
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
				PrintWriter printWriter = new PrintWriter(osw);		
				
				String messageFields[] = message.split("¬");
				
				printWriter.println(System.getProperty("line.separator")+System.getProperty("line.separator")+"Message Date: "+ sdf.format(currentDate)+System.getProperty("line.separator")+"name: "+messageFields[0]+System.getProperty("line.separator")+"e-mail: "+messageFields[1]+System.getProperty("line.separator")+"message: "+messageFields[2]);		
				printWriter.close();
				osw.close();
				os.close();
				log.debug("\n-->Email Message Register was saved with success!!");
		
			}catch(IOException e){
				e.printStackTrace();
				
				log.debug("\n--->Error during the record of Email Message Register!");
			}
		}
		
		return "please, verify the log for more information"; 
		
	}
    
    public static String getFieldFromHTML(String htmlURL){
    	try {  
            
            URL url = new URL(htmlURL);  

            InputStream is = url.openStream();   
            DataInputStream dis = new DataInputStream(new BufferedInputStream(is));  
            String s = "";  
              
            while ((s = dis.readLine()) != null) {  
               //log.debug("\n\n--->") 
            }  

            is.close();  
              
    	} catch (MalformedURLException e) {  
    		e.printStackTrace();  
    	} catch (IOException e) {  
    		e.printStackTrace();  
    	}  
    	
    	return "";
    }
    
    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
          SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
          Mac mac = Mac.getInstance(algo);
          mac.init(key);

          byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

          StringBuffer hash = new StringBuffer();
          for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
              hash.append('0');
            }
            hash.append(hex);
          }
          digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        } catch (InvalidKeyException e) {
        	e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }
        return digest;
      }
   
	
	
}
