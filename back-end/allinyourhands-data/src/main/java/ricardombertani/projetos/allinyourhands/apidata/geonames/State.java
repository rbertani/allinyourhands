package ricardombertani.projetos.allinyourhands.apidata.geonames;

public class State {

	private String name;
	private String geonameId;

	public State() {		
		name = "";
		geonameId = "";
	}
	
	public State(String name, String geonameId) {		
		this.name = name;
		this.geonameId = geonameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeonameId() {
		return geonameId;
	}

	public void setGeonameId(String geonameId) {
		this.geonameId = geonameId;
	}

}
