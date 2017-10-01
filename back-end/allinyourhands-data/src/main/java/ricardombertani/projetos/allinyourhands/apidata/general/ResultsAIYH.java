package ricardombertani.projetos.allinyourhands.apidata.general;

import java.util.ArrayList;
import java.util.List;


public class ResultsAIYH {

	private List<Result> results = new ArrayList<Result>();
	
	public ResultsAIYH(){
		
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	public void add(Result result){
		results.add(result);
	}
}
