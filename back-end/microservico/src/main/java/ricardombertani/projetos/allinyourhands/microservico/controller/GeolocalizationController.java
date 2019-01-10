package ricardombertani.projetos.allinyourhands.microservico.controller;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ricardombertani.projetos.allinyourhands.apidata.places.Place;
import ricardombertani.projetos.allinyourhands.apidata.places.Suggestion;
import ricardombertani.projetos.allinyourhands.apidata.places.SuggestionCollection;
import ricardombertani.projetos.allinyourhands.microservico.util.ApiUrlMaker;
import ricardombertani.projetos.allinyourhands.microservico.util.ResponseFormater;

import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/v1")
public class GeolocalizationController {

    private static Logger log = Logger.getLogger(GeolocalizationController.class.getName());

    @Autowired
    private Environment env;

    @RequestMapping(path = "/distance", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getDistance(@RequestParam("origin") String origin,@RequestParam("destination") String destination ){

        RestTemplate restTemplate = new RestTemplate();

        String geocodingUrlOrigin = makeGeocodingURL(origin);
        log.log(Level.INFO,"geocodingUrlOrigin: "+geocodingUrlOrigin);
        String geocodingResponseOrigin  = ResponseFormater.formaterPlacesGeocodingAPI_response(restTemplate.getForObject(geocodingUrlOrigin, String.class));
        log.log(Level.INFO,"geocodingResponseOrigin: "+geocodingResponseOrigin);


        String geocodingUrlDestination = makeGeocodingURL(destination);
        log.log(Level.INFO,"geocodingUrlDestination: "+geocodingUrlDestination);
        String geocodingResponseDestination = ResponseFormater.formaterPlacesGeocodingAPI_response(restTemplate.getForObject(geocodingUrlDestination, String.class));
        log.log(Level.INFO,"geocodingResponseDestination: "+geocodingResponseDestination);

        String distanceUrl = makeDistanceApiURL(geocodingResponseOrigin, geocodingResponseDestination);
        log.log(Level.INFO,"distanceUrl: "+distanceUrl);
        String distanceResponse = restTemplate.getForObject(distanceUrl, String.class);

        JSONObject responseJSON = new JSONObject();
        try {
            JSONObject jsonRootObject = new JSONObject(distanceResponse);
            JSONArray rowsArray = jsonRootObject.getJSONArray("rows");
            JSONObject genericObject = rowsArray.getJSONObject(0);

            String originValue = jsonRootObject.getJSONArray("origin_addresses").get(0).toString();
            responseJSON.put("origem",originValue);

            String destinationValue = jsonRootObject.getJSONArray("destination_addresses").get(0).toString();
            responseJSON.put("destino",destinationValue);

            JSONArray elementsArray = genericObject.getJSONArray("elements");
            JSONObject elementsObj = elementsArray.getJSONObject(0);

            String distanciaKm = elementsObj.getJSONObject("distance").getString("text");
            responseJSON.put("distancia",distanciaKm);

            String duration = elementsObj.getJSONObject("duration").getString("text");
            responseJSON.put("duracao",duration);

            String justDistanceNumber = distanciaKm.trim().replaceAll("km","").replaceAll(",",".");
            Double distanceDouble = new Double(justDistanceNumber);

            responseJSON.put("menos_de_1km",distanceDouble<=1.0?"sim":"nao");

        }catch(JSONException e){
            e.printStackTrace();
        }


       return responseJSON.toString();


    }

    @RequestMapping(path = "/places", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<Place> getPlaces(@RequestParam("query") String query, @RequestParam("latAndLong") String latAndLong,
                            @RequestParam("countryCode") String countryCode,
                            @RequestParam("section") String section,
                            @RequestParam("offsetPlaces") String offsetPlaces
                            ){

        RestTemplate restTemplate = new RestTemplate();

        String placesSuggestedUrl = makePlacesSuggestionsURL(query,latAndLong,countryCode, section, offsetPlaces);
        log.log(Level.INFO,"Requested Suggested Places API... URL: "+placesSuggestedUrl);
        String placesSuggestedResponse = restTemplate.getForObject(placesSuggestedUrl,String.class);
        SuggestionCollection suggestionCollection = ResponseFormater.formaterPlacesSuggestionsAPI_response(placesSuggestedResponse);

        String placesNearMeUrl = makePlacesNearOfMeURL(query,latAndLong,countryCode, section, offsetPlaces);
        log.log(Level.INFO,"Requested Places Near Me API... URL: "+placesNearMeUrl);
        String placesNearMeResponse = restTemplate.getForObject(placesNearMeUrl,String.class);
        SuggestionCollection suggestionCollection2 = ResponseFormater.formaterPlacesNearOfMeAPI_response(placesNearMeResponse);

        SuggestionCollection allPlaceSuggestions = new SuggestionCollection();
        List<Suggestion> suggestions1 = suggestionCollection.getSuggestions();
        List<Suggestion> suggestions2 = suggestionCollection2.getSuggestions();

        List<Place> places1 = suggestions1.get(0).getPlaces();
        List<Place> places2 = suggestions2.get(0).getPlaces();
        places1.addAll(places2);


        return places1;

    }




    private  Properties getPlacesCommonProperties(String query, String latAndLong, String countryCode, String section, String offsetPlaces){
        Properties parameters = new Properties();

        String clientAndSecret = foursquareKeyReservBalancer();
        String clientAndSecretVector[] = clientAndSecret.split("\\|");

        parameters.setProperty("client_id",clientAndSecretVector[0]);
        parameters.setProperty("client_secret", clientAndSecretVector[1]);
        parameters.setProperty("ll", latAndLong);
        parameters.setProperty("query",query);
        parameters.setProperty("section", section); // section seria a categoria de sugestao (food, drinks, coffee, shops, arts, outdoors, sights, trending or specials, nextVenues or topPicks )
        parameters.setProperty("limit", System.getenv("places_resultsperpage")); // limite de resultados por vez
        parameters.setProperty("locale", countryCode);
        parameters.setProperty("v", "20131104"); // a data de atualizacao da API

        int offSetIntegerValur = Integer.valueOf(offsetPlaces) * Integer.valueOf(System.getenv("places_resultsperpage"));
        parameters.setProperty("offset",  String.valueOf(offSetIntegerValur)  );


        return parameters;
    }


    private  String foursquareKeyReservBalancer(){

        int rangeLimit = Integer.parseInt(System.getenv("foursquare_key_selector"));
        Random randomGenerator = new Random();
        int keyIndex = randomGenerator.nextInt(rangeLimit);

        switch(keyIndex){
            case 0:
                log.log(Level.INFO,"Using PROPERTY_API_FOURSQUARE_KEY");
                return System.getenv("foursquare_client_id_1")+"|"+System.getenv("foursquare_secret_1");

            case 1:
                log.log(Level.INFO,"Using PROPERTY_API_FOURSQUARE_KEY2");
                return System.getenv("foursquare_client_id_2")+"|"+System.getenv("foursquare_secret_2");
        }

        return System.getenv("foursquare_client_id_1")+"|"+System.getenv("foursquare_secret_1");//System.getProperty(AllInYourHandsConstants.PROPERTY_API_LASTFM_KEY);


    }


    public String makeGeocodingURL(String address){
        // os parametros desta API
        Properties parameters = new Properties();
        parameters.setProperty("address",address);
        parameters.setProperty("sensor", "false"); // setamos o sensor aqui para false pois executamos esta api do servidor e n�o de um cel
        parameters.setProperty("key", ApiUrlMaker.googleKeyReservBalancer(
                        System.getenv("google_keyreserv_selector"),
                        System.getenv("google_general_key"),
                        System.getenv("google_general_key_reserv1"),
                        System.getenv("google_general_key_reserv2"),
                        System.getenv("google_general_key_reserv3")
                )

        );

        return ApiUrlMaker.makeApiURL(env.getProperty("geolocalization.geocodeAPIBaseURL"), parameters).replaceAll("  ", " ").replaceAll(" ", "+");
    }

    public String makeDistanceApiURL(String origins, String destinations){

        // os parametros desta API
        Properties parameters = new Properties();
        parameters.setProperty("origins",origins);
        parameters.setProperty("destinations",destinations);
        parameters.setProperty("mode","walking");  //driving , walking,bicycling ,transit
        //  parameters.setProperty("language", language);
        parameters.setProperty("key", ApiUrlMaker.googleKeyReservBalancer(
                System.getenv("google_keyreserv_selector"),
                System.getenv("google_general_key"),
                System.getenv("google_general_key_reserv1"),
                System.getenv("google_general_key_reserv2"),
                System.getenv("google_general_key_reserv3")
                )

        );

        return ApiUrlMaker.makeApiURL(env.getProperty("geolocalization.distanceAPIBaseURL"), parameters);

    }

    public String makePlacesSuggestionsURL(String query, String latAndLong,String countryCode, String section, String offsetPlaces){

        // os parametros desta API
        Properties parameters = getPlacesCommonProperties(query,latAndLong, countryCode,section,offsetPlaces);

        return ApiUrlMaker.makeApiURL(env.getProperty("geolocalization.suggestionPlacesBaseURL"), parameters);
    }

    public String makePlacesNearOfMeURL(String query,String latAndLong,String countryCode, String section, String offsetPlaces){

        // os parametros desta API
        Properties parameters = getPlacesCommonProperties(query,latAndLong, countryCode, section, offsetPlaces);

        return ApiUrlMaker.makeApiURL(env.getProperty("geolocalization.placesNearmeBaseURL"), parameters);
    }

}
