package ricardombertani.projetos.allinyourhands.apidata.places;

import java.util.ArrayList;
import java.util.List;

public class Suggestion {
  
	private String type;
	private List<Place> places = new ArrayList<Place>();
	
	public Suggestion(){
		
	}
		
	public Suggestion(String type, List<Place> places) {
		super();
		this.type = type;
		this.places = places;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	
	public void add(Place place){
		places.add(place);
	}
	
	
	
}
