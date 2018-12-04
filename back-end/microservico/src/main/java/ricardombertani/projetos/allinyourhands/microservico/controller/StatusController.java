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
                "<audio>"+ env.getProperty("general.status.audios")  +"</audio>"+
                "<video>"+ env.getProperty("general.status.videos") +"</video>"+
                "<lyric>" + env.getProperty("general.status.lyrics") + "</lyric>"+
                "<book>" + env.getProperty("general.status.books") +"</book>"+
                "<placeSug>" + env.getProperty("general.status.suggestions") + "</placeSug>" +
                "<placeNear>" +  env.getProperty("general.status.geolocalization") + "</placeNear>" +
                "<weather>" + env.getProperty("general.status.weather") + "</weather>" +
                "<directions>" + env.getProperty("general.status.geolocalization") + "</directions>" +
                "<goods>0</goods>" +
                "<chat>0</chat>" +
                "<streamingSessionID>" +""+ "</streamingSessionID>" +
                "<billingKey>"+System.getenv("general.status.billingKey")+"</billingKey>"+
                "<gcmProjectNumber>"+System.getenv("general.status.gtmprojectnumber")+"</gcmProjectNumber>"+
                "</statusAPIs>";


        JSONObject xmlJSONObj = new JSONObject();

        try {
            xmlJSONObj = XML.toJSONObject(xmlResponse);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return xmlJSONObj.toString();

    }

}
