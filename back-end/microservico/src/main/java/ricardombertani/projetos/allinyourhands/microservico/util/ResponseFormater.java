package ricardombertani.projetos.allinyourhands.microservico.util;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ricardombertani.projetos.allinyourhands.apidata.bannerweb.Banner;
import ricardombertani.projetos.allinyourhands.apidata.bannerweb.BannerCollection;
import ricardombertani.projetos.allinyourhands.apidata.book.Book;
import ricardombertani.projetos.allinyourhands.apidata.book.BooksVolumeCollection;
import ricardombertani.projetos.allinyourhands.apidata.book.ImageLink;
import ricardombertani.projetos.allinyourhands.apidata.book.VolumeInfo;
import ricardombertani.projetos.allinyourhands.apidata.directions.*;
import ricardombertani.projetos.allinyourhands.apidata.geonames.Country;
import ricardombertani.projetos.allinyourhands.apidata.geonames.CountryCollection;
import ricardombertani.projetos.allinyourhands.apidata.geonames.State;
import ricardombertani.projetos.allinyourhands.apidata.geonames.StateCollection;
import ricardombertani.projetos.allinyourhands.apidata.lyrics.Lyric;
import ricardombertani.projetos.allinyourhands.apidata.places.Place;
import ricardombertani.projetos.allinyourhands.apidata.places.Suggestion;
import ricardombertani.projetos.allinyourhands.apidata.places.SuggestionCollection;
import ricardombertani.projetos.allinyourhands.apidata.places.Tip;
import ricardombertani.projetos.allinyourhands.apidata.sales.Product;
import ricardombertani.projetos.allinyourhands.apidata.sales.ProductList;
import ricardombertani.projetos.allinyourhands.apidata.song.*;
import ricardombertani.projetos.allinyourhands.apidata.video.Video;
import ricardombertani.projetos.allinyourhands.apidata.video.VideoCollection;
import ricardombertani.projetos.allinyourhands.apidata.weather.Weather;

import java.util.logging.Level;
import java.util.logging.Logger;


/** Classe responsavel pela formatacao do resultado vindo do request atraves da classe RequestManager
 *   
 * @author Ricardo M. Bertani
 *
 */  
  

public class ResponseFormater {

	private static Logger log = Logger.getLogger(ResponseFormater.class.getName());
	
	private static XStream xstreamXML = new XStream(new DomDriver("UTF-8")){
	 
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

	private static boolean isNotNullOrEmptyThisArray(JSONArray array){

		if(array != null){
			if(array.length() > 0){
				return true;
			}
		}

		return false;
	}

	private static boolean isFieldNotNull(JSONObject jsonObject, String field){
		try {

			Object obj = jsonObject.get(field);

			return (obj != null)?true:false;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.log(Level.INFO,"\n--> Campo n�o encontrado: "+field);
		} catch(NullPointerException e){
			log.log(Level.INFO,"\n--> Problema ao ler o campo: "+field);
		}
		return false;
	}

	private static Object getFieldObject(JSONObject jsonObject, String field, String type){

		try {

			if(type.equals("string")){
				return (String)jsonObject.getString(field);
			}else if(type.equals("double")){
				return (Double)jsonObject.getDouble(field);
			}else if(type.equals("boolean")){
				return (Boolean)jsonObject.getBoolean(field);
			}else if(type.equals("int")){
				return (Integer)jsonObject.getInt(field);
			}else if(type.equals("jsonarray")){
				return (JSONArray)jsonObject.getJSONArray(field);
			}else return (JSONObject)jsonObject.getJSONObject(field);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(type.equals("string")){
				return "";
			}else if(type.equals("double")){
				return 0.0;
			}else if(type.equals("boolean")){
				return false;
			}else if(type.equals("int")){
				return 0;
			}else {
				return null;
			}

		}

	}

	public static BooksVolumeCollection formaterBooks1API_response(String response, String resultsPerPage){ // resposta em JSON

		JSONArray items = null;
		JSONObject jsonObject = null;

		BooksVolumeCollection booksVolumeCollection = new BooksVolumeCollection();


		try {

			jsonObject = new JSONObject(response);

			booksVolumeCollection.setTotalItems( Integer.parseInt(isFieldNotNull(jsonObject,"totalItems")?(String)getFieldObject(jsonObject,"totalItems","string"):"") );


			items = (JSONArray)jsonObject.get("items");

			if(isNotNullOrEmptyThisArray(items)){
				// Obtendo as informacoes de cada livro
				for(int i = 0; i < items.length(); i++){
					Book book = new Book();
					JSONObject jsonBookObject = items.getJSONObject(i);


					book.setId( isFieldNotNull(jsonBookObject,"id")?(String)getFieldObject(jsonBookObject,"id","string"):"" );
					book.setEtag( isFieldNotNull(jsonBookObject,"etag")?(String)getFieldObject(jsonBookObject,"etag","string"):"" );

					// Informacoes do Volume
					JSONObject volumeInfoJsonObject = isFieldNotNull(jsonBookObject,"volumeInfo")?(JSONObject)getFieldObject(jsonBookObject,"volumeInfo",""):null;
					JSONArray authors =  isFieldNotNull(volumeInfoJsonObject,"authors")?(JSONArray)getFieldObject(volumeInfoJsonObject,"authors","jsonarray"):null;
					String authorsFormated = "";
					if(authors != null){
						for(int j = 0; j < authors.length(); j++){
							if(j < (authors.length() - 1))
								authorsFormated += (String)authors.get(j) + "; ";
							else authorsFormated += (String)authors.get(j);
						}
					}

					boolean textReadingMode = false;
					boolean imageReadingMode = false;

					// obtendo atributos que determinarao de livro pode ser lido via navegador (se a leitura esta disponivel)
					JSONObject readingModes = isFieldNotNull(volumeInfoJsonObject,"readingModes")?(JSONObject)getFieldObject(volumeInfoJsonObject,"readingModes",""):null;
					if(readingModes != null){
						textReadingMode = isFieldNotNull(readingModes,"text")?(Boolean)getFieldObject(readingModes,"text","boolean"):false;
						imageReadingMode = isFieldNotNull(readingModes,"image")?(Boolean)getFieldObject(readingModes,"image","boolean"):false;
					}

					int pageCount =  isFieldNotNull(volumeInfoJsonObject,"pageCount")?(Integer)getFieldObject(volumeInfoJsonObject,"pageCount","int"):0;
					JSONArray categories =  isFieldNotNull(volumeInfoJsonObject,"categories")?(JSONArray)getFieldObject(volumeInfoJsonObject,"categories","jsonarray"):null;
					String categoriesFormated = "";
					if(categories != null){
						for(int j = 0; j < categories.length(); j++){
							if(j < (categories.length() - 1))
								categoriesFormated += (String)categories.get(j) + "; ";
							else categoriesFormated += (String)categories.get(j);
						}
					}

					JSONObject imageLinks =  isFieldNotNull(volumeInfoJsonObject,"imageLinks")?(JSONObject)getFieldObject(volumeInfoJsonObject,"imageLinks",""):null;

					ImageLink imageLink = new ImageLink();
					if(imageLinks != null){
						imageLink.setSmallThumbnail( isFieldNotNull(imageLinks,"smallThumbnail")?(String)getFieldObject(imageLinks,"smallThumbnail","string"):"" );
						imageLink.setThumbnail( isFieldNotNull(imageLinks,"thumbnail")?(String)getFieldObject(imageLinks,"thumbnail","string"):""  );
						imageLink.setMedium( isFieldNotNull(imageLinks,"medium")?(String)getFieldObject(imageLinks,"medium","string"):"" );
						imageLink.setLarge( isFieldNotNull(imageLinks,"large")?(String)getFieldObject(imageLinks,"large","string"):""  );
						imageLink.setExtraLarge(  isFieldNotNull(imageLinks,"extraLarge")?(String)getFieldObject(imageLinks,"extraLarge","string"):""  );
					}



					book.setVolumeInfo( new VolumeInfo( isFieldNotNull(volumeInfoJsonObject,"title")?(String)getFieldObject(volumeInfoJsonObject,"title","string"):"",
							isFieldNotNull(volumeInfoJsonObject,"subtitle")?(String)getFieldObject(volumeInfoJsonObject,"subtitle","string"):"",
							authorsFormated,
							pageCount,
							isFieldNotNull(volumeInfoJsonObject,"publisher")?(String)getFieldObject(volumeInfoJsonObject,"publisher","string"):"",
							isFieldNotNull(volumeInfoJsonObject,"publishedDate")?(String)getFieldObject(volumeInfoJsonObject,"publishedDate","string"):"",
							isFieldNotNull(volumeInfoJsonObject,"description")?(String)getFieldObject(volumeInfoJsonObject,"description","string"):"",
							categoriesFormated,
							imageLink
					));

					String accessViewStatusValue = "";
					String viewabilityValue = "";
					JSONObject accessInfoObject = isFieldNotNull(jsonBookObject,"accessInfo")?(JSONObject)getFieldObject(jsonBookObject,"accessInfo",""):null;
					if(accessInfoObject != null){
						/*book.setWebReaderLink( isFieldNotNull(accessInfoObject,"webReaderLink")?(String)getFieldObject(accessInfoObject,"webReaderLink","string"):"" );
						if(!book.getWebReaderLink().equals("")){
							book.setWebReaderLink( book.getWebReaderLink().replaceAll("f=false", "f=true"));
						}*/

						JSONObject pdf = isFieldNotNull(accessInfoObject,"pdf")?(JSONObject)getFieldObject(accessInfoObject,"pdf",""):null;
						boolean pdfIsAvailable = isFieldNotNull(pdf,"isAvailable")?(Boolean)getFieldObject(pdf,"isAvailable","boolean"):false;
						if(pdfIsAvailable){
							String acsTokenLink = isFieldNotNull(pdf,"acsTokenLink")?(String)getFieldObject(pdf,"acsTokenLink","string"):"";
							book.setPdfDownloadLink(acsTokenLink);
							book.setPdfFile(false);
						}

						accessViewStatusValue = isFieldNotNull(accessInfoObject,"accessViewStatus")?(String)getFieldObject(accessInfoObject,"accessViewStatus","string"):"";
						book.setAccessViewStatus( accessViewStatusValue );

						if(book.getAccessViewStatus().equals("FULL")){
							//book.setViewability( "FULL"/*isFieldNotNull(accessInfoObject,"viewability")?(String)getFieldObject(accessInfoObject,"viewability","string"):""*/);
							book.setFull(true);

						}
						else{
							book.setFull(false);
						}

						viewabilityValue = isFieldNotNull(accessInfoObject,"viewability")?(String)getFieldObject(accessInfoObject,"viewability","string"):"";
					}

					String previewLink = isFieldNotNull(volumeInfoJsonObject,"previewLink")?(String)getFieldObject(volumeInfoJsonObject,"previewLink","string"):"";
					if(!previewLink.equals("")){

						log.log(Level.INFO,"\n--->previewLink: "+previewLink);
						//tratar previewLink para montar o link de leitura embutido (trata-se de um c�digo html para apresenta��o
						// apenas do conteudo do livro sem bordas)
						String embeddedVersionBaseURL = previewLink.substring(0, previewLink.indexOf("?"));
						//String hlTagValue = previewLink.substring(0, previewLink.indexOf("&hl="));
						String idValue = ApiUrlMaker.getParametersFromUrl(previewLink).get("id");
						if(idValue == null)
							idValue = "";
						String  hlValue = ApiUrlMaker.getParametersFromUrl(previewLink).get("hl");
						if(hlValue == null){
							hlValue = "";
						}
						embeddedVersionBaseURL += "?id="+idValue+"&hl="+hlValue+"&lpg=PP1&pg=PP1&output=embed";
						//concatenar com:      e depois verificar o valor de hl e concatenar tb

						// campo setado com o valor da base da url e do parametro h1 (solu��o encontrada para o portalWeb conseguir recuperar o valor da base url e de h1 e poder montar o html embutido para a leitura de um livro)
						book.setAccessViewStatus(embeddedVersionBaseURL+"#"+hlValue);

						book.setWebReaderLink( "<html><header></header><body><iframe frameborder=\"0\" scrolling=\"yes\" style=\"border:0px\" src=\""+
								embeddedVersionBaseURL+
								"\" width=97% height=97%></iframe></body></html>");
						book.setReaderLinkHtmlFormat(true); // indica que o link de leitura esta em html

						if(!accessViewStatusValue.equals("NONE") && !viewabilityValue.equals("NO_PAGES") && (textReadingMode || imageReadingMode)){

							log.log(Level.INFO,"\n--->This book[ "+book.getVolumeInfo().getTitle()+" ] can be read online! (AccessViewStatus: "+accessViewStatusValue+" Viewability: "+viewabilityValue+" txtReadMode: "+textReadingMode+" ImgReadMode: "+imageReadingMode);
							booksVolumeCollection.add(book);

						}else{
							log.log(Level.INFO,"\n--->This book can't be read online! We don't consider this book [ "+book.getVolumeInfo().getTitle()+" ]! AccessViewStatus: "+accessViewStatusValue+" Viewability: "+viewabilityValue);
						}

					}



				}

				booksVolumeCollection.setTotalItems(Integer.parseInt(resultsPerPage));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}


		return booksVolumeCollection;
	}

	public static String formaterPlacesGeocodingAPI_response(String response){

		JSONObject jsonObject = null;
		Location location = null;
		try {
			jsonObject = new JSONObject(response);

			JSONArray results = isFieldNotNull(jsonObject,"results")?(JSONArray)getFieldObject(jsonObject,"results","jsonarray"):null;
			if(isNotNullOrEmptyThisArray(results)){

				for(int i=0; i < results.length(); i++){

					JSONObject geometryJSONObj = isFieldNotNull(results.getJSONObject(i),"geometry")?(JSONObject)getFieldObject(results.getJSONObject(i),"geometry",""):null;

					JSONObject locationJSONObj = isFieldNotNull(geometryJSONObj,"location")?(JSONObject)getFieldObject(geometryJSONObj,"location",""):null;

					location = new Location(isFieldNotNull(locationJSONObj,"lat")?(String)getFieldObject(locationJSONObj,"lat","string"):"",
							isFieldNotNull(locationJSONObj,"lng")?(String)getFieldObject(locationJSONObj,"lng","string"):"");
				}

			}

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		String lat_lgt = (location != null)?location.getLat()+","+location.getLng():"";


		return lat_lgt; // retorna algo como -23.5768634,-46.6431783
	}

	/*
	
	public static String formaterBooksFinalAPI_response(BooksVolumeCollection booksVolumeCollection){ // resposta em JSON
		
		String aux = "";
		for(Book book : booksVolumeCollection.getBooks()){
			aux+=book.toString()+"\n\n";
		}
		
		log.log(Level.INFO,"\n--->SAIDA:\n\n"+aux);
		
		xstreamXML.alias("book", Book.class);
		xstreamXML.alias("books", BooksVolumeCollection.class);
		xstreamXML.addImplicitCollection(BooksVolumeCollection.class, "books");
		xstreamXML.alias("volumeInfo", VolumeInfo.class);
		xstreamXML.alias("imageLink", ImageLink.class);
		String outputXml = xstreamXML.toXML(booksVolumeCollection);
		
		return outputXml; // retorna um xml com o seguinte formato: <books> <book> <id></id><etag></etag><volumeInfo><title></title><subtitle></subtitle><authors></authors><pageCount></pageCount><publisher></publisher><publishedDate></publishedDate><description></description><categories></categories><imageLink><smallThumbnail></smallThumbnail><thumbnail></thumbnail><medium/><large/><extraLarge/></imageLink></volumeInfo> <webReaderLink></webReaderLink><accessViewStatus></accessViewStatus><viewability></viewability></book> </books>
				
		
	}
	

	
	public static BooksVolumeCollection formaterBooks2API_response(String response){ // resposta em JSON
		
		JSONObject jsonObject = null;
						
		BooksVolumeCollection booksVolumeCollection = new BooksVolumeCollection();
			
		
		try {
			 
			jsonObject = new JSONObject(response);
			JSONArray docs = isFieldNotNull(jsonObject,"docs")?(JSONArray)getFieldObject(jsonObject,"docs","jsonarray"):null;
			if(isNotNullOrEmptyThisArray(docs)){
				for(int i =0; i< docs.length(); i++){
					JSONObject jsonBookObject = docs.getJSONObject(i);
					
					// obter array "subject" e verificar se dentro existe em um dos campos o valor "Accessible book", ent�o
					// obter o campo "ia" e verificar se existe apenas um elemento, contatenar o valor desse campo a "http://archive.org/stream/"
					JSONArray accessibleBookArrayCheck = isFieldNotNull(jsonBookObject,"subject")?(JSONArray)getFieldObject(jsonBookObject,"subject","jsonarray"):null;
					boolean accessibleBook = false;
					if(isNotNullOrEmptyThisArray(accessibleBookArrayCheck)){
						for(int j=0; j< accessibleBookArrayCheck.length(); j++){
							if(accessibleBookArrayCheck.get(j).equals("Accessible book")){								
								accessibleBook = true;								
							}
						}
						for(int j=0; j< accessibleBookArrayCheck.length(); j++){
							if(accessibleBookArrayCheck.getString(j).contains("Protected ")){								
								accessibleBook = false;								
							}
						}
					}
					
					String ia_collection_s = isFieldNotNull(jsonBookObject,"ia_collection_s")?(String)getFieldObject(jsonBookObject,"ia_collection_s","string"):"";
					if(ia_collection_s.contains("lending")){ // verificando se o livro est� disponivel apenas para aluguel
						accessibleBook = false;
					}
					
					if(accessibleBook){
						
						    log.log(Level.INFO,"\n\n-->Accessible book!!");
							//Setando as informa��es descritivas do Livro
							Book book = new Book();
							book.setFull(true); // Open Library just offers free books
							
							JSONArray iaArray = isFieldNotNull(jsonBookObject,"ia")?(JSONArray)getFieldObject(jsonBookObject,"ia","jsonarray"):null;
							if(isNotNullOrEmptyThisArray(iaArray)){
								// setando os links diretos para leitura e download do PDF
								String iaValue = iaArray.getString(0);
								String readLink = System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPEN_LIBRARY_READ_BOOK_URL)+iaValue+"#page/n0/mode/1up";
								String downloadLink = System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPEN_LIBRARY_PDF_DOWNLOAD_BOOK_URL)+iaValue+"/"+iaValue+".pdf";
								log.log(Level.INFO,"\n--->Read Link: "+readLink+"\n--->Download Link: "+downloadLink);
								book.setWebReaderLink(readLink);
								book.setReaderLinkHtmlFormat(false); // indica que o link de leitura n�o est� em codigo html(� um link existente)
								book.setPdfDownloadLink(downloadLink);
								book.setPdfFile(true);
							}
								
							//Para obter mais detalhes sobre o livro
							JSONArray editionKeyArray = isFieldNotNull(jsonBookObject,"edition_key")?(JSONArray)getFieldObject(jsonBookObject,"edition_key","jsonarray"):null;
							String detailsResponse = "";
							int pageCount = 0;
							if(isNotNullOrEmptyThisArray(editionKeyArray)){
								String editionKey = editionKeyArray.getString(0);
								detailsResponse = RequestManager.requestBooks2DetailsAPI(editionKey);
								JSONObject detailsResponseObj = new JSONObject(detailsResponse);
								String pageCountStringValue = isFieldNotNull(detailsResponseObj,"number_of_pages")?(String)getFieldObject(detailsResponseObj,"number_of_pages","string"):"";
								if(pageCountStringValue.equals("")){
									pageCount = 0;									
								}else{
									pageCount = Integer.valueOf(pageCountStringValue);
								}
								
							}
							
							String cover_i = isFieldNotNull(jsonBookObject,"cover_i")?(String)getFieldObject(jsonBookObject,"cover_i","string"):"";
							String imagePreviewURL = "";
							String thumbNail = "";
							String medium = "";
							String large = "";
							if(!cover_i.equals("")){ 
								imagePreviewURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_OPEN_LIBRARY_COVER_PREVIEW_URL)+cover_i+"-";;
								thumbNail = imagePreviewURL+"S.jpg";
								medium = imagePreviewURL+"M.jpg";
								large = imagePreviewURL+"L.jpg";
							
							}
							
							JSONArray publishDateArray = isFieldNotNull(jsonBookObject,"publish_date")?(JSONArray)getFieldObject(jsonBookObject,"publish_date","jsonarray"):null;
							String publishDate = "";
							if(isNotNullOrEmptyThisArray(publishDateArray)){
								publishDate = publishDateArray.getString(0);
							}
							
							book.setVolumeInfo(	new VolumeInfo(	isFieldNotNull(jsonBookObject,"title")?(String)getFieldObject(jsonBookObject,"title","string"):"", 
																isFieldNotNull(jsonBookObject,"subtitle")?(String)getFieldObject(jsonBookObject,"subtitle","string"):"",
																isFieldNotNull(jsonBookObject,"author_name")?(String)getFieldObject(jsonBookObject,"author_name","string"):"", 
																pageCount,																
																isFieldNotNull(jsonBookObject,"publisher")?(String)getFieldObject(jsonBookObject,"publisher","string"):"", 
																publishDate,
																"", //description
															    "", // categories
																new ImageLink(thumbNail, thumbNail, medium, large, large)));  // usar o campo "cover_i"  e chamar http://covers.openlibrary.org/b/id/<ID>-<S,M,L>.jpg  onde ID � o cover ID e S,M ou L o tamanho da imagem
						
						
						     booksVolumeCollection.add(book);
					}
					
					booksVolumeCollection.setTotalItems(Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_BOOKS_RESULTS_PER_PAGE_LIMIT)));
					
				}
			}
					
										
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return booksVolumeCollection;
		
	}
	
	public static String formaterSalesAPI_response(String response){ 
		
		response = response.replace("foo([200,", "").replace("]);", "");
		response = "{ \"salesAPI\" : [ "+ response + " ] }";
		//log.log(Level.INFO,"--> RESPONSE FORMATADO: "+response);
		JSONArray results = null;
		JSONObject jsonObject = null;
		
		ProductList productList = new ProductList();
		
		try {
			 
			jsonObject = new JSONObject(response);		
			
			JSONArray salesAPIArray = jsonObject.getJSONArray("salesAPI");
			JSONObject rootElement = salesAPIArray.getJSONObject(1);		
			
			results = (JSONArray)rootElement.get("results");
			
			// Obtendo as informa��es de cada livro
			for(int i = 0; i < results.length(); i++){
				Product product = new Product();
				JSONObject jsonProductObject = results.getJSONObject(i);
			
				int availableQuantity = isFieldNotNull(jsonProductObject,"available_quantity")?(Integer)getFieldObject(jsonProductObject,"available_quantity","int"):0;
				if(availableQuantity > 0){ // somente trazemos produtos com quantia disponivel
					product.setTitle( isFieldNotNull(jsonProductObject,"title")?(String)getFieldObject(jsonProductObject,"title","string"):""  );
					product.setSubtitle( isFieldNotNull(jsonProductObject,"subtitle")?(String)getFieldObject(jsonProductObject,"subtitle","string"):"" );
					product.setPriceRange( isFieldNotNull(jsonProductObject,"price")?(String)getFieldObject(jsonProductObject,"price","string"):"" );
					product.setAvailable_quantity( availableQuantity );
					product.setSold_quantity( isFieldNotNull(jsonProductObject,"sold_quantity")?(Integer)getFieldObject(jsonProductObject,"sold_quantity","int"):0 );
					product.setStop_time( isFieldNotNull(jsonProductObject,"stop_time")?(String)getFieldObject(jsonProductObject,"stop_time","string"):""  );
					product.setPermalink( isFieldNotNull(jsonProductObject,"permalink")?(String)getFieldObject(jsonProductObject,"permalink","string"):"" );
				    
					JSONObject sellerAdress = isFieldNotNull(jsonProductObject,"seller_address")?(JSONObject)getFieldObject(jsonProductObject,"seller_address",""):null;
					if(sellerAdress != null){
						JSONObject sellerAdressCountry = isFieldNotNull(sellerAdress,"country")?(JSONObject)getFieldObject(sellerAdress,"country",""):null;
						if(sellerAdressCountry != null){
							product.setSeller_address_country_name( isFieldNotNull(sellerAdressCountry,"name")?(String)getFieldObject(sellerAdressCountry,"name","string"):"" );
	
						}
						
						JSONObject sellerAdressState= isFieldNotNull(sellerAdress,"state")?(JSONObject)getFieldObject(sellerAdress,"state",""):null;
						if(sellerAdressState != null){
							product.setSeller_address_state_name( isFieldNotNull(sellerAdressState,"name")?(String)getFieldObject(sellerAdressState,"name","string"):"" );
	
						}
						
						JSONObject sellerAdressCity= isFieldNotNull(sellerAdress,"city")?(JSONObject)getFieldObject(sellerAdress,"city",""):null;
						if(sellerAdressCity != null){
							product.setSeller_address_city_name( isFieldNotNull(sellerAdressCity,"name")?(String)getFieldObject(sellerAdressCity,"name","string"):"" );
	
						}
					}
					
					productList.add(product);
					
				}
				
				
			} 
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return response;
			
		}
		
		String aux = "";
		for(Product product : productList.getProducts()){
			aux+=product.toString()+"\n\n";
		}
		
		log.log(Level.INFO,"--->SAIDA:\n\n\n"+aux);
		
		xstreamXML.alias("product", Product.class);
		xstreamXML.alias("productList", ProductList.class);
		xstreamXML.addImplicitCollection(ProductList.class, "products");
		
		String outputXml = xstreamXML.toXML(productList);		
		
		return outputXml;
		
	}
	
	public  static String formaterDirectionsAPI_response(String response){ // usamos XML
		
	
		xstreamXML.alias("DirectionsResponse", Direction.class);
		xstreamXML.alias("route", Route.class);
		xstreamXML.alias("step", Step.class);		
		xstreamXML.alias("leg", Leg.class);
		xstreamXML.addImplicitCollection(Leg.class, "steps");
		xstreamXML.alias("start_location", Location.class);
		xstreamXML.alias("duration", Duration.class);
		xstreamXML.alias("distance", Distance.class);
						
		
		Direction direction = (Direction)xstreamXML.fromXML(response);
		
		direction.setStatus(null);
			
		direction.setSummary( direction.getRoute().getSummary() );
		direction.setSteps( direction.getRoute().getLeg().getSteps() );
		direction.setDistance( direction.getRoute().getLeg().getDistance() );
		direction.setDuration( direction.getRoute().getLeg().getDuration() );
		
		Leg tempLeg = direction.getRoute().getLeg();
			
		// Definindo mapa estatico para o caminho tracado:
		
		// definindo indice de uma etapa no meio da rota (aprox. metade do caminho para centralizar o mapa)
		//int mediumRoteIndextemp = tempLeg.getSteps().size()/2;
		// definindo o ponto central em latitude e longitude
		//String centerLocation =   tempLeg.getSteps().get(mediumRoteIndextemp).getStart_location().getLat()+","+tempLeg.getSteps().get(mediumRoteIndextemp).getStart_location().getLng();
		//log.log(Level.INFO,"\n--> CENTER LOCATION: "+centerLocation);
		
		String path = "";		
		for(int i = 0; i < tempLeg.getSteps().size(); i++){
			path += "|"+ tempLeg.getSteps().get(i).getStart_location().getLat() +","+tempLeg.getSteps().get(i).getStart_location().getLng();
		}
		
		String initialPlaceLatLong = tempLeg.getSteps().get(0).getStart_location().getLat() +","+tempLeg.getSteps().get(0).getStart_location().getLng();
		String finalPlaceLatLong = tempLeg.getSteps().get(tempLeg.getSteps().size()-1).getStart_location().getLat() +","+tempLeg.getSteps().get(tempLeg.getSteps().size()-1).getStart_location().getLng();
		String initialAndFinalLatLong =  "|"+initialPlaceLatLong+"|"+finalPlaceLatLong+"|";
										 
		//log.log(Level.INFO,"\n--> PATH PARA O MAPA ESTATICO: "+path);	
		
		//double distanceKm =  Double.parseDouble(direction.getDistance().getText().replaceAll("km", "").replaceAll(" ", "").replace(",", "."));
		// calculando valor do zoom
		//int zoomValue = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_DIRECTIONS_STATICMAP_ZOOM_VALUE));	

		String staticMapURL = RequestManager.requestDirectionsStaticMapAPI(initialAndFinalLatLong, path);
		log.log(Level.INFO,"\n-->URL PARA MAPA ESTATICO: "+staticMapURL);
		
		direction.setRoute(null);
				
		direction.setStaticMapURL(staticMapURL);
		
		String streetViewDestinyImageURL = "<html><body><iframe width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" src=\"https://maps.google.com/maps?q="+finalPlaceLatLong+"&amp;ie=UTF8&amp;t=m&amp;layer=c&amp;cbll="+finalPlaceLatLong+"&amp;cbp=13,160.56,,0,0&amp;source=embed&amp;ll="+finalPlaceLatLong+"&amp;spn="+finalPlaceLatLong+"&amp;z=14&amp;output=svembed\"></iframe></body></html>";//RequestManager.requestDirectionsStreetViewAPI(finalPlaceLatLong);
		log.log(Level.INFO,"\n-->URL PARA IMAGEM STREETVIEW [END DESTINO]: "+streetViewDestinyImageURL);
		direction.setDestinyStreetViewImageURL(streetViewDestinyImageURL);		
		
		xstreamXML.alias("direction", Direction.class);
		
		return xstreamXML.toXML(direction); // retorna xml no estilo  <direction><steps><step><duration/><distance/><start_location/></step></steps> <duration/><distance/> </direction>
	}
	
	public static String formaterTimeZoneAPI_response(String response){
		
		JSONObject jsonObject = null;
		try {
			
			jsonObject = new JSONObject(response);
			
			return isFieldNotNull(jsonObject,"timeZoneId")?(String)getFieldObject(jsonObject,"timeZoneId","string"):"";
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String formaterYahooAPI_response(String response){	
		return "";
		
	}
	
	public static String formaterWeatherAPI_response(String response, String finalAdress){	
      
		JSONArray items = null;
		JSONObject jsonObject = null;
		
		Weather weather = new Weather();
		
		try {
			jsonObject = new JSONObject(response);
			
			JSONArray list = isFieldNotNull(jsonObject,"list")?(JSONArray)getFieldObject(jsonObject,"list","jsonarray"):null;
			if(isNotNullOrEmptyThisArray(list)){
				
				JSONObject currentWeather = list.getJSONObject(0);
				
				JSONObject currentTempObj = isFieldNotNull(currentWeather,"temp")?(JSONObject)getFieldObject(currentWeather,"temp",""):null;
				
				String tempFieldValue = (isFieldNotNull(currentTempObj,"day")?(String)getFieldObject(currentTempObj,"day","string"):"");
				
				
				String tempFieldValueIntPart = "";
				if(tempFieldValue.indexOf(".") > 0)
					tempFieldValueIntPart = tempFieldValue.substring(0, tempFieldValue.indexOf("."));
				else tempFieldValueIntPart = tempFieldValue;
				
				Integer tempFieldValueInt = Integer.parseInt(tempFieldValueIntPart);
				
				Integer tempInCelcius = tempFieldValueInt - 273;
				log.log(Level.INFO,"--> Temperature Integer Part :"+tempFieldValueIntPart+" In Celcius: "+tempInCelcius);
				weather.setTemperature(String.valueOf(tempFieldValueIntPart));
				
				//weather.setHumidity(isFieldNotNull(mainObject,"humidity")?(String)getFieldObject(mainObject,"humidity","string"):"");
				weather.setTemp_min(isFieldNotNull(currentTempObj,"min")?(String)getFieldObject(currentTempObj,"min","string"):"");
				weather.setTemp_max(isFieldNotNull(currentTempObj,"max")?(String)getFieldObject(currentTempObj,"max","string"):"");
				
				JSONArray weatherObj = isFieldNotNull(currentWeather,"weather")?(JSONArray)getFieldObject(currentWeather,"weather","jsonarray"):null;
				if(isNotNullOrEmptyThisArray(weatherObj)){
					
					weather.setWeatherDescription( isFieldNotNull(weatherObj.getJSONObject(0),"description")?(String)getFieldObject(weatherObj.getJSONObject(0),"description","string"):""); 
				}
				
				weather.setWindSpeed(isFieldNotNull(currentWeather,"speed")?(String)getFieldObject(currentWeather,"speed","string"):"" );
							
			    // plano de fundo para o layout
				weather.setStaticMapImageURL( RequestManager.requestDirectionsStaticMapOnePlacePointAPI(finalAdress) );
				
				//Previsao para os proximos 6 dias
				String forecastText = "";
				for(int i = 1; i < list.length(); i++){
					JSONObject nextTempObj = list.getJSONObject(i);
					
					JSONObject currentTempObj1 = isFieldNotNull(nextTempObj,"temp")?(JSONObject)getFieldObject(nextTempObj,"temp",""):null;
					String minTemp = (isFieldNotNull(currentTempObj1,"min")?(String)getFieldObject(currentTempObj1,"min","string"):"");
					String maxTemp = (isFieldNotNull(currentTempObj1,"max")?(String)getFieldObject(currentTempObj1,"max","string"):"");
					
					if(!minTemp.equals("") && !maxTemp.equals("")){
						
						int minTempValue  = 0;
						if(minTemp.indexOf(".") > 0)
							minTempValue = Integer.parseInt(minTemp.substring(0, minTemp.indexOf(".")) );
						else minTempValue = Integer.valueOf(minTemp);
						
						int minTempCelciusTemp = minTempValue - 273;
						int minTempFahrenhTemp = minTempValue - 241;
						String minCelciusFormattedTemp = String.valueOf(minTempCelciusTemp) + "°C" ;
						String minFahrenhFormattedTemp  = String.valueOf(minTempFahrenhTemp) + "°F" ;
						
						int maxTempValue  = 0;
						if(maxTemp.indexOf(".") > 0)
							maxTempValue = Integer.parseInt(maxTemp.substring(0, maxTemp.indexOf(".")) ); 
						else maxTempValue = Integer.valueOf(maxTemp);
						
						int maxTempCelciusTemp = maxTempValue - 273;
						int maxTempFahrenhTemp = maxTempValue - 241;
						String maxCelciusFormattedTemp = String.valueOf(maxTempCelciusTemp) + "°C" ;
						String maxFahrenhFormattedTemp  = String.valueOf(maxTempFahrenhTemp) + "°F" ;	
										
						//weather.setHumidity(isFieldNotNull(mainObject,"humidity")?(String)getFieldObject(mainObject,"humidity","string"):"");
						forecastText +="Min: "+minCelciusFormattedTemp+"/"+minFahrenhFormattedTemp +"  ";
						forecastText +="Max: "+maxCelciusFormattedTemp+"/" +maxFahrenhFormattedTemp+"  ";
						JSONArray weatherObj1 = isFieldNotNull(nextTempObj,"weather")?(JSONArray)getFieldObject(nextTempObj,"weather","jsonarray"):null;
						if(isNotNullOrEmptyThisArray(weatherObj1)){
							
							forecastText += ( isFieldNotNull(weatherObj1.getJSONObject(0),"description")?(String)getFieldObject(weatherObj1.getJSONObject(0),"description","string"):""); 
						}
						forecastText+="\n\n";
					}
					
					
					//weather.setWindSpeed(isFieldNotNull(nextTempObj,"speed")?(String)getFieldObject(nextTempObj,"speed","string"):"" );
								
				}
				weather.setForecastText(forecastText);
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    xstreamXML.alias("weather", Weather.class);
		
		return xstreamXML.toXML(weather); // retorna xml no estilo  <weather><temperature/><temp__min/><temp__max/><humidity/><pressure/><windSpeed/><weatherDescription/></weather>
		
	}
	
	public static String formaterVideoAPI_response(String response){	
		
		JSONArray items = null;
		JSONObject jsonObject = null;
		
		VideoCollection videoCollection = new VideoCollection();
		
		
		try {
			 
			jsonObject = new JSONObject(response);		
				
			String nextPageToken = isFieldNotNull(jsonObject,"nextPageToken")?(String)getFieldObject(jsonObject,"nextPageToken","string"):"";
			//session.setAttribute("pageToken",nextPageToken);
			videoCollection.setNextPageToken(nextPageToken);
			
			items = (JSONArray)jsonObject.get("items");
			
			if(isNotNullOrEmptyThisArray(items)){
				// Obtendo as informa��es de cada video
				for(int i = 0; i < items.length(); i++){
					
					Video video = new Video();
					JSONObject jsonVideoObject = items.getJSONObject(i);
					
					JSONObject jsonIdObject = isFieldNotNull(jsonVideoObject,"id")?(JSONObject)getFieldObject(jsonVideoObject,"id",""):null; 
					if(jsonIdObject != null){
					  video.setId( isFieldNotNull(jsonIdObject,"videoId")?(String)getFieldObject(jsonIdObject,"videoId","string"):"" );
					  video.setDirectURL( System.getProperty(AllInYourHandsConstants.PROPERTY_API_YOUTUBE_VIDEO_DIRECT_URL)  + video.getId() );
					}
					
					JSONObject jsonSnippetObject = isFieldNotNull(jsonVideoObject,"snippet")?(JSONObject)getFieldObject(jsonVideoObject,"snippet",""):null; 
					if(jsonSnippetObject != null){
					  video.setPublishedAt( isFieldNotNull(jsonSnippetObject,"publishedAt")?(String)getFieldObject(jsonSnippetObject,"publishedAt","string"):""  );
					  video.setTitle( isFieldNotNull(jsonSnippetObject,"title")?(String)getFieldObject(jsonSnippetObject,"title","string"):""   );
					  video.setDescription( isFieldNotNull(jsonSnippetObject,"description")?(String)getFieldObject(jsonSnippetObject,"description","string"):"" );
					  
					  JSONObject thumbnails = isFieldNotNull(jsonSnippetObject,"thumbnails")?(JSONObject)getFieldObject(jsonSnippetObject,"thumbnails",""):null;
					  
					  JSONObject defaultField = isFieldNotNull(thumbnails,"default")?(JSONObject)getFieldObject(thumbnails,"default",""):null;
					  
					  video.setPreviewImage( isFieldNotNull(defaultField,"url")?(String)getFieldObject(defaultField,"url","string"):"" );
					  
					  video.setChannelId(  isFieldNotNull(jsonSnippetObject,"channelId")?(String)getFieldObject(jsonSnippetObject,"channelId","string"):"" );
					  video.setChannelTitle(  isFieldNotNull(jsonSnippetObject,"channelTitle")?(String)getFieldObject(jsonSnippetObject,"channelTitle","string"):"" );
					  
					}
					
					videoCollection.add(video);
					
				}
			}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("videos", VideoCollection.class);
		xstreamXML.alias("video", Video.class);
		xstreamXML.addImplicitCollection(VideoCollection.class, "videos");
		
		return xstreamXML.toXML(videoCollection); //retorna xml no estilo  <videos> <video> <id/> <publishedAt/> <title/> <channelId/> </description> <channelTitle/> <directURL/> </video> </videos>
		
		
	}
	
	public static String formaterVideoCategoriesAPI_response(String response){	
		
		JSONArray items = null;
		JSONObject jsonObject = null;
		
		CategoryCollection videoCategories= new CategoryCollection();
		
		
		try {
			 
			jsonObject = new JSONObject(response);	
			
			items = (JSONArray)jsonObject.get("items");
			if(isNotNullOrEmptyThisArray(items)){
				
					String categoriesVideoNumber_string = System.getProperty(AllInYourHandsConstants.PROPERTY_API_YOUTUBE_VIDEO_CATEGORIES_LIMIT_NUMBER);
					int categoriesVideoNumber = Integer.parseInt( (categoriesVideoNumber_string!=null) ? (categoriesVideoNumber_string.equals("") ? "10" : categoriesVideoNumber_string) :"10" );
					int categoriesVideoLimitNumber = (categoriesVideoNumber>items.length())? items.length() : categoriesVideoNumber;
					for(int i=0; i< categoriesVideoLimitNumber; i++){
						Category videoCategory = new Category();
						
						videoCategory.setId( isFieldNotNull(items.getJSONObject(i),"id")?(String)getFieldObject(items.getJSONObject(i),"id","string"):"" );
						
						JSONObject snippetObject = isFieldNotNull(items.getJSONObject(i),"snippet")?(JSONObject)getFieldObject(items.getJSONObject(i),"snippet",""):null;
						
						videoCategory.setName( isFieldNotNull(snippetObject,"title")?(String)getFieldObject(snippetObject,"title","string"):"" );
						
						videoCategories.add(videoCategory);
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("videoCategories", CategoryCollection.class);
		xstreamXML.alias("category", Category.class);
		xstreamXML.addImplicitCollection(CategoryCollection.class, "categories");
		
		return xstreamXML.toXML(videoCategories); // retorna xml do tipo  <videoCategories> <videoCategory><videoCategoryId/><videoCategoryName/> </videoCategories>
	}
	
	public static String formaterAudioAPI_response(String response, String lyricIndicator){ 
		
		JSONArray songsObject = null;				
		SongCollection songCollection = new SongCollection();
		
		try {
			songsObject = new JSONArray(response);
			
			if(isNotNullOrEmptyThisArray(songsObject)){
				
					for(int i=0; i< songsObject.length(); i++){
						JSONObject songObject = songsObject.getJSONObject(i);
						Song song = new Song();
						
						song.setUrl( isFieldNotNull(songObject,"Url")?(String)getFieldObject(songObject,"Url","string"):"" );
						song.setName( isFieldNotNull(songObject,"SongName")?(String)getFieldObject(songObject,"SongName","string"):"" );
						song.setId( isFieldNotNull(songObject,"SongID")?(String)getFieldObject(songObject,"SongID","string"):"" );
						song.setArtistName( isFieldNotNull(songObject,"ArtistName")?(String)getFieldObject(songObject,"ArtistName","string"):"" );
						song.setArtistId( isFieldNotNull(songObject,"ArtistID")?(String)getFieldObject(songObject,"ArtistID","string"):"" );
						song.setAlbumName( isFieldNotNull(songObject,"AlbumName")?(String)getFieldObject(songObject,"AlbumName","string"):"" );
						song.setAlbumId( isFieldNotNull(songObject,"AlbumID")?(String)getFieldObject(songObject,"AlbumID","string"):"" );
						
						
						String previewURL = "";
						String enablePreviewURLPropertyValue = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_PREVIEWIMAGE_ENABLE);
						int enablePreviewURL = Integer.parseInt(  ((enablePreviewURLPropertyValue==null)?"0":enablePreviewURLPropertyValue) );
						 	
						if(enablePreviewURL == 1){
							previewURL = ResponseFormater.formaterAudioPreviewUrl2Api_response( RequestManager.requestAudioPreviewUrl2API( song.getName()+" "+song.getArtistName()) );
							//if(previewURL == "")
								//previewURL = ResponseFormater.formaterAudioPreviewUrlApi_response( RequestManager.requestAudioPreviewUrlAPI( song.getName()+" "+song.getArtistName()) );
						}
						
						song.setImagePreview( previewURL ); //setando a imagem de preview
						
						
						//setando a letra da m�sica se o parametro "artistLyricName" tiver algum valor
						if(lyricIndicator.equals("1")){
							// fazendo busca e obtendo track id da musica em quest�o
							Lyric lyric = ResponseFormater.formaterLyricsAPI_response(  RequestManager.requestLyricsAPI( song.getName(), song.getArtistName() )  );
							
							if(lyric != null){							    
								
								song.setLyricBody( lyric.getLyricBody() );
								song.setLyricCopyright( lyric.getCopyright() );
							}
						}

						if(!song.getUrl().equals("")) // a api do grooveshark DEVE sempre ter a URL com um link, urls vazias ser�o desconsideradas
							songCollection.add(song);
						
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("songs", SongCollection.class);
		xstreamXML.alias("song", Song.class);
		xstreamXML.addImplicitCollection(SongCollection.class, "songs");
		
				
		return  xstreamXML.toXML(songCollection);
	}
	
	public static String formaterAudioOfficialGroveshark_response(String response, String lyricIndicator){
	    	
	    	
	    	JSONObject jsonObject = null;			
			SongCollection songCollection = new SongCollection();
			
			try {
				jsonObject = new JSONObject(response);
				
				JSONObject resultObj = isFieldNotNull(jsonObject,"result")?(JSONObject)getFieldObject(jsonObject,"result",""):null; 
				JSONArray songsArray = isFieldNotNull(resultObj,"songs")?(JSONArray)getFieldObject(resultObj,"songs","jsonarray"):null; 
				if(isNotNullOrEmptyThisArray(songsArray)){
					
					for(int i = 0;i < songsArray.length(); i++){
						JSONObject songObject = songsArray.getJSONObject(i);
						Song song = new Song();
						
						
						song.setName( isFieldNotNull(songObject,"SongName")?(String)getFieldObject(songObject,"SongName","string"):"" );
						song.setId( isFieldNotNull(songObject,"SongID")?(String)getFieldObject(songObject,"SongID","string"):"" );
						int directUrlIncludeEnabled = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFFICIAL_DIRECTURL_INCLUDE_ENABLED));
						String songDirectUrl = "defaultText"; // texto generico para indicar que o song possui uma url
						if(directUrlIncludeEnabled == 1){
							//songDirectUrl = ResponseFormater.formaterAudioUrlOfficialGroveshark_response( RequestManager.requestAudioUrlOfficialGroovesharkAPI(song.getId()) );
						    log.log(Level.INFO,"formaterAudioUrlOfficialGroveshark_response() finished: "+songDirectUrl);
						}
						song.setUrl( songDirectUrl );
						song.setArtistName( isFieldNotNull(songObject,"ArtistName")?(String)getFieldObject(songObject,"ArtistName","string"):"" );
						song.setArtistId( isFieldNotNull(songObject,"ArtistID")?(String)getFieldObject(songObject,"ArtistID","string"):"" );
						song.setAlbumName( isFieldNotNull(songObject,"AlbumName")?(String)getFieldObject(songObject,"AlbumName","string"):"" );
						song.setAlbumId( isFieldNotNull(songObject,"AlbumID")?(String)getFieldObject(songObject,"AlbumID","string"):"" );
						
						String previewImageFileName = isFieldNotNull(songObject,"CoverArtFilename")?(String)getFieldObject(songObject,"CoverArtFilename","string"):"";
						if(!previewImageFileName.equals("")){ // existe arquivo de imagem vinculado ao item
							String previewImageBaseURL = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_OFFICIAL_PREVIEWIMAGE_BASE_URL);
							String imageSize = "l"; // s - small, m - medium , l - large
							song.setImagePreview(previewImageBaseURL + imageSize + previewImageFileName );
							
						}else{ // n�o existe arquivo de imagem (usar api terceira para encontrar imagem - exige mais processamento)
							String enablePreviewURLPropertyValue = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_PREVIEWIMAGE_ENABLE);
							int enablePreviewURL = Integer.parseInt(  ((enablePreviewURLPropertyValue==null)?"0":enablePreviewURLPropertyValue) );
						 	
							if(enablePreviewURL == 1){ 
								String alternativePreviewURL = ResponseFormater.formaterAudioPreviewUrl2Api_response( RequestManager.requestAudioPreviewUrl2API( song.getName()+" "+song.getArtistName()) );
								//String alternativePreviewURL = ResponseFormater.formaterAudioPreviewUrlApi_response( RequestManager.requestAudioPreviewUrlAPI( song.getName()+" "+song.getArtistName()) );
								song.setImagePreview(alternativePreviewURL);
							}
							
							
						}
						
						//setando a letra da m�sica se o parametro "artistLyricName" tiver algum valor
						if(lyricIndicator.equals("1")){
							// fazendo busca e obtendo track id da musica em quest�o
							Lyric lyric = ResponseFormater.formaterLyricsAPI_response(  RequestManager.requestLyricsAPI( song.getName(), song.getArtistName() )  );
							
							if(lyric != null){							    
								
								song.setLyricBody( lyric.getLyricBody() );
								song.setLyricCopyright( lyric.getCopyright() );
							}
						}
						
						if(!song.getUrl().equals("")) // a api do grooveshark DEVE sempre ter a URL com um link, urls vazias ser�o desconsideradas
							songCollection.add(song);
						
						
						
					}
						
				}
				
			}catch (JSONException e) {			
				return "";			
			}catch(Exception e){
				return "";
			}
			
			xstreamXML.alias("songs", SongCollection.class);
			xstreamXML.alias("song", Song.class);
			xstreamXML.addImplicitCollection(SongCollection.class, "songs");
		
			
			return  xstreamXML.toXML(songCollection);
				    	
	    }
	
	
	public static String formaterAudioSpotify_response(String response, String lyricIndicator){
    	
    	
    	JSONObject jsonObject = null;			
		SongCollection songCollection = new SongCollection();
		
		try {
			jsonObject = new JSONObject(response);
			
			JSONObject tracksObj = isFieldNotNull(jsonObject,"tracks")?(JSONObject)getFieldObject(jsonObject,"tracks",""):null; 
			JSONArray items   = isFieldNotNull(tracksObj,"items")?(JSONArray)getFieldObject(tracksObj,"items","jsonarray"):null; 
			if(isNotNullOrEmptyThisArray(items)){
				
				for(int i = 0;i < items.length(); i++){
					JSONObject songObject = items.getJSONObject(i);
					Song song = new Song();
					
					
					song.setName( isFieldNotNull(songObject,"name")?(String)getFieldObject(songObject,"name","string"):"" );
					song.setId( isFieldNotNull(songObject,"id")?(String)getFieldObject(songObject,"id","string"):"" );
				
					song.setUrl( isFieldNotNull(songObject,"preview_url")?(String)getFieldObject(songObject,"preview_url","string"):"" );
					
					JSONArray artists   = isFieldNotNull(songObject,"artists")?(JSONArray)getFieldObject(songObject,"artists","jsonarray"):null; 
					if(isNotNullOrEmptyThisArray(artists)){
						JSONObject artist = artists.getJSONObject(0);					
						song.setArtistName( isFieldNotNull(artist,"name")?(String)getFieldObject(artist,"name","string"):"" );
						song.setArtistId( isFieldNotNull(artist,"id")?(String)getFieldObject(artist,"id","string"):""  );
					}
					
					JSONObject album = isFieldNotNull(songObject,"album")?(JSONObject)getFieldObject(songObject,"album",""):null; 
					JSONArray images   = isFieldNotNull(album,"images")?(JSONArray)getFieldObject(album,"images","jsonarray"):null; 
					if(isNotNullOrEmptyThisArray(images)){
						int size = 0;						
						for(int j = 0;j < images.length(); j++){
							JSONObject imageObj = images.getJSONObject(j);
							if ( Integer.parseInt( isFieldNotNull(imageObj,"height")?(String)getFieldObject(imageObj,"height","string"):"" ) > size){
								size = Integer.parseInt( isFieldNotNull(imageObj,"height")?(String)getFieldObject(imageObj,"height","string"):"" ) ;
								song.setImagePreview( isFieldNotNull(imageObj,"url")?(String)getFieldObject(imageObj,"url","string"):"" );
							}
						}
												
					}else{
						song.setImagePreview( "" );
					}
					
					song.setAlbumName( isFieldNotNull(album,"name")?(String)getFieldObject(album,"name","string"):"" );
					song.setAlbumId( isFieldNotNull(album,"id")?(String)getFieldObject(album,"id","string"):"" );
				
					//setando a letra da musica se o parametro "artistLyricName" tiver algum valor
					if(lyricIndicator.equals("1")){
						// fazendo busca e obtendo track id da musica em quest�o
						Lyric lyric = ResponseFormater.formaterLyricsAPI_response(  RequestManager.requestLyricsAPI( song.getName(), song.getArtistName() )  );
						
						if(lyric != null){							    
							
							song.setLyricBody( lyric.getLyricBody() );
							song.setLyricCopyright( lyric.getCopyright() );
						}
					}
					
					if(!song.getUrl().equals("")) // a api do spotify DEVE sempre ter a URL com um link, urls vazias serao desconsideradas
						songCollection.add(song);
					
					
					
				}
					
			}
			
		}catch (JSONException e) {			
			return "";			
		}catch(Exception e){
			return "";
		}
		
		xstreamXML.alias("songs", SongCollection.class);
		xstreamXML.alias("song", Song.class);
		xstreamXML.addImplicitCollection(SongCollection.class, "songs");
	
		
		return  xstreamXML.toXML(songCollection);
			    	
    }
	
	public static String formaterAudioUrlOfficialGroveshark_response(String response){
    	
		//{"header":{"hostname":"RHL109"},"result":{"url":"http:\/\/grooveshark.com\/s\/Porto+D+alma\/2KAbUh?src=3"}}
    			
    	JSONObject jsonObject = null;			
    	String audioURL = "";
		
		try {
			jsonObject = new JSONObject(response);
			
			JSONObject resultObj = isFieldNotNull(jsonObject,"result")?(JSONObject)getFieldObject(jsonObject,"result",""):null; 
			audioURL = isFieldNotNull(resultObj,"url")?(String)getFieldObject(resultObj,"url","string"):""; 
			
		}catch (JSONException e) {			
			return "";			
		}catch(Exception e){
			return "";
		}
		
		return  audioURL;
			    	
    }
	
	// Este � o metodo de formata��o de audio que est� sendo utilizado por padr�o
	public static String formaterAudioAPI2_response(String response, String lyricIndicator){ 
		
		JSONObject responseObj = null;				
		SongCollection songCollection = new SongCollection();
		
		try {
			responseObj = new JSONObject(response);
			
			
			if(responseObj != null){
				
				JSONObject resultObj = isFieldNotNull(responseObj,"result")?(JSONObject)getFieldObject(responseObj,"result",""):null;
				
				   if(resultObj != null){
					   
					   JSONArray resultsObj =  isFieldNotNull(resultObj,"results")?(JSONArray)getFieldObject(resultObj,"results","jsonarray"):null;	
					   
					   if(isNotNullOrEmptyThisArray(resultsObj)){
				
							for(int i=0; i< resultsObj.length(); i++){
								JSONObject songObject = resultsObj.getJSONObject(i);
								Song song = new Song();
								
								boolean canStream = isFieldNotNull(songObject,"canStream")?(Boolean)getFieldObject(songObject,"canStream","boolean"):false;
								
								if(canStream){ // se for possivel ouvir em streaming
									
									song.setUrl( "" ); // a URL DEVE ser nula no caso de usarmos a api Rdio
									song.setName( isFieldNotNull(songObject,"name")?(String)getFieldObject(songObject,"name","string"):"" );
									song.setId( isFieldNotNull(songObject,"key")?(String)getFieldObject(songObject,"key","string"):"" );
									song.setArtistName( isFieldNotNull(songObject,"artist")?(String)getFieldObject(songObject,"artist","string"):"" );
									song.setArtistId( isFieldNotNull(songObject,"artistKey")?(String)getFieldObject(songObject,"artistKey","string"):"" );
									song.setAlbumName( isFieldNotNull(songObject,"album")?(String)getFieldObject(songObject,"album","string"):"" );
									song.setAlbumId( isFieldNotNull(songObject,"albumKey")?(String)getFieldObject(songObject,"albumKey","string"):"" );
									
									
									String previewURL = isFieldNotNull(songObject,"icon400")?(String)getFieldObject(songObject,"icon400","string"):"";
									String enablePreviewURLPropertyValue = System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_PREVIEWIMAGE_ENABLE);
									int enablePreviewURL = Integer.parseInt(  ((enablePreviewURLPropertyValue==null)?"0":enablePreviewURLPropertyValue) );
									 	
									if(enablePreviewURL == 1){
										
										// no caso a api Rdio j� possui um preview padr�o, mas caso n�o o encontremos utilizamos nosso meio convencional (outra api para o preview)
										if(previewURL.equals(""))
											previewURL = ResponseFormater.formaterAudioPreviewUrl2Api_response( RequestManager.requestAudioPreviewUrl2API( song.getName()+" "+song.getArtistName()) );
										//if(previewURL == "")
											//previewURL = ResponseFormater.formaterAudioPreviewUrlApi_response( RequestManager.requestAudioPreviewUrlAPI( song.getName()+" "+song.getArtistName()) );
									}
									
									song.setImagePreview( previewURL ); //setando a imagem de preview
									
									
									//setando a letra da m�sica se o parametro "artistLyricName" tiver algum valor
									if(lyricIndicator.equals("1")){
										// fazendo busca e obtendo track id da musica em quest�o
										Lyric lyric = ResponseFormater.formaterLyricsAPI_response(  RequestManager.requestLyricsAPI( song.getName(), song.getArtistName() )  );
										
										if(lyric != null){							    
											
											song.setLyricBody( lyric.getLyricBody() );
											song.setLyricCopyright( lyric.getCopyright() );
										}
									}
									
									songCollection.add(song);
									
							    }
								
							}
						
					   }
					
				   }
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("songs", SongCollection.class);
		xstreamXML.alias("song", Song.class);
		xstreamXML.addImplicitCollection(SongCollection.class, "songs");
	
		
		return  xstreamXML.toXML(songCollection);
	}
	
	public static String formaterAudioPreviewUrlApi_response(String response){ 
		
		JSONObject songObject = null;				
	
		
		try {
			songObject = new JSONObject(response);
			JSONObject resultsObj = isFieldNotNull(songObject,"results")?(JSONObject)getFieldObject(songObject,"results",""):null;
			
			JSONObject trackmatchesObj = isFieldNotNull(resultsObj,"trackmatches")?(JSONObject)getFieldObject(resultsObj,"trackmatches",""):null;
			
			JSONObject track = isFieldNotNull(trackmatchesObj,"track")?(JSONObject)getFieldObject(trackmatchesObj,"track",""):null;
			
			
			//if(isNotNullOrEmptyThisArray(tracks)){
					
			JSONArray images = isFieldNotNull(track,"image")?(JSONArray)getFieldObject(track,"image","jsonarray"):null;
			if(isNotNullOrEmptyThisArray(images)){							
					//for(int j=0; j< images.length(); j++){
							String size = isFieldNotNull(images.getJSONObject( images.length() - 1 ),"size")?(String)getFieldObject(images.getJSONObject( images.length() - 1 ),"size","string"):"";
							if( size.equals("extralarge") ){
										return isFieldNotNull(images.getJSONObject( images.length() - 1 ),"#text")?(String)getFieldObject(images.getJSONObject( images.length() - 1 ),"#text","string"):"";
							}
					//}
			}else  return "";
						
														
			//}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}catch(NullPointerException e){
			return "";
		}
		
	    return "";
	}
	
	public static String formaterAudioPreviewUrl2Api_response(String response){ 
		
		JSONObject songObject = null;				
	
		
		try {
			songObject = new JSONObject(response);
			JSONArray resultsArray = isFieldNotNull(songObject,"results")?(JSONArray)getFieldObject(songObject,"results","jsonarray"):null;
			
			if(isNotNullOrEmptyThisArray(resultsArray)){
				

				return isFieldNotNull(resultsArray.getJSONObject(0),"artworkUrl100")?(String)getFieldObject(resultsArray.getJSONObject(0),"artworkUrl100","string"):"";
				
			}else return "";
			
						
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}catch(NullPointerException e){
			return "";
		}
		
	    return "";
	}
	
	public static String formaterTopArtistsByCategoryAPI_response(String response, boolean isWebVersion){
		
		JSONObject responseObj = null;				
		ArtistCollection artistsCollection = new ArtistCollection();
		
		try {
			
			responseObj = new JSONObject(response);
			
			JSONObject topArtistsJSONObj = isFieldNotNull(responseObj,"topartists")?(JSONObject)getFieldObject(responseObj,"topartists",""):null;
			
			JSONArray artistArray = isFieldNotNull(topArtistsJSONObj,"artist")?(JSONArray)getFieldObject(topArtistsJSONObj,"artist","jsonarray"):null;
			if(isNotNullOrEmptyThisArray(artistArray)){
				
				int limitProperty = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_ARTISTS_BY_CATEGORY_LIMIT));
				if(isWebVersion){
					limitProperty = Integer.parseInt(System.getProperty(AllInYourHandsConstants.PROPERTY_API_GROOVSHARK_ARTISTS_BY_CATEGORY_WEBLIMIT));
				}
				int maxIndex = (artistArray.length() > limitProperty )? limitProperty : artistArray.length();
				
				for(int i=0; i< maxIndex; i++){
					Artist songArtist = new Artist();					
					songArtist.setName(isFieldNotNull(artistArray.getJSONObject(i),"name")?(String)getFieldObject(artistArray.getJSONObject(i),"name","string"):"");
					
					JSONArray imageObj = isFieldNotNull(artistArray.getJSONObject(i),"image")?(JSONArray)getFieldObject(artistArray.getJSONObject(i),"image","jsonarray"):null;
					if(isNotNullOrEmptyThisArray(imageObj)){
						for(int j=0; j< imageObj.length(); j++){
							String size = isFieldNotNull(imageObj.getJSONObject(j),"size")?(String)getFieldObject(imageObj.getJSONObject(j),"size","string"):"";
							if(size.equals("medium") || size.equals("large") || size.equals("extralarge") ){
								songArtist.setPreviewImage(isFieldNotNull(imageObj.getJSONObject(j),"#text")?(String)getFieldObject(imageObj.getJSONObject(j),"#text","string"):"");
							}
						}
					}
					artistsCollection.add(songArtist);
					
				}
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("artists", ArtistCollection.class);
		xstreamXML.alias("artist", Artist.class);
		xstreamXML.addImplicitCollection(ArtistCollection.class, "artists");
		
		return xstreamXML.toXML(artistsCollection); // retorna xml do tipo  <artists> <artist> <artistName/> </artist> </artists>
	}
	
	public static String formaterTopCategoriesAPI_response(String response){ 
		
		JSONObject responseObj = null;				
		CategoryCollection categoriesCollection = new CategoryCollection();
		
		try {
			responseObj = new JSONObject(response);
			JSONObject toptags = isFieldNotNull(responseObj,"toptags")?(JSONObject)getFieldObject(responseObj,"toptags",""):null;
			JSONArray tags = isFieldNotNull(toptags,"tag")?(JSONArray)getFieldObject(toptags,"tag","jsonarray"):null;
			if(isNotNullOrEmptyThisArray(tags)){
				
					String categoriesNumber_string = System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_TAG_LIMIT);
					
					int categoriesNumber = Integer.parseInt( (categoriesNumber_string!=null) ? (categoriesNumber_string.equals("") ? "10" : categoriesNumber_string) : "10" );
					int categoriesLimitNumber = (categoriesNumber>tags.length())? tags.length() : categoriesNumber;
					for(int i=0; i<categoriesLimitNumber; i++){
						
						Category songCategory = new Category();						
						songCategory.setName(isFieldNotNull(tags.getJSONObject(i),"name")?(String)getFieldObject(tags.getJSONObject(i),"name","string"):"");
						
						categoriesCollection.add(songCategory);
						
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("categories", CategoryCollection.class);
		xstreamXML.alias("category", Category.class);
		xstreamXML.addImplicitCollection(CategoryCollection.class, "categories");
		
		return xstreamXML.toXML(categoriesCollection); // retorna xml do tipo  <categories> <category> <categoryName/> </category> </categories>
		
	}
	
	public static String formaterAudio2AudiobyCategoriesAPI_response(String response){ 
		
		JSONObject songObject = null;				
		SongCollection songCollection = new SongCollection();
		
		
		try {
			songObject = new JSONObject(response);
			JSONObject topTracksObj = isFieldNotNull(songObject,"toptracks")?(JSONObject)getFieldObject(songObject,"toptracks",""):null;
			
			JSONArray trackArrayObj = isFieldNotNull(topTracksObj,"track")?(JSONArray)getFieldObject(topTracksObj,"track","jsonarray"):null;
			
			if(isNotNullOrEmptyThisArray(trackArrayObj)){
								
					for(int i =0; i<trackArrayObj.length(); i++){
						Song song = new Song();
						song.setName( isFieldNotNull(trackArrayObj.getJSONObject(i),"name")?(String)getFieldObject(trackArrayObj.getJSONObject(i),"name","string"):"" );
						JSONObject artistObj = isFieldNotNull(trackArrayObj.getJSONObject(i),"artist")?(JSONObject)getFieldObject(trackArrayObj.getJSONObject(i),"artist",""):null;
						song.setArtistName(  isFieldNotNull(artistObj,"name")?(String)getFieldObject(artistObj,"name",""):"" );
						song.setUrl( isFieldNotNull(trackArrayObj.getJSONObject(i),"url")?(String)getFieldObject(trackArrayObj.getJSONObject(i),"url","string"):"" );
						
						JSONArray images = isFieldNotNull(trackArrayObj.getJSONObject(i),"image")?(JSONArray)getFieldObject(trackArrayObj.getJSONObject(i),"image","jsonarray"):null;
						if(images != null){
							if(images.length() > 0){
								for(int j=0; j< images.length(); j++){
									String size = isFieldNotNull(images.getJSONObject(j),"size")?(String)getFieldObject(images.getJSONObject(j),"size","string"):"";
									if(size.equals("medium") || size.equals("large") || size.equals("extralarge") ){
										song.setImagePreview( isFieldNotNull(images.getJSONObject(j),"#text")?(String)getFieldObject(images.getJSONObject(j),"#text","string"):"" );
									}
								}
							}
						}
						
						songCollection.add(song);
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("songs", SongCollection.class);
		xstreamXML.alias("song", Song.class);
		xstreamXML.addImplicitCollection(SongCollection.class, "songs");
		
		return xstreamXML.toXML(songCollection); // retorna xml do tipo: <songs> <song> <name/> <url/> <artistName/> <imagePreview/> </song> </songs>
	}
	
	
	
	public static String formaterAIAPI_response(String response){	
		return "";
	};
	
	
	public static Lyric formaterLyricsAPI_response(String response){
	
	
		JSONObject jsonObject = null;
		
		//log.log(Level.INFO,"---> Printing the response:  "+response);
		
		Lyric lyric = null;
		
		try {
			jsonObject = new JSONObject(response);
			
			JSONObject messageJSONObj = isFieldNotNull(jsonObject,"message")?(JSONObject)getFieldObject(jsonObject,"message",""):null; 
			
			JSONObject bodyJSONObj  = isFieldNotNull(messageJSONObj,"body")?(JSONObject)getFieldObject(messageJSONObj,"body",""):null; 
			
			JSONObject lyricsJSONObj  = isFieldNotNull(bodyJSONObj,"lyrics")?(JSONObject)getFieldObject(bodyJSONObj,"lyrics",""):null;
			
			lyric = new Lyric();
			
			lyric.setLyricBody( isFieldNotNull(lyricsJSONObj,"lyrics_body")?(String)getFieldObject(lyricsJSONObj,"lyrics_body","string"):"" );
			lyric.setCopyright( isFieldNotNull(lyricsJSONObj,"lyrics_copyright")?(String)getFieldObject(lyricsJSONObj,"lyrics_copyright","string"):"" );
						
						
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}		
			
		
		return lyric;
		
	}
		

	
	public static String formaterPlacesGeonamesAPI_response(String response){
		
		JSONObject jsonObject = null;
		CountryCollection countryCollection = new CountryCollection();
		
		try {
			jsonObject = new JSONObject(response);
						
			JSONArray geonames = isFieldNotNull(jsonObject,"geonames")?(JSONArray)getFieldObject(jsonObject,"geonames","jsonarray"):null; 
			if(isNotNullOrEmptyThisArray(geonames)){
				
					for(int i=0; i < geonames.length(); i++){
						Country country = new Country();
						country.setName(isFieldNotNull(geonames.getJSONObject(i),"countryName")?(String)getFieldObject(geonames.getJSONObject(i),"countryName","string"):"");
						String s = isFieldNotNull(geonames.getJSONObject(i),"countryCode")?(String)getFieldObject(geonames.getJSONObject(i),"countryCode","string"):"";
						country.setCode( s.equals("")? s : s.toLowerCase());
						country.setGeonameId(isFieldNotNull(geonames.getJSONObject(i),"geonameId")?(String)getFieldObject(geonames.getJSONObject(i),"geonameId","string"):"");
						
						countryCollection.add(country);
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("countries", CountryCollection.class);
		xstreamXML.alias("country", Country.class);
		xstreamXML.addImplicitCollection(CountryCollection.class, "countries");
		
		return xstreamXML.toXML(countryCollection); 
	}
	
	public static String formaterPlacesGeonamesStatesAPI_response(String response){
		
		JSONObject jsonObject = null;
		StateCollection stateCollection = new StateCollection();
		
		try {
			jsonObject = new JSONObject(response);
						
			JSONArray geonames = isFieldNotNull(jsonObject,"geonames")?(JSONArray)getFieldObject(jsonObject,"geonames","jsonarray"):null; 
			if(isNotNullOrEmptyThisArray(geonames)){
				
					for(int i=0; i < geonames.length(); i++){
						State state = new State();
						state.setName(isFieldNotNull(geonames.getJSONObject(i),"adminName1")?(String)getFieldObject(geonames.getJSONObject(i),"adminName1","string"):"");
					    state.setGeonameId(isFieldNotNull(geonames.getJSONObject(i),"geonameId")?(String)getFieldObject(geonames.getJSONObject(i),"geonameId","string"):"");
						
						stateCollection.add(state);
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("states", StateCollection.class);
		xstreamXML.alias("state", State.class);
		xstreamXML.addImplicitCollection(StateCollection.class, "states");
		
		return xstreamXML.toXML(stateCollection); 
	}
	
	public static String formaterPlacesGeocoding2API_response(String response){
		
		JSONObject jsonObject = null;
		Location location = null;
		String lat_lgt = "";
		
		try {
			jsonObject = new JSONObject(response);
						
			JSONArray resourceSetsArrayObj = isFieldNotNull(jsonObject,"resourceSets")?(JSONArray)getFieldObject(jsonObject,"resourceSets","jsonarray"):null; 
			if(isNotNullOrEmptyThisArray(resourceSetsArrayObj)){
				
					for(int i=0; i < resourceSetsArrayObj.length(); i++){
						
						JSONArray resourcesArrayObj = isFieldNotNull(resourceSetsArrayObj.getJSONObject(i),"resources")?(JSONArray)getFieldObject(resourceSetsArrayObj.getJSONObject(i),"resources","jsonarray"):null; 
						
						for(int j=0; j< resourcesArrayObj.length(); j++){
							JSONObject pointJSONObj = isFieldNotNull(resourcesArrayObj.getJSONObject(j),"point")?(JSONObject)getFieldObject(resourcesArrayObj.getJSONObject(j),"point",""):null; 
							JSONArray coordinates = isFieldNotNull(pointJSONObj,"coordinates")?(JSONArray)getFieldObject(pointJSONObj,"coordinates","jsonarray"):null; 
							if(coordinates != null){
								if(coordinates.length() > 1){
									
									lat_lgt = coordinates.getString(0)+","+coordinates.getString(1);
									
								}
							}
						}
						
						
					} 
					
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}		
				
		log.log(Level.INFO,"\n--> O ENDERE�O INFORMADO EM CORDENADAS DE LAT E LONG FICOU:  "+lat_lgt);
		
		return lat_lgt; // retorna algo como -23.5768634,-46.6431783
	}
	
   public static String formaterPlacesSuggestionsAPI_response(String response){
		
		JSONObject jsonObject = null;
		SuggestionCollection suggestionCollection = new SuggestionCollection();
		try {
			jsonObject = new JSONObject(response);
			
			JSONObject responseJSONObj =  isFieldNotNull(jsonObject,"response")?(JSONObject)getFieldObject(jsonObject,"response",""):null; 
			suggestionCollection.setTotalResults( isFieldNotNull(responseJSONObj,"totalResults")?(Integer)getFieldObject(responseJSONObj,"totalResults","int"):0  );
			
				
			JSONArray groups = isFieldNotNull(responseJSONObj,"groups")?(JSONArray)getFieldObject(responseJSONObj,"groups","jsonarray"):null; 
			
			if(isNotNullOrEmptyThisArray(groups)){
				
					for(int i=0; i < groups.length(); i++){
						
						Suggestion suggestion = new Suggestion();
						suggestion.setType(  isFieldNotNull(groups.getJSONObject(i),"type")?(String)getFieldObject(groups.getJSONObject(i),"type","string"):"" );
						
						JSONArray items = isFieldNotNull(groups.getJSONObject(i),"items")?(JSONArray)getFieldObject(groups.getJSONObject(i),"items","jsonarray"):null; 
												
						if(isNotNullOrEmptyThisArray(items)){							
									
								for(int j=0; j< items.length(); j++){
								 
								  Place place = new Place();
								 	
								  JSONObject venueObj =  isFieldNotNull(items.getJSONObject(j),"venue")?(JSONObject)getFieldObject(items.getJSONObject(j),"venue",""):null; 
									
									place.setId( isFieldNotNull(venueObj,"id")?(String)getFieldObject(venueObj,"id","string"):""  );
									place.setName( isFieldNotNull(venueObj,"name")?(String)getFieldObject(venueObj,"name","string"):""  );
									
									JSONObject contactObj = isFieldNotNull(venueObj,"contact")?(JSONObject)getFieldObject(venueObj,"contact",""):null; 
									place.setPhone( isFieldNotNull(contactObj,"phone")?(String)getFieldObject(contactObj,"phone","string"):""  );
									
									JSONObject locationObj = isFieldNotNull(venueObj,"location")?(JSONObject)getFieldObject(venueObj,"location",""):null; 
									place.setAddress( isFieldNotNull(locationObj,"address")?(String)getFieldObject(locationObj,"address","string"):""  );
									place.setState( isFieldNotNull(locationObj,"state")?(String)getFieldObject(locationObj,"state","string"):""  );
 									place.setCountry( isFieldNotNull(locationObj,"country")?(String)getFieldObject(locationObj,"country","string"):""  );
									
 									String latLong = (isFieldNotNull(locationObj,"lat")?(String)getFieldObject(locationObj,"lat","string"):"") + "," + (isFieldNotNull(locationObj,"lng")?(String)getFieldObject(locationObj,"lng","string"):"");
 									place.setLatLong(latLong);
 											
									String distance = isFieldNotNull(locationObj,"distance")?(String)getFieldObject(locationObj,"distance","string"):"";
									if(!distance.equals("")){
										int distanceIntValue = Integer.parseInt(distance);
										if(distanceIntValue > 1000){
											distance = distance.substring(0, 2).toCharArray()[0]+","+distance.substring(0, 2).toCharArray()[1]+" Km";
										}else{
											distance = distanceIntValue+" m";
										}
									}
									place.setDistance( distance  );
									place.setPostalCode( isFieldNotNull(locationObj,"postalCode")?(String)getFieldObject(locationObj,"postalCode","string"):""  );
									
									JSONArray categories = isFieldNotNull(venueObj,"categories")?(JSONArray)getFieldObject(venueObj,"categories","jsonarray"):null; 
									if(isNotNullOrEmptyThisArray(categories)){
																				
 											place.setCategoryName( isFieldNotNull(categories.getJSONObject(0),"name")?(String)getFieldObject(categories.getJSONObject(0),"name","string"):"" );
 											
 											JSONObject iconObj = isFieldNotNull(categories.getJSONObject(0),"icon")?(JSONObject)getFieldObject(categories.getJSONObject(0),"icon",""):null;
 											
 											String imgPrefix = isFieldNotNull(iconObj,"prefix")?(String)getFieldObject(iconObj,"prefix","string"):"";
 										    String imgSufix = isFieldNotNull(iconObj,"suffix")?(String)getFieldObject(iconObj,"suffix","string"):"";
 										    String imgURL = imgPrefix+"bg_88"+imgSufix; // "bg" serve para deixar a imagem mais escura e 88 � o tamanho (no caso m�ximo, mas poderia ter os seguintes valores: 32, 44, 64 ou 88)
 										    place.setImagePreviewURL(imgURL);
									}
																		
									place.setUrlSite( isFieldNotNull(venueObj,"url")?(String)getFieldObject(venueObj,"url","string"):"" );
									
									JSONObject statsJSONObj = isFieldNotNull(venueObj,"stats")?(JSONObject)getFieldObject(venueObj,"stats",""):null; 
									place.setCheckinsCount( isFieldNotNull(statsJSONObj,"checkinsCount")?(Integer)getFieldObject(statsJSONObj,"checkinsCount","int"):0 );
									place.setUsersCount( isFieldNotNull(statsJSONObj,"usersCount")?(Integer)getFieldObject(statsJSONObj,"usersCount","int"):0 );
									
									JSONObject hoursJSONObj = isFieldNotNull(venueObj,"hours")?(JSONObject)getFieldObject(venueObj,"hours",""):null; 
									if(hoursJSONObj != null){
										place.setStatusText(  isFieldNotNull(hoursJSONObj,"status")?(String)getFieldObject(hoursJSONObj,"status","string"):"" );
										place.setOpen( isFieldNotNull(hoursJSONObj,"isOpen")?(Boolean)getFieldObject(hoursJSONObj,"isOpen","boolean"):false );
									}
									JSONObject hereNowJSONObj = isFieldNotNull(venueObj,"hereNow")?(JSONObject)getFieldObject(venueObj,"hereNow",""):null; 
									if(hereNowJSONObj != null)
										place.setCount(  isFieldNotNull(hereNowJSONObj,"count")?(Integer)getFieldObject(hereNowJSONObj,"count","int"):0  );
									
								  JSONArray tipsObj =  isFieldNotNull(items.getJSONObject(j),"tips")?(JSONArray)getFieldObject(items.getJSONObject(j),"tips","jsonarray"):null; 	
								  	
								  if(isNotNullOrEmptyThisArray(tipsObj)){
										    
											for(int p=0; p< tipsObj.length(); p++){ // para cada dica dada por uma pessoa deste lugar, adicionamos as dicas ao lugar corrente
												
												Tip tip = new Tip(isFieldNotNull(tipsObj.getJSONObject(p),"text")?(String)getFieldObject(tipsObj.getJSONObject(p),"text","string"):"",
														                                  (isFieldNotNull(tipsObj.getJSONObject(p),"canonicalUrl")?(String)getFieldObject(tipsObj.getJSONObject(p),"canonicalUrl","string"):"").replace("\\", "")
														                                  );
											   
												place.add(tip);
											}
									}
									
								  	
								   if(!place.getCategoryName().equals("Neighborhood") && !place.getAddress().equals("") ) // adicionamos dicas sobre lugares exceto indica��es do pr�prio bairro
									   suggestion.add(place);
								}
							
						}
						
						suggestionCollection.add(suggestion);
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("suggestions", SuggestionCollection.class);
		xstreamXML.alias("suggestion", Suggestion.class);
		xstreamXML.addImplicitCollection(SuggestionCollection.class, "suggestions");
		xstreamXML.addImplicitCollection(Suggestion.class, "places");
		xstreamXML.alias("place", Place.class);
		xstreamXML.addImplicitCollection(Place.class, "tips");
		xstreamXML.alias("tip", Tip.class);
		
		
		
		return xstreamXML.toXML(suggestionCollection);
	}
   
    
   public static String formaterPlacesNearOfMeAPI_response(String response){
		
 		JSONObject jsonObject = null;
 		SuggestionCollection suggestionCollection = new SuggestionCollection();
 		
 		try {
 			jsonObject = new JSONObject(response);
 			
 			JSONObject responseJSONObj =  isFieldNotNull(jsonObject,"response")?(JSONObject)getFieldObject(jsonObject,"response",""):null; 
 			//suggestionCollection.setTotalResults( isFieldNotNull(responseJSONObj,"totalResults")?(Integer)getFieldObject(responseJSONObj,"totalResults","int"):0  );
 			
 				

 						Suggestion suggestion = new Suggestion();
 						//suggestion.setType(  isFieldNotNull(groups.getJSONObject(i),"type")?(String)getFieldObject(groups.getJSONObject(i),"type","string"):"" );
 						
 						JSONArray venuesObj = isFieldNotNull(responseJSONObj,"venues")?(JSONArray)getFieldObject(responseJSONObj,"venues","jsonarray"):null; 
 						
 						
 						if(isNotNullOrEmptyThisArray(venuesObj)){
 							
 							 																
 								for(int j=0; j< venuesObj.length(); j++){
 								 
 								  Place place = new Place();
 								 	
 								  JSONObject venueObj =  venuesObj.getJSONObject(j);  
 									
 									place.setId( isFieldNotNull(venueObj,"id")?(String)getFieldObject(venueObj,"id","string"):""  );
 									place.setName( isFieldNotNull(venueObj,"name")?(String)getFieldObject(venueObj,"name","string"):""  );
 									
 									JSONObject contactObj = isFieldNotNull(venueObj,"contact")?(JSONObject)getFieldObject(venueObj,"contact",""):null; 
 									place.setPhone( isFieldNotNull(contactObj,"phone")?(String)getFieldObject(contactObj,"phone","string"):""  );
 									
 									JSONObject locationObj = isFieldNotNull(venueObj,"location")?(JSONObject)getFieldObject(venueObj,"location",""):null; 
 									place.setAddress( isFieldNotNull(locationObj,"address")?(String)getFieldObject(locationObj,"address","string"):""  );
 									place.setState( isFieldNotNull(locationObj,"state")?(String)getFieldObject(locationObj,"state","string"):""  );
 									place.setCountry( isFieldNotNull(locationObj,"country")?(String)getFieldObject(locationObj,"country","string"):""  );
 									
 									String latLong = (isFieldNotNull(locationObj,"lat")?(String)getFieldObject(locationObj,"lat","string"):"") + "," + (isFieldNotNull(locationObj,"lng")?(String)getFieldObject(locationObj,"lng","string"):"");
 									place.setLatLong(latLong);
 									
 									String distance = isFieldNotNull(locationObj,"distance")?(String)getFieldObject(locationObj,"distance","string"):"";
 									if(!distance.equals("")){
										int distanceIntValue = Integer.parseInt(distance);
										if(distanceIntValue > 1000){
											distance = distance.substring(0, 2).toCharArray()[0]+","+distance.substring(0, 2).toCharArray()[1]+" Km";
										}else{
											distance = distanceIntValue+" m";
										}
									}
									place.setDistance( distance  );
 									 									
 									place.setPostalCode( isFieldNotNull(locationObj,"postalCode")?(String)getFieldObject(locationObj,"postalCode","string"):""  );
 									
 									JSONArray categories = isFieldNotNull(venueObj,"categories")?(JSONArray)getFieldObject(venueObj,"categories","jsonarray"):null; 
 									if(isNotNullOrEmptyThisArray(categories)){
 																							
 											place.setCategoryName( isFieldNotNull(categories.getJSONObject(0),"name")?(String)getFieldObject(categories.getJSONObject(0),"name","string"):"" );
 											
 										    JSONObject iconObj = isFieldNotNull(categories.getJSONObject(0),"icon")?(JSONObject)getFieldObject(categories.getJSONObject(0),"icon",""):null;
 											
 											String imgPrefix = isFieldNotNull(iconObj,"prefix")?(String)getFieldObject(iconObj,"prefix","string"):"";
 										    String imgSufix = isFieldNotNull(iconObj,"suffix")?(String)getFieldObject(iconObj,"suffix","string"):"";
 										    String imgURL = imgPrefix+"bg_88"+imgSufix; // "bg" serve para deixar a imagem mais escura e 88 � o tamanho (no caso m�ximo, mas poderia ter os seguintes valores: 32, 44, 64 ou 88)
 										    place.setImagePreviewURL(imgURL);
 										
 									}
 									
 									place.setUrlSite( (isFieldNotNull(venueObj,"url")?(String)getFieldObject(venueObj,"url","string"):"") );
 									
 									JSONObject statsJSONObj = isFieldNotNull(venueObj,"stats")?(JSONObject)getFieldObject(venueObj,"stats",""):null; 
 									place.setCheckinsCount( isFieldNotNull(statsJSONObj,"checkinsCount")?(Integer)getFieldObject(statsJSONObj,"checkinsCount","int"):0 );
 									place.setUsersCount( isFieldNotNull(statsJSONObj,"usersCount")?(Integer)getFieldObject(statsJSONObj,"usersCount","int"):0 );
 									

 									// campos a desconsiderar
 									place.setCategoryName(null);
 									
 								   if( !place.getAddress().equals("") ) // somente adicionamos locais que possuem endere�o e telefone
 									   suggestion.add(place);
 								}
 							//}
 						}
 						
 						suggestionCollection.add(suggestion);

 			
 		}catch (JSONException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			
 		}
 		
 		xstreamXML.alias("places_near_of_you", SuggestionCollection.class);
 		xstreamXML.alias("places", Suggestion.class);
 		xstreamXML.addImplicitCollection(SuggestionCollection.class, "suggestions");
 		xstreamXML.addImplicitCollection(Suggestion.class, "places");
 		xstreamXML.alias("place", Place.class);
 		
 		
 		
 		return xstreamXML.toXML(suggestionCollection); // retorna xml do tipo <places__near__of__you> <places> <type/> <place> <name/><address/><distance/><postalCode/><usersCount/> </place> </places></places__near__of__you>
 	}
   
    public static String formaterShortnerUrlAPI_response(String response){
		
    	log.log(Level.INFO,"\n--->RESPONSE OF SHORTENER API:\n"+response);
    	
		JSONObject jsonObject = null;
		String shortURL = "";
		
		try {
			jsonObject = new JSONObject(response);
			
			shortURL = isFieldNotNull(jsonObject,"id")?(String)getFieldObject(jsonObject,"id","string"):null; 
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
		
		return shortURL;
	}
    
    public static String formaterStreamingSessionID_response(String response){
    	
    	//{"header":{"hostname":"RHL081"},"result":{"success":true,"sessionID":"b88ed639bcfe5b6406dfcb5db18ed7e0"}}
    	JSONObject jsonObject = null;
		String sessionId = "";
		
		try {
			jsonObject = new JSONObject(response);
			
			JSONObject resultObj = isFieldNotNull(jsonObject,"result")?(JSONObject)getFieldObject(jsonObject,"result",""):null; 
			boolean success = isFieldNotNull(resultObj,"success")?(Boolean)getFieldObject(resultObj,"success","boolean"):false; 
			if(success){
				sessionId = isFieldNotNull(resultObj,"sessionID")?(String)getFieldObject(resultObj,"sessionID","string"):""; 
			}
			
		}catch (JSONException e) {			
			return "";			
		}catch(Exception e){
			return "";
		}
		
		return sessionId;
    	
    }
    
    public static String formaterStreamingGetCountry_response(String response){
    	
    	//{"header":{"hostname":"RHL081"},"result":{"ID":223,"CC1":0,"CC2":0,"CC3":0,"CC4":1073741824,"DMA":751,"IPR":0}}
    	JSONObject jsonObject = null;
    	JSONObject resultObj = null;
		String responseFormatted = "";
		
		log.log(Level.INFO,"\n-->Responde GetCountry: "+response);
		
		try {
			jsonObject = new JSONObject(response);
			
			resultObj = isFieldNotNull(jsonObject,"result")?(JSONObject)getFieldObject(jsonObject,"result",""):null; 
			Integer ID = isFieldNotNull(resultObj,"ID")?(Integer)getFieldObject(resultObj,"ID","int"):0;
			Integer CC1 = isFieldNotNull(resultObj,"CC1")?(Integer)getFieldObject(resultObj,"CC1","int"):0;
			Integer CC2 = isFieldNotNull(resultObj,"CC2")?(Integer)getFieldObject(resultObj,"CC2","int"):0;
			Integer CC3 = isFieldNotNull(resultObj,"CC3")?(Integer)getFieldObject(resultObj,"CC3","int"):0;
			Integer CC4 = isFieldNotNull(resultObj,"CC4")?(Integer)getFieldObject(resultObj,"CC4","int"):0;
			Integer DMA = isFieldNotNull(resultObj,"DMA")?(Integer)getFieldObject(resultObj,"DMA","int"):0;
			Integer IPR = isFieldNotNull(resultObj,"IPR")?(Integer)getFieldObject(resultObj,"IPR","int"):0;
			
			responseFormatted = "\"ID\":\""+ID+"\",\"CC1\":\""+CC1+"\",\"CC2\":\""+CC2+"\",\"CC3\":\""+CC3+"\",\"CC4\":\""+CC4+"\",\"DMA\":\""+DMA+"\",\"IPR\":\""+IPR+"\"";
			
			
		}catch (JSONException e) {	
			
			e.printStackTrace();
			return "";			
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
		return (resultObj!=null)?responseFormatted:"";
    	
    }
   
  public static String formaterStreamingKeyURL_response(String response){
    	
	  //{"header":{"hostname":"RHL072"},"result":{"StreamKey":"49f96f87d901402998409bcc616cffec802e8c819_4dc072cd_1a8c758_1d2b5ce_19f7ebe3_8","url":"http://stream57-he.grooveshark.com/stream.php?streamKey=49f96f87d901402998409bcc616cffec802e8c819_4dc072cd_1a8c758_1d2b5ce_19f7ebe3_8", "StreamServerID":32768, "uSecs":220000000}}
		
	    log.log(Level.INFO,"\n---> StreamingKey Response: "+response);
	  
    	JSONObject jsonObject = null;
    	JSONObject resultObj = null;
		
    	String StreamKey = "";
    	String url = "";
    	String StreamServerID = "";
    	
		try {
			jsonObject = new JSONObject(response);
			
			resultObj = isFieldNotNull(jsonObject,"result")?(JSONObject)getFieldObject(jsonObject,"result",""):null; 
			StreamKey = isFieldNotNull(resultObj,"StreamKey")?(String)getFieldObject(resultObj,"StreamKey","string"):null; 
			url =  isFieldNotNull(resultObj,"url")?(String)getFieldObject(resultObj,"url","string"):null; 
			StreamServerID = isFieldNotNull(resultObj,"StreamServerID")?(String)getFieldObject(resultObj,"StreamServerID","string"):null; 
			
		}catch (JSONException e) {			
			return "error";			
		}catch(Exception e){
			return "error";
		}
		
		return url+"#"+StreamKey+"#"+StreamServerID;
    	
    }
  
    public static String formaterBannersWebAPI_response(String response){
		
		JSONObject jsonObject = null;
		BannerCollection bannerCollection = new BannerCollection();
		
		try {
			jsonObject = new JSONObject(response);
						
			JSONArray data = isFieldNotNull(jsonObject,"data")?(JSONArray)getFieldObject(jsonObject,"data","jsonarray"):null; 
			if(isNotNullOrEmptyThisArray(data)){
				
				    
					for(int i=0; i < data.length(); i++){
						
						JSONArray campaigns = isFieldNotNull(data.getJSONObject(i),"campaign")?(JSONArray)getFieldObject(data.getJSONObject(i),"campaign","jsonarray"):null; 
						if(isNotNullOrEmptyThisArray(campaigns)){
							for(int j=0; j < campaigns.length(); j++){ // percorrendo as campanhas
								Banner banner = new Banner();
								banner.setCid(isFieldNotNull(campaigns.getJSONObject(i),"cid")?(String)getFieldObject(campaigns.getJSONObject(i),"cid","string"):"");
								banner.setImgsize(isFieldNotNull(campaigns.getJSONObject(i),"imgsize")?(String)getFieldObject(campaigns.getJSONObject(i),"imgsize","string"):"");
								banner.setUrlImage(isFieldNotNull(campaigns.getJSONObject(i),"url_img")?(String)getFieldObject(campaigns.getJSONObject(i),"url_img","string"):"");
								banner.setUrlClick(isFieldNotNull(campaigns.getJSONObject(i),"url_click")?(String)getFieldObject(campaigns.getJSONObject(i),"url_click","string"):"");
								
								bannerCollection.add(banner);
							}
						}
						
						
					}
				
			}
			
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		xstreamXML.alias("banners", BannerCollection.class);
		xstreamXML.alias("banner", Banner.class);
		xstreamXML.addImplicitCollection(BannerCollection.class, "banners");
		
		return xstreamXML.toXML(bannerCollection); 
	 }
    
	

	


	 */
	
	
}
