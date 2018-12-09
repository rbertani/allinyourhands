package ricardombertani.projetos.allinyourhands.microservico.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ricardombertani.projetos.allinyourhands.apidata.book.BooksVolumeCollection;
import ricardombertani.projetos.allinyourhands.microservico.util.ApiUrlMaker;
import ricardombertani.projetos.allinyourhands.microservico.util.ResponseFormater;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/v1/status")
public class StatusController {

    private static Logger log = Logger.getLogger(StatusController.class.getName());

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getStatus() {

        String xmlResponse = "<statusAPIs>"+
                "<audio>"+ System.getenv("general_status_audios")  +"</audio>"+
                "<video>"+ System.getenv("general_status_videos") +"</video>"+
                "<lyric>" + System.getenv("general_status_lyrics") + "</lyric>"+
                "<book>" + System.getenv("general_status_books") +"</book>"+
                "<placeSug>" + System.getenv("general_status_suggestions") + "</placeSug>" +
                "<placeNear>" +  System.getenv("general_status_geolocalization") + "</placeNear>" +
                "<weather>" + System.getenv("general_status_weather") + "</weather>" +
                "<directions>" + System.getenv("general_status_geolocalization") + "</directions>" +
                "<goods>0</goods>" +
                "<chat>0</chat>" +
                "<streamingSessionID>" +""+ "</streamingSessionID>" +
                "<billingKey>"+System.getenv("general_status_billingKey")+"</billingKey>"+
                "<gcmProjectNumber>"+System.getenv("general_status_gtmprojectnumber")+"</gcmProjectNumber>"+
                "</statusAPIs>";


        JSONObject xmlJSONObj = new JSONObject();

        try {
            xmlJSONObj = XML.toJSONObject(xmlResponse);
        }catch (JSONException e){
            e.printStackTrace();
        }

        for(int i=0;i<100000000;i++){
            System.out.println("processando ...");
        };

        return xmlJSONObj.toString();

    }

}
