package ricardombertani.projetos.allinyourhands.apidata.geonames;

import java.util.ArrayList;
import java.util.List;

public class StateCollection {

	private List<State> states;

	public StateCollection(){
		states = new ArrayList<State>();
	}
	
	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
	
	public void add(State state){
		states.add(state);
	}
}
