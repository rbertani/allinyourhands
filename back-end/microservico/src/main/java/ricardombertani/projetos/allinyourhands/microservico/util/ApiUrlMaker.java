package ricardombertani.projetos.allinyourhands.microservico.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ApiUrlMaker {

	private  static Logger log = Logger.getLogger(ApiUrlMaker.class.getName());


	public static String makeApiURL(String baseURL, Properties parameters){

		// o iterador, para criar a URL
		Iterator i = parameters.keySet().iterator();
		// o contador
		int counter = 0;
		// enquanto ainda existir parametros
		while (i.hasNext()) {

			// pega o nome
			String name = (String) i.next();
			// pega o valor
			String value = parameters.getProperty(name);

			// adiciona com um conector (? ou &)
			// o primeiro � ?, depois s�o &
			baseURL += (++counter == 1 ? "?" : "&")
					+ name
					+ "="
					+ value;
		}
		return baseURL;
	}

	public static String googleKeyReservBalancer(String keyuReservSelector,String key, String key1, String key2, String key3){

		log.log(Level.INFO,"\n-->Using googleKeyReservBalancer(): ");

		int rangeLimit = Integer.parseInt(keyuReservSelector);
		Random randomGenerator = new Random();
		int keyIndex = randomGenerator.nextInt(rangeLimit);

		switch(keyIndex){
			case 0:
				log.log(Level.INFO,"Using PROPERTY_API_GOOGLE_KEY ");
				return key;
			case 1:
				log.log(Level.INFO,"Using PROPERTY_API_GOOGLE_KEY1 ");
				return key1;
			case 2:
				log.log(Level.INFO,"Using PROPERTY_API_GOOGLE_KEY2 ");
				return key2;
			case 3:
				log.log(Level.INFO,"Using PROPERTY_API_GOOGLE_KEY3 ");
				return key3;

			default:
				log.log(Level.INFO,"Using PROPERTY_API_GOOGLE_KEY[Default]");
				return key;
		}

	}

	public  static Map<String, String> getParametersFromUrl(String url)
	{
		Map<String, String> map = new HashMap<String, String>();

		String[] params = url.split("[&,?]");
		for (String param : params)
		{
			try {
				String name = param.split("=")[0];
				String value = param.split("=")[1];
				map.put(name, value);
			} catch (Exception e)
			{
				log.log(Level.INFO,"\n--> getParametersFromUrlNo(): no value for parameter");
			}
		}

		return map;
	}

	/*
	public  String makeBooksApi2URL(String text, String offset){
		String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPEN_LIBRARY_BASE_URL);		
		// os parametros desta API 
		Properties parameters = new Properties();			
		parameters.setProperty("q",text); 
		parameters.setProperty("limit", System.getProperty(AllInYourHandsConstants.PROPERTY_API_BOOKS_RESULTS_PER_PAGE_LIMIT)); 
		parameters.setProperty("offset",offset);  
		

		return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+");   //O request 
	}
			
	public  String makeBooksApi2_DetailsURL(String editionKey){
		String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPEN_LIBRARY_DETAILS_URL);
		baseURL += editionKey+".json";
		// os parametros desta API 
		Properties parameters = new Properties();
				  
		return  makeApiURL(baseURL, parameters);
	}
	
	public  String makeDirectionsApiURL(String origin, String destination, String mode, String language){	
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("origin",origin);  
		  parameters.setProperty("destination",destination);  	
		  parameters.setProperty("sensor","false");  // true se a pesquisa estiver sendo feita de um cell, false caso contrario
		  parameters.setProperty("mode",mode);  //driving , walking,bicycling ,transit 
		  parameters.setProperty("language", language);
		  return (makeApiURL(baseURL, parameters)).replaceAll("  ", " ").replaceAll(" ", "+"); // temos problema no request da API do google maps se n�o
		                                                                                      // adicionarmos o caracter "+" no lugar de espa�os
		                                                                                        
	} 
	
	public  String makeDirectionsMapApiURL(String initialAndFinalLatLongFormat, String pathLatLongFormat){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_MAP_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		 // parameters.setProperty("center",centerAdressLatLongFormat);  
		  //parameters.setProperty("zoom",String.valueOf(zoomValue));  	
		  parameters.setProperty("sensor","false");  // true se a pesquisa estiver sendo feita de um cell, false caso contrario
		  parameters.setProperty("size",System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_MAP_IMAGEMAP_SIZE));  
		  parameters.setProperty("visual_refresh", "true"); // para uma visualiza��o mais apurada do mapa
		  parameters.setProperty("maptype", "roadmap\\");
		  parameters.setProperty("markers", "color:red"+initialAndFinalLatLongFormat);
		  parameters.setProperty("format", "png");
		  parameters.setProperty("path", "color:0x0000ff|weight:5"+pathLatLongFormat); // path � algo como: |40.737102,-73.990318|40.749825,-73.987963|40.752946,-73.987384|40.755823,-73.986397
		  parameters.setProperty("key", googleKeyReservBalancer());
		  return (makeApiURL(baseURL, parameters)).replaceAll("  ", " ").replaceAll(" ", "+"); // temos problema no request da API do google  maps se n�o
		                                                                                      // adicionarmos o caracter "+" no lugar de espa�os
		                                                                                        
	} 
	
	public  String makeDirectionsMapOnePlacePointApiURL(String placeLatLongFormat){	
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_MAP_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("sensor","false");  // true se a pesquisa estiver sendo feita de um cell, false caso contrario
		  parameters.setProperty("size","640x640");  
		  parameters.setProperty("visual_refresh", "false"); // para uma visualiza��o mais apurada do mapa
		  parameters.setProperty("maptype", "roadmap\\");
		  parameters.setProperty("markers", "color:red"+"|"+placeLatLongFormat+"|"); // formato |40.737102,-73.990318|
		  parameters.setProperty("format", "png");		
		  parameters.setProperty("key", googleKeyReservBalancer());
		  
		  return (makeApiURL(baseURL, parameters)).replaceAll("  ", " ").replaceAll(" ", "+"); // temos problema no request da API do google  maps se n�o
		                                                                                      // adicionarmos o caracter "+" no lugar de espa�os
		                                                                                        
	} 
	
	public  String makeDirectionsStreetViewApiURL(String currentPosition){	
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_STREETVIEW_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("size", "400x400");
		  parameters.setProperty("location", currentPosition);//coordenadas no formato 40.737102,-73.990318
		  parameters.setProperty("fov", "90");
		  parameters.setProperty("heading","235");
		  parameters.setProperty("pitch","10");
		  parameters.setProperty("sensor","false");
		  parameters.setProperty("key", googleKeyReservBalancer());
		  return (makeApiURL(baseURL, parameters)).replaceAll("  ", " ").replaceAll(" ", "+"); // temos problema no request da API do google  maps se n�o
		                                                                                      // adicionarmos o caracter "+" no lugar de espa�os
		                                                                                        
	} 
	
	public  String makeDirectionsTimeZoneApiURL(String currentPosition){
		
		String baseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_TIMEZONE_BASE_URL);
		
		 Properties parameters = new Properties();
		 parameters.setProperty("location", currentPosition);
		 parameters.setProperty("timestamp", ""+ ((new Timestamp( (new Date()).getTime() )).getTime() )/100);
		 parameters.setProperty("sensor","false");
		 parameters.setProperty("key", googleKeyReservBalancer());
		
		 return (makeApiURL(baseURL, parameters));
	}
	
	public  String makeYahooApiURL(String text, String region){	
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_YAHOO_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("appid",System.getProperty(AllInYourHandsConstants.PROPERTY_API_YAHOO_APPID));   // meu id para aplicativo do yahoo
		  parameters.setProperty("query",text); 
		  parameters.setProperty("region",region); // prefixo da regi�o

		  return makeApiURL(baseURL, parameters);
	} 
	
	public  String makeWeatherApiURL(String latLong, String lang){	
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPENWEATHER_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  //parameters.setProperty("q",text);  
		  parameters.setProperty("lat", latLong.split(",")[0]);
		  parameters.setProperty("lon", latLong.split(",")[1]);
		  parameters.setProperty("mode","json"); // o modo de retorno da API decidimos por xml  	
		  parameters.setProperty("lang", lang); // lang � o prefixo da regi�o
		  parameters.setProperty("APPID", System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPENWEATHER_API_KEY));
		  parameters.setProperty("cnt", "7"); // previs�o para 7 dias

		  return makeApiURL(baseURL, parameters);
	} 
	  
	public  String makeVideoApiURL(String text, String categoryId, String  pageToken){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_YOUTUBE_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("part","snippet");  // valor default (pode ser alterado, mas funciona bem assim)
		  parameters.setProperty("q",text); 
		  parameters.setProperty("key", googleKeyReservBalancer());
		  parameters.setProperty("type", "video"); // por padr�o mantemos o tipo como v�deo
		  parameters.setProperty("maxResults", System.getProperty(AllInYourHandsConstants.PROPERTY_API_YOUTUBE_GOOGLE_RESULTS_PER_PAGE_LIMIT)); // quantidade maxima de resultados por pagina
		  // parameters.setProperty("videoCategoryId", categoryId);   Removida flag de categoria em 10/02/2016 - ouve uma alteracao na api do youtube, este campo nao é mais aceito estando vazio
		  parameters.setProperty("safesearch","strict");
		 // parameters.setProperty("regionCode", regionCode);
			

		  parameters.setProperty("pageToken", pageToken); // parametro que ficar� responsavel por sempre mostrar o proximo request, o token � obtido atrav�s do campo "nextPageToken"
		  
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); // substituimos espa�os por "+" por um problema no request das APIs da Google
		  // para justin tv: return baseURL+text+".xml";
	}
	
	public  String makeVideoCategoriesApiURL(String regionCode){	
		
		 String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_YOUTUBE_VIDEO_CATEGORIES_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("part","snippet");  
		  String correctRegionCode = (!regionCode.equals("pt"))?"us":"pt";
		  parameters.setProperty("regionCode",correctRegionCode);		
		  parameters.setProperty("hl",regionCode); // parametro que faz com que os resultados fiquem na linguagem correta
		  parameters.setProperty("key", googleKeyReservBalancer());	  	
		  
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+");
		
	}
	  
	public  String makeAudioApiURL(String text, int pageNumber){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_BASE_URL);  		  
		  baseURL = baseURL.replaceAll("query", text);
		  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("format","json");
		  parameters.setProperty("key",System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_KEY));
		  parameters.setProperty("limit", String.valueOf( pageNumber*(Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_RESULTSPERPAGE_LIMIT))) ) );
		
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
	
	public  String makeAudioPreviewURL(String text){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_BASE_URL);  		  
		 
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("method", "track.search");
		  parameters.setProperty("track", text); // nome da musica + artista
		  parameters.setProperty("format","json");
		  parameters.setProperty("api_key",lastFMKeyReservBalancer());
		  parameters.setProperty("limit", "1");
		
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
	
	public  String makeAudioPreviewURL2(String text){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_APPLE_ITUNES_BASE_URL);  		  
		 
		  // os parametros desta API 
		  Properties parameters = new Properties();		 
		  parameters.setProperty("term", text); // nome da musica + artista
		  parameters.setProperty("limit","1");		  
		 		
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
	
	//Metodo atual utilizado para montar a url da api do spotify
	public  String makeAudioSpotifyApiURL(String text, int pageNumber, String access_token){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_SPOTIFY_BASE_URL);  		  
		 		  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("q",text);
		  parameters.setProperty("key",System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_KEY));
		  parameters.setProperty("limit", String.valueOf( pageNumber*(Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_RESULTSPERPAGE_LIMIT))) ) );
		  parameters.setProperty("offset", String.valueOf(pageNumber) );
		  parameters.setProperty("type","track");
		  parameters.setProperty("access_token", access_token);
		  
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
	
	public  String makeTopArtistsByCategoryApiURL(String text, int pageNumber, boolean isWebVersion){
		  String baseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_BASE_URL);  		  
		 		  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("format","json");
		  parameters.setProperty("api_key",lastFMKeyReservBalancer());
		  parameters.setProperty("method", "tag.gettopartists");
		  if(isWebVersion){
			  parameters.setProperty("limit", String.valueOf( pageNumber*(Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_ARTISTS_BY_CATEGORY_PAGINATION_WEBLIMIT))) ) );
		  }else{
			  parameters.setProperty("limit", String.valueOf( pageNumber*(Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_ARTISTS_BY_CATEGORY_PAGINATION_LIMIT))) ) );
		  }
		  parameters.setProperty("tag", text); // tag � considerada como a categoria
		
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
	
	public  String makeTopCategoriesApiURL(){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_BASE_URL);  		  
		 		  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("format","json");
		  parameters.setProperty("api_key",lastFMKeyReservBalancer());
		  parameters.setProperty("method", "tag.getTopTags");
		 
		
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
	

	
	public  String makeAIApiURL(String text){	
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_PERSONALITYFORGE_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("apiKey",System.getProperty(AllInYourHandsConstants.PROPERTY_API_PERSONALITYFORGE_APIKEY));  
		  parameters.setProperty("hash", System.getProperty(AllInYourHandsConstants.PROPERTY_API_PERSONALITYFORGE_APISECRET));
		  parameters.setProperty("message", text); 
		  return makeApiURL(baseURL, parameters);
	}          
	
	public  String makeVendasURL(String countryCode,String text, String priceRange, String offsetVendas){	
		
		// Paises aceitos pela API (n�o aceita paises americanos, apenas latino americanos
		Hashtable countryCodeTable = new Hashtable();
		countryCodeTable.put("br", "MLB");
		countryCodeTable.put("ar", "MLA");
		countryCodeTable.put("co", "MCO");
		countryCodeTable.put("cr", "MCR");
		countryCodeTable.put("ec", "MEC");		
		countryCodeTable.put("cl", "MLC");
		countryCodeTable.put("mx", "MLM");
		countryCodeTable.put("uy", "MLU");
		countryCodeTable.put("ve", "MLV");
		countryCodeTable.put("pa", "MPA");
		countryCodeTable.put("pe", "MPE");
		countryCodeTable.put("pt", "MPT");
		countryCodeTable.put("do", "MRD");
		
		 String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_MERCADO_LIVRE_BASE_URL);  		
		if(countryCodeTable.get(countryCode) != null){
			baseURL = baseURL.replaceAll("MLB", (String)countryCodeTable.get(countryCode));
		}
		
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("q",text); 
		  parameters.setProperty("price", priceRange);
		  parameters.setProperty("limit", System.getProperty(AllInYourHandsConstants.PROPERTY_API_MERCADO_LIVRE_RESULTS_PER_PAGE_LIMIT)); // Valor do Limite de resultados retornados
		   
		  int offsetVendasInt = Integer.parseInt(offsetVendas);
		  
		  // incrementar com o valor do "limit"
		  offsetVendasInt += Integer.valueOf(System.getProperty(AllInYourHandsConstants.PROPERTY_API_MERCADO_LIVRE_RESULTS_PER_PAGE_LIMIT));
		 
		  parameters.setProperty("offsetVendas", String.valueOf(offsetVendasInt));
		  parameters.setProperty("callback", "foo"); // parametro necessario para retornar a resposta em json (sem html)
		  
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+");
	}
	
	public  String makeLyricsURL(String text, String artistName){
		 String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_LYRICS_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("q_track",text); 
		  parameters.setProperty("q_artist", artistName);
		  parameters.setProperty("apikey", System.getProperty( AllInYourHandsConstants.PROPERTY_API_LYRICS_API_KEY ));
		  //parameters.setProperty("f_has_lyrics", String.valueOf(1)); // indicamos que somente queremos no resultados musicas que possuem letras
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+");
	}
	

	public  String makeSendSmsURL(String dest, String message){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API2_SEND_MESSAGES_BASE_URL);
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  //parameters.setProperty("Key",System.getProperty(AllInYourHandsConstants.PROPERTY_API_SEND_MESSAGES_API_KEY)); 
		 // parameters.setProperty("To", dest);
		  //parameters.setProperty("Content", message);
		  parameters.setProperty("access_token", System.getProperty(AllInYourHandsConstants.PROPERTY_API2_SEND_MESSAGES_API_KEY));
		  parameters.setProperty("send_to", "post_contacts");
		  parameters.setProperty("message", message);
		  parameters.setProperty("post_contacts", "[ \""+dest+"\" ]");
		  
		  return makeApiURL(baseURL, parameters);//.replaceAll("  ", " ").replaceAll(" ", "+"); // substituimos espa�os por "+" por um problema no request das APIs da clockworksms;
	}
		
	
	public  String makePlacesGeocodingURL(String address){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEOCODING_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("address",address); 
		  parameters.setProperty("sensor", "false"); // setamos o sensor aqui para false pois executamos esta api do servidor e n�o de um cel
		  parameters.setProperty("key", googleKeyReservBalancer());		  
		 		  
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); // substituimos espa�os por "+" por um problema no request das API do google
	}
	
	public  String makePlacesGeonamesURL(){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEONAMES_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("username",System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEONAMES_USERNAME));   
		  parameters.setProperty("charset", "UTF8");
		 		  
		  String finalURL = makeApiURL(baseURL, parameters);
		 		  
		  return finalURL; 
	}
	
	public  String makePlacesStatesGeonamesURL(String geonameId){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEONAMES_CHILDREN_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("username",System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEONAMES_USERNAME));   
		  parameters.setProperty("geonameId",geonameId);
		  parameters.setProperty("maxRows", "600");
		  parameters.setProperty("charset", "UTF8");
		  
		  String finalURL = makeApiURL(baseURL, parameters);
		  
		  return finalURL; 
	}
	
	public  String makePlacesGeocodingBingURL(String address){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEOCODING_BING_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("query",address); 
		  parameters.setProperty("maxResults", "5");
		  parameters.setProperty("key", System.getProperty(AllInYourHandsConstants.PROPERTY_API_PLACES_GEOCODING_BING_API_KEY));
		 		  
		 		  
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); // substituimos espa�os por "+" por um problema no request das API do google
	}
	

	public  String makePortalWebBannersAPIURL(String ip, String userAgent,String browser, boolean isBannerWeb, String bannerWebImageSizePosfix){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_PORTALWEB_BANNERS_BASE_URL);  		  
		  // os parametros desta API 
		  Properties parameters = new Properties();
		  parameters.setProperty("get","rich");
		  parameters.setProperty("partnerid",System.getProperty(AllInYourHandsConstants.PROPERTY_PORTALWEB_BANNERS_PARTNERID)); 
		  parameters.setProperty("ip", ip);
		  parameters.setProperty("ua", userAgent);
		  //parameters.setProperty("hash",System.getProperty(AllInYourHandsConstants.PROPERTY_PORTALWEB_BANNERS_HASH_VALUE)); 
		  //parameters.setProperty("cntr",countryCode); 
		  parameters.setProperty("limit","1"); 
		  parameters.setProperty("limit",browser); 
		  if(isBannerWeb)
			  parameters.setProperty("imgsize", System.getProperty(AllInYourHandsConstants.PROPERTY_PORTALWEB_BANNERS_IMAGE_SIZE + "."+bannerWebImageSizePosfix) );
		  else 
			  parameters.setProperty("imgsize", System.getProperty(AllInYourHandsConstants.PROPERTY_PORTALWEB_BANNERS_MOBILE_IMAGE_SIZE));
		  parameters.setProperty("v","3"); // API version
		 
		
		  return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); // substituimos espa�os por "+" por um problema no request das API do google
	}
	

	public  String makeShortnerApiURL(String longURL){
		  String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_CHATWEB_REGISTER_EMAIL_SHORTENERURL_BASE_URL);  		  
		  
		  return baseURL + "|" + "{\"longUrl\": \""+longURL+"\","+"\"key\": \""+googleKeyReservBalancer()+"\"}";
		 	 
		
	}

	
	public  String makeMailServiceURL(String subject,String message, String destMail){
		
		String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_MAILSERVICE_BASE_URL);
		Properties parameters = new Properties();
		parameters.put("subject", subject);
		parameters.put("message", message);
		parameters.put("destMail", destMail);
		
		
		return makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); 
	}
		

	

	

	private  String lastFMKeyReservBalancer(){
		
		int rangeLimit = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_RESERV_SELECTOR));
		Random randomGenerator = new Random();
		int keyIndex = randomGenerator.nextInt(rangeLimit);
		
		switch(keyIndex){
			case 0:
				log.log(Level.INFO,"Using PROPERTY_API_LASTFM_KEY");
				return System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_KEY);
						
			case 1:
				log.log(Level.INFO,"Using PROPERTY_API_LASTFM_KEY2");
				return System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_KEY2);
		}
		
		return System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_KEY);
		 
		
	}
	

	
	public  String groovesharkStreamingKeyReservBalancer(){
		
		int rangeLimit = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_KEY_SELECTOR));
		Random randomGenerator = new Random();
		int keyIndex = randomGenerator.nextInt(rangeLimit);
		
		switch(keyIndex){
			case 0:
				log.log(Level.INFO,"Using PROPERTY_API_GROOVESHARK_KEY 1");
				return System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFICIAL_SECRET)+"|"+System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFICIAL_KEY);
						
			case 1:
				log.log(Level.INFO,"Using PROPERTY_API_GROOVESHARK_KEY 2");
				return System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFICIAL_SECRET2)+"|"+System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFICIAL_KEY2);
		}
		
		return System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFICIAL_SECRET)+"|"+System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFICIAL_KEY);
		 
		
	}
	*/
	
}
