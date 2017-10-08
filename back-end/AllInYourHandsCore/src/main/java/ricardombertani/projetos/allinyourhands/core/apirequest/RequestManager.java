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
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

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
		  
	public static int resultCode = 200;
	  
	public static HttpSession session;
		
	      
	public static String requestBooksAPI(String text, String startIndex, String countryCode){
		 
		return requestAPIbyGetMethod( ApiUrlMaker.makeBooksApiURL(text, startIndex,countryCode), "UTF-8" ); // no caso do google books serï¿½o necessï¿½rias vï¿½rias invocaï¿½ï¿½es
		                                                                   // devido ao alto numero de resultados, o startIndex deve ser incrementado 
		                                                                  // de 40 em 40 atï¿½ que o campo "totalItems" obtido no request seja igual ao
		                                                                 // numero de resultados vindos do request  (usar sessï¿½o para armazenar o ultimo 
                                                                         // "startIndex" e se houver outro request, entï¿½o incrementar o "startIndex" em 40)
		
	}
	
	public static String requestBooks2API(String text, String startIndex){
						
		return requestAPIbyGetMethod( ApiUrlMaker.makeBooksApi2URL(text, startIndex), "UTF-8" ) ;//ISO-8859-1
	}
	
	/** Funï¿½ï¿½o utilizada para efetuar um request para verificar se o livro em questï¿½o estï¿½ disponivel para leitura
	 * 
	 * @param bookCheckPartialURL
	 * @return
	 */
	public static String requestBooks2DetailsAPI(String editionKey){
		
		return requestAPIbyGetMethod( ApiUrlMaker.makeBooksApi2_DetailsURL(editionKey), "UTF-8"  );
	}
	        
	public  static String requestDirectionsAPI(String origin, String destination, String mode, String language){
		return requestAPIbyGetMethod( ApiUrlMaker.makeDirectionsApiURL(origin, destination, mode, language), "UTF-8" );
		
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
		return requestAPIbyGetMethod( ApiUrlMaker.makeDirectionsTimeZoneApiURL(currentPosition), "UTF-8"  );
	}
	             
	public static String requestYahooAPI(String text, String region){	
		return requestAPIbyGetMethod( ApiUrlMaker.makeYahooApiURL(text, region), "UTF-8" );
		
	}
	
	public static String requestWeatherAPI(String text, String lang){	
		return requestAPIbyGetMethod( ApiUrlMaker.makeWeatherApiURL(text, lang), "UTF-8" );
	}
	
	public static String requestVideoAPI(String text, String categoryId,/* String regionCode,*/String pageToken){	 // no caso do Youtube, como tratam-se de muitos Dados, ï¿½ necessario varias invocaï¿½ï¿½es
		                                                                     // a API para obter as diversas paginas de resultados atravï¿½s do parametro "pageToken"
		                                                                    // O parametro "pageToken" deverï¿½ receber a cada request  o valor do campo "nextPageToken"
		                                                                    // atï¿½ que este campo nï¿½o possua valor algum (usar sessï¿½o para armazenar o ultimo 
		                                                                    // "nextPageToken" e se houver outro request, entï¿½o usar esse nextPageToken)
		return requestAPIbyGetMethod( ApiUrlMaker.makeVideoApiURL(text,categoryId,/* regionCode,*/ pageToken), "UTF-8" );		
	}
	
	public static String requestVideoCategoriesAPI(String regionCode){	
      
       return requestAPIbyGetMethod( ApiUrlMaker.makeVideoCategoriesApiURL(regionCode), "UTF-8" );		
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
		
						
		return requestAPIbyGetMethod(ApiUrlMaker.makeAudioApiURL(text,pageNumber), "UTF-8");
	}
	
		
	public static String requestAudioSpotifyAPI(String text, int pageNumber){
		
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
     	RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
     	Request request = new Request.Builder()
     	     .url(System.getProperty(AllInYourHandsConstants.PROPERTY_API_SPOTIFY_ACCESSTOKEN_BASE_URL))
     	     .post(body)
     	     .addHeader("authorization", System.getProperty(AllInYourHandsConstants.PROPERTY_API_SPOTIFY_ACCESSTOKEN_AUTH_CODE) )
     	     .addHeader("content-type", "application/x-www-form-urlencoded")
     	     .addHeader("cache-control", "no-cache")
     	     .addHeader("postman-token", "cc5000a5-e0a7-a0b9-bd3c-98ca515b5dcb")
     	     .build();

    	String spotifyAccessTokenJsonResponse = requestAPIbyPostMethod(request,body, mediaType, "UTF-8");//requestAPI("https://accounts.spotify.com/api/token","UTF-8",false);
    	String access_token = "";
    	    	
    	try {
			JSONObject jsonObject = new JSONObject( spotifyAccessTokenJsonResponse  );
			access_token = jsonObject.getString("access_token");
		} catch (JSONException e) {
		 
			e.printStackTrace();
		}
				
		return requestAPIbyGetMethod(ApiUrlMaker.makeAudioSpotifyApiURL(text, pageNumber, access_token), "UTF-8");
		
	}
	
	public static String requestTopArtistsByCategoryAPI(String text, int pageNumber, boolean isWebVersion){
		
		return requestAPIbyGetMethod(ApiUrlMaker.makeTopArtistsByCategoryApiURL(text, pageNumber,isWebVersion), "UTF-8");
	}
	
	public static String requestTopCategoriesAPI(){
		
		return requestAPIbyGetMethod(ApiUrlMaker.makeTopCategoriesApiURL(), "UTF-8");
	}
	 
	
	public static String requestGetCountryValue(String ipAdress){
		
		String keyVector[] = ApiUrlMaker.groovesharkStreamingKeyReservBalancer().split("\\|");
		
		String secretKey = keyVector[0];
		//Teste de chamada de streaming
	    String streamingBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARKSTREAMING_BASE_URL)+"=";
		String requestBody = "{\"method\":\"getCountry\",\"header\":{\"wsKey\":\""+keyVector[1]+"\"},\"parameters\":{\"ip\":\""+ipAdress+"\"}}";
		String sig = hmacDigest(requestBody, secretKey, "HmacMD5");
		
		MediaType mediaType = MediaType.parse("application/json");
     	RequestBody body = RequestBody.create(mediaType, requestBody);
     	Request request = new Request.Builder()
     	     .url(streamingBaseURL+sig)
     	     .post(body)     	    
     	     .build();
    		
		
		return	requestAPIbyPostMethod(request,body, mediaType, "UTF-8");
			
	}
	
		
	public static String requestStreamRegisterNewUser(){
		return "";
	}
	
	
	public static String requestAudioPreviewUrlAPI(String songTitle_Artist){
		
		return requestAPIbyGetMethod(ApiUrlMaker.makeAudioPreviewURL(songTitle_Artist), "UTF-8");
	}
	
	public static String requestAudioPreviewUrl2API(String songTitle_Artist){
		
		return requestAPIbyGetMethod(ApiUrlMaker.makeAudioPreviewURL2(songTitle_Artist), "UTF-8");
	}
	
	public static String requestAIAPI(String text){	
		return requestAPIbyGetMethod( ApiUrlMaker.makeAIApiURL(text), "UTF-8" );
	}            
	
	public static String requestVendasAPI(String countryCode,String text, String priceRange, String offsetVendas ){
		return requestAPIbyGetMethod( ApiUrlMaker.makeVendasURL(countryCode, text,priceRange,offsetVendas), "UTF-8" );
	}
	
	public static String requestLyricsAPI(String text, String artistName){
		return requestAPIbyGetMethod( ApiUrlMaker.makeLyricsURL(text,artistName), "UTF-8" );
	}
	
	/*public static  String request2LyricsAPI(String track_id){
		return requestAPI( ApiUrlMaker.makeLyricsURL2(track_id), "ISO-8859-1" );
	}*/
	
	public static String requestPlacesGeocodingAPI(String address){
		return requestAPIbyGetMethod( ApiUrlMaker.makePlacesGeocodingURL(address), "UTF-8" );
	}
	
	public static String requestPlacesGeonamesAPI(){
		return requestAPIbyGetMethod( ApiUrlMaker.makePlacesGeonamesURL(), "UTF-8" );
	}
	
	public static String requestPlacesGeonamesStatesAPI(String geonameId){
		return requestAPIbyGetMethod( ApiUrlMaker.makePlacesStatesGeonamesURL(geonameId), "UTF-8" );
	}
	
	public static String requestPlacesGeocoding2API(String address){
		return requestAPIbyGetMethod( ApiUrlMaker.makePlacesGeocodingBingURL(address), "UTF-8" );
	}
	
	public static String requestPlacesSuggestionsAPI(String latAndLong, String countryCode, String section, String paginationIndex){
		return requestAPIbyGetMethod( ApiUrlMaker.makePlacesSuggestionsURL(latAndLong, countryCode,section, paginationIndex), "UTF-8" );
	}
	
	public static String requestPlacesNearOfMeAPI(String latAndLong,String countryCode, String section,String paginationIndex){
		return requestAPIbyGetMethod( ApiUrlMaker.makePlacesNearOfMeURL(latAndLong,countryCode, section, paginationIndex), "UTF-8" );
	}
	
	
	/*public static String requestSendSmsAPI(String dest, String message){
		
		String response = requestAPI( ApiUrlMaker.makeSendSmsURL(dest, message), "UTF-8", false ); // POST request
		
		log.debug("--> TRYING SEND SMS MESSAGE [ "+message+" ] TO DEST: "+dest+":");
		
		return response;
		
	}*/
	
	/*public static String requestShortenerUrlAPI(String longURL){
				
		String response = requestAPI( ApiUrlMaker.makeShortnerApiURL(longURL), "UTF-8", false); // POST request
		
		return response;
	}*/
	
		
	public static String requestPortalWebBanners(String ip, String userAgent,String browser, boolean isBannerWeb,String bannerWebImageSizePosfix){
		return requestAPIbyGetMethod(ApiUrlMaker.makePortalWebBannersAPIURL(ip, userAgent, browser,isBannerWeb,bannerWebImageSizePosfix),"UTF-8");
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
    	    	
    	String streamingSessionID = "";
    	
    	    	    	
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
		String result = requestAPIbyGetMethod(ApiUrlMaker.makeMailServiceURL(subject, message, destMail),"UTF-8");
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
	
	/** Funcao que faz um request HTTP para um link generico passado por parametro e retorna o resultado em forma de string. O metodo utilizado é o GET
	 * 
	 * @param apiRequestURL
	 */
    private static String requestAPIbyGetMethod(String apiRequestURL, String charset){
    	    	
       String result = "";
    	
           	   
       OkHttpClient client = new OkHttpClient();
    	   
       Request request = new Request.Builder()
    	    	     .url(apiRequestURL)
    	    	     .get().build();
    	   
       try {
    		   
    		   log.debug("\n--->CALLINNG THE FOLLOWING API RESOURCE:     GET  "+apiRequestURL);
    		   
    		   Response response = client.newCall(request).execute();
    		   result = response.body().string();
    		   
    		   log.debug("\n---> API RESPONSE: "+result);
    		   		 
		} catch (IOException e) {
				e.printStackTrace();
		}
    	   
       
    	           
        return result;
    }
    
    /** Funcao que faz um request HTTP para um link generico passado por parametro e retorna o resultado em forma de string. O metodo utilizado é o POST
	 * 
	 * @param apiRequestURL
	 */
    private static String requestAPIbyPostMethod(Request request,RequestBody body,MediaType mediaType, String charset){
    	
      OkHttpClient client = new OkHttpClient();
      String result = "";

  	   	   
  	   try {
  		   
  		   log.debug("\n--->CALLINNG THE FOLLOWING API RESOURCE:     POST  "+request.urlString());
  		   log.debug("\n WITH HEADERS: "+request.headers().toString());
  		      		   
  		   Response response = client.newCall(request).execute();
  		   result = response.body().string();
  		   
  		   log.debug("\n---> API RESPONSE: "+result);
  		   
  		    		   
		} catch (IOException e) {
				
				e.printStackTrace();
		}
  	   
  	   return result;
  	 
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
