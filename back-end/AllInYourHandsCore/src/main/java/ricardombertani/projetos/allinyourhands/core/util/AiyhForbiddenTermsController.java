package ricardombertani.projetos.allinyourhands.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class AiyhForbiddenTermsController {

	// variavel para os logs
	private static Logger log = Logger.getLogger(AiyhForbiddenTermsController.class.getName());
		  
	public static boolean containsImproperTerms(String phrase) {

		ScatteringSet scatteringSet = new ScatteringSet();
		loadingForbiddenTermsDataSet(scatteringSet);
		
		/*for(String s : scatteringSet.getAllWords() ){
			System.out.println(s + " "+scatteringSet.calculateTableIndex(s));
		}*/
				
		//scatteringSet.printTable();
		System.out.println("ScatteringSet is prepared!!");
		
		String words[] = phrase.split(" ");
		
		// busca por termos individuais da frase
		for (String word : words) {
			
			if (scatteringSet.contains(word)) {
				return true;
			}
			
			if(scatteringSet.containsSubstring(word)){
				return true;
			}
			
		}
		
		
		
		return false;

	}
	
	private static void loadingForbiddenTermsDataSet(ScatteringSet scatteringSet){
		File forbiddenTermsFile = new File(	System.getProperty(AllInYourHandsConstants.PROPERTY_AIYH_FORBIDDENTERMS_LIST_FILE_PATH));

		String forbiddenText = "";
		
		try{
			BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(forbiddenTermsFile), "UTF8"));
			// loop que le e imprime todas as linhas do arquivo
			forbiddenText = myBuffer.readLine();
							
			String aux = ""+forbiddenText;
			while (forbiddenText != null) {				
				forbiddenText = myBuffer.readLine();
				if(forbiddenText != null)
					aux += forbiddenText;
			}
			
			aux = aux.replaceAll(" ","");
			String[] forbiddenTermsVector = aux.split(",");
			if(forbiddenTermsVector != null){
				if(forbiddenTermsVector.length > 0){
					for(int i =0; i<forbiddenTermsVector.length; i++){
						scatteringSet.add(forbiddenTermsVector[i]);
					}
					log.debug("\n--->Forbidden List Loaded with SUCCESS!!!");
				}
			}
		}catch(IOException e){
			log.debug("\n--->Error to read file in loadingForbiddenTermsDataSet: "+e.getMessage());
			e.printStackTrace();
			
		}catch(Exception e){
			log.debug("\n--->Exception - Error to read file in loadingForbiddenTermsDataSet: "+e.getMessage());
			e.printStackTrace();
			
		}	
		
	}
	

	
}