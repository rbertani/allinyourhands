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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/v1/all")
public class AllContentsControler {

    private static Logger log = Logger.getLogger(AllContentsControler.class.getName());

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getAllContents(@RequestParam("query") String query, @RequestParam("latAndLong") String latAndLong,
                                 @RequestParam("countryCode") String countryCode,
                                 @RequestParam("section") String section,
                                 @RequestParam("pageNumber") String pageNumber,
                                 @RequestParam("nextpagetoken") String nextpagetoken
    ){

        String genericResult = "";

        RestTemplate restTemplate = new RestTemplate();

        String awsServiceBaseURL = System.getenv("awsservice_baseurl");

        String booksApiURL = awsServiceBaseURL+"/rest/v1/books?keyword="+query+"&pagenumber="+pageNumber+"&countryCode="+countryCode;
        String booksResult = "[]";

        String placesApiURL = awsServiceBaseURL+"/rest/v1/places?query="+query+"&latAndLong="+latAndLong+"&countryCode="+countryCode+"&section="+section+"&offsetPlaces="+pageNumber;
        String placesResult = "[]";

        String videosApiURL = awsServiceBaseURL+"/rest/v1/videos?q="+query+"&nextpagetoken="+nextpagetoken;
        String videosResult = "[]";

        try {
            booksResult = restTemplate.getForObject(booksApiURL, String.class);
            placesResult = restTemplate.getForObject(placesApiURL, String.class);
        }catch(Exception e){
            e.printStackTrace();
        }


        List<JSONObject> genericContentResult = new ArrayList<JSONObject>();
        List<JSONObject> booksList = new ArrayList<JSONObject>();
        List<JSONObject> placesList = new ArrayList<JSONObject>();

        try {
            JSONArray books = new JSONArray(booksResult);
            JSONArray places = new JSONArray(placesResult);

            for(int i=0;i<books.length();i++){

                JSONObject tmpObject = new JSONObject();

                tmpObject.put("id" , ((JSONObject)books.get(i)).get("id") );
                tmpObject.put("type" , "books" );
                tmpObject.put("title" , ((JSONObject)books.get(i)).getJSONObject("volumeInfo").get("title") +"<br /> Autor(es): " + ((JSONObject)books.get(i)).getJSONObject("volumeInfo").get("authors") );
                //tmpObject.put("subtitle" , ((JSONObject)books.get(i)).getJSONObject("volumeInfo").get("subtitle") );
                tmpObject.put("description" , ((JSONObject)books.get(i)).getJSONObject("volumeInfo").get("description") );
                tmpObject.put("image" , ((JSONObject)books.get(i)).getJSONObject("volumeInfo").getJSONObject("imageLink").get("smallThumbnail") );
                tmpObject.put("htmlContent" , ((JSONObject)books.get(i)).get("webReaderLink") );


                booksList.add(tmpObject);
            }

            for(int i=0;i<places.length();i++){

                JSONObject tmpObject = new JSONObject();

                tmpObject.put("id" , ((JSONObject)places.get(i)).get("id") );
                tmpObject.put("type" , "places" );
                tmpObject.put("title" , ((JSONObject)places.get(i)).get("name")  + "<br />"+((JSONObject)places.get(i)).get("address") );
                tmpObject.put("description" ,"<br />Categoria: " +((JSONObject)places.get(i)).get("categoryName")+ "<br />CEP: " +((JSONObject)places.get(i)).get("postalCode")+"<br />Distancia: " +
                        ((JSONObject)places.get(i)).get("distance"));

                tmpObject.put("image" , ((JSONObject)places.get(i)).getString("imagePreviewURL") );

                placesList.add(tmpObject);
            }

            genericContentResult.addAll(booksList);
            genericContentResult.addAll(placesList);

            Collections.shuffle(genericContentResult);


        }catch(JSONException e){
            e.printStackTrace();
        }

        JSONArray finalResult = new JSONArray(genericContentResult);


        return finalResult.toString();
    }

}
