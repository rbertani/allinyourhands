package ricardombertani.projetos.allinyourhands.apidata.directions;

public class Route {

	private String summary;
	       
	private Leg leg;
     
	public Route(){
		this.summary = "";
		this.leg = null;
	}
	  
	public Route(String summary, Leg leg) {
		
		this.summary = summary;
		this.leg = leg;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Leg getLeg() {
		return leg;
	}

	public void setLeg(Leg leg) {
		this.leg = leg;
	}
	 
	 
}
