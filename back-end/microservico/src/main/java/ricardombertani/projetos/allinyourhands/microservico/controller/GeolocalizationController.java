package ricardombertani.projetos.allinyourhands.microservico.controller;

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
import ricardombertani.projetos.allinyourhands.microservico.util.ApiUrlMaker;
import ricardombertani.projetos.allinyourhands.microservico.util.ResponseFormater;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/v1/distance")
public class GeolocalizationController {

    private static Logger log = Logger.getLogger(GeolocalizationController.class.getName());

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getDistance(@RequestParam("origin") String origin,@RequestParam("destination") String destination ){

        ApiUrlMaker apiUrlMaker = new ApiUrlMaker();
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

}
