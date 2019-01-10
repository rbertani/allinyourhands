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

import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/v1/all")
public class AllContentsControler {

    private static Logger log = Logger.getLogger(AllContentsControler.class.getName());

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getPlaces(@RequestParam("query") String query, @RequestParam("latAndLong") String latAndLong,
                                 @RequestParam("countryCode") String countryCode,
                                 @RequestParam("section") String section,
                                 @RequestParam("pageNumber") String pageNumber
    ){

        String genericResult = "";

        RestTemplate restTemplate = new RestTemplate();

        String booksApiURL = "http://localhost:8080/rest/v1/books?keyword="+query+"java&pagenumber="+pageNumber+"&countryCode="+countryCode;
        String booksResult = restTemplate.getForObject(booksApiURL, String.class);

        String placesApiURL = "http://localhost:8080/rest/v1/places?query="+query+"&latAndLong="+latAndLong+"&countryCode="+countryCode+"&section="+section+"&offsetPlaces="+pageNumber;
        String placesResult = restTemplate.getForObject(placesApiURL, String.class);

        String videosApiURL = "";

        JSONArray genericContentResult = new JSONArray();

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


                genericContentResult.put(tmpObject);
            }

            for(int i=0;i<places.length();i++){

                JSONObject tmpObject = new JSONObject();

                tmpObject.put("id" , ((JSONObject)places.get(i)).get("id") );
                tmpObject.put("type" , "places" );
                tmpObject.put("title" , ((JSONObject)places.get(i)).get("name")  + "<br />"+((JSONObject)places.get(i)).get("address") );
                tmpObject.put("description" ,"Categoria: " + "<br />" + "Phone: " + "<br />" + ((JSONObject)places.get(i)).get("phone") + "<br />" + ((JSONObject)places.get(i)).get("categoryName") +"CEP: " + "<br />" + ((JSONObject)places.get(i)).get("postalCode") + "<br />" + ((JSONObject)places.get(i)).get("distance"));
                tmpObject.put("image" , ((JSONObject)places.get(i)).getString("imagePreviewURL") );

                genericContentResult.put(tmpObject);
            }

            // shuffle the contents
            int minorSize =  (books.length() < places.length())? books.length() : places.length();

            int index = 0;
            while(index < minorSize){
                if(index%2 == 0)
                    genericContentResult.put(books.get(index));
                else genericContentResult.put(places.get(index));

                index++;
            }


        }catch(JSONException e){
            e.printStackTrace();
        }


        return genericContentResult.toString();
    }

    public static JSONArray shuffleJsonArray (JSONArray array) throws JSONException {

        Random rnd = new Random();
        for (int i = array.length() - 1; i >= 0; i--)
        {
            int j = rnd.nextInt(i + 1);
            // Simple swap
            Object object = array.get(j);
            array.put(j, array.get(i));
            array.put(i, object);
        }
        return array;
    }

}
