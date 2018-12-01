package ricardombertani.projetos.allinyourhands.microservico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ricardombertani.projetos.allinyourhands.microservico.util.ApiUrlMaker;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest/v2/")
public class BookController {

    private static Logger log = Logger.getLogger(BookController.class.getName());
    public static final String RESOURCES_DIRECTORY_PATH = new File("").getAbsolutePath() + File.separator
            + "/src/main/resources/";

    @Autowired
    private Environment env;


    @RequestMapping(path = "books", method = RequestMethod.GET)
    @ResponseBody
    public String getBooks(@RequestParam("keyword") String keyword,@RequestParam("pagenumber") String pagenumber,@RequestParam("countryCode") String countryCode ) {

        ApiUrlMaker apiUrlMaker = new ApiUrlMaker();
        String requestedApiUrl = makeBooksApiURL(keyword, pagenumber,countryCode);

        log.log(Level.INFO,"Requested Book API... URL: "+requestedApiUrl);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestedApiUrl, String.class);

        return result;

    }


    public String makeBooksApiURL(String text, String startIndex, String countryCode){

        // os parametros desta API
        Properties parameters = new Properties();
        parameters.setProperty("q",text);
        parameters.setProperty("maxResults", env.getProperty("books.resultsperpage")); // maximo de resultados por vez
        parameters.setProperty("key",
                ApiUrlMaker.googleKeyReservBalancer(
                        env.getProperty("google.keyreserv.selector"),
                        env.getProperty("google.general.key"),
                        env.getProperty("google.general.key.reserv1"),
                        env.getProperty("google.general.key.reserv2"),
                        env.getProperty("google.general.key.reserv3")
                        )

        );
        parameters.setProperty("country", countryCode );
        parameters.setProperty("startIndex", startIndex); // este parametro dever� ser incrementado para a pagina��o
        // ele dever� ser incrementado de forma a ser maior que a quantidade
        // de itens na ultima request, dever� ser incrementado com o valor do parametro
        //"maxResults", no caso de 40 em 40.

        return ApiUrlMaker.makeApiURL(env.getProperty("books.baseUrl"), parameters);

    }

}
