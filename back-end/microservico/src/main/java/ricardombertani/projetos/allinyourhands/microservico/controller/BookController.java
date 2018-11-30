package ricardombertani.projetos.allinyourhands.microservico.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ricardombertani.projetos.allinyourhands.microservico.util.ApiUrlMaker;

import java.io.File;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/rest")
public class BookController {

    private static Logger logger = Logger.getLogger(BookController.class.getName());
    public static final String RESOURCES_DIRECTORY_PATH = new File("").getAbsolutePath() + File.separator
            + "/src/main/resources/";

    @RequestMapping(path = "/book", method = RequestMethod.GET)
    @ResponseBody
    public String getBooks() {

        ApiUrlMaker apiUrlMaker = new ApiUrlMaker();
        String requestAPI = apiUrlMaker.makeBooksApiURL("java", 0,"pt_br");

        return requestAPI;

        //apiUrlMaker.makeBooksApiURL("java", startIndex,countryCode)

    }

}
