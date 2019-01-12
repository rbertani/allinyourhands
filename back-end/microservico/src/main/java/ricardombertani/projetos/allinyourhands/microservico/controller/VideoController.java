package ricardombertani.projetos.allinyourhands.microservico.controller;

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
@RequestMapping(path = "/rest/v1/videos")
public class VideoController {

    private static Logger log = Logger.getLogger(VideoController.class.getName());

    @Autowired
    private Environment env;

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public String getVideos(@RequestParam("q") String query, @RequestParam("nextpagetoken") String nextpagetoken) {

        RestTemplate restTemplate = new RestTemplate();

        String videoRequestUrl = makeVideoApiURL(query,"",nextpagetoken);
        log.log(Level.INFO,"Requested Video API... URL: "+videoRequestUrl);

        String response = restTemplate.getForObject(videoRequestUrl,String.class);

        return ResponseFormater.formaterVideoAPI_response(env.getProperty("videos.youtubeDirectUrl"),response);

    }

    public  String makeVideoApiURL(String text, String categoryId, String  nextPageToken){
        String baseURL =  env.getProperty("videos.youtubeAPIBaseUrl");
        // os parametros desta API
        Properties parameters = new Properties();
        parameters.setProperty("part","snippet");  // valor default (pode ser alterado, mas funciona bem assim)
        parameters.setProperty("q",text);
        parameters.setProperty("key",
                ApiUrlMaker.googleKeyReservBalancer(
                System.getenv("google_keyreserv_selector"),
                System.getenv("google_general_key"),
                System.getenv("google_general_key_reserv1"),
                System.getenv("google_general_key_reserv2"),
                System.getenv("google_general_key_reserv3")
                )
        );
        parameters.setProperty("type", "video"); // por padr�o mantemos o tipo como v�deo
        parameters.setProperty("maxResults", env.getProperty("videos.resultsPerPage")); // quantidade maxima de resultados por pagina
        // parameters.setProperty("videoCategoryId", categoryId);   Removida flag de categoria em 10/02/2016 - ouve uma alteracao na api do youtube, este campo nao é mais aceito estando vazio
        parameters.setProperty("safesearch","strict");
        // parameters.setProperty("regionCode", regionCode);


        parameters.setProperty("pageToken", nextPageToken); // parametro que ficar� responsavel por sempre mostrar o proximo request, o token � obtido atrav�s do campo "nextPageToken"

        return ApiUrlMaker.makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+"); // substituimos espa�os por "+" por um problema no request das APIs da Google

    }

    /*
    public String makeVideoCategoriesApiURL(String regionCode){

        String baseURL =  System.getProperty(AllInYourHandsConstants.PROPERTY_API_YOUTUBE_VIDEO_CATEGORIES_BASE_URL);
        // os parametros desta API
        Properties parameters = new Properties();
        parameters.setProperty("part","snippet");
        String correctRegionCode = (!regionCode.equals("pt"))?"us":"pt";
        parameters.setProperty("regionCode",correctRegionCode);
        parameters.setProperty("hl",regionCode); // parametro que faz com que os resultados fiquem na linguagem correta
        parameters.setProperty("key", ApiUrlMaker.googleKeyReservBalancer(
                System.getenv("google.keyreserv.selector"),
                System.getenv("google.general.key"),
                System.getenv("google.general.key.reserv1"),
                System.getenv("google.general.key.reserv2"),
                System.getenv("google.general.key.reserv3")
        ));

        return ApiUrlMaker.makeApiURL(baseURL, parameters).replaceAll("  ", " ").replaceAll(" ", "+");

    }
*/


}
