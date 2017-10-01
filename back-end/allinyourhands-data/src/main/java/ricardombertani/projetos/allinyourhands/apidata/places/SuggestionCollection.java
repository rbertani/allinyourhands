package ricardombertani.projetos.allinyourhands.apidata.places;

import java.util.ArrayList;
import java.util.List;

public class SuggestionCollection {

	private int totalResults;
	private List<Suggestion> suggestions = new ArrayList<Suggestion>();
	
	public SuggestionCollection(){
		
	}
	
	public SuggestionCollection(int totalResults, List<Suggestion> suggestions) {
		super();
		this.totalResults = totalResults;
		this.suggestions = suggestions;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public List<Suggestion> getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}
	
	public void add(Suggestion suggestion){
		suggestions.add(suggestion);
	}
	
	
}
