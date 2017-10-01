package ricardombertani.projetos.allinyourhands.apidata.directions;

public class Step {
   
	 private Duration duration;
	 private String html_instructions;
	 private Distance distance;
	 private Location start_location;
	 
	 public Step(){
		 this.duration = null;
		 this.html_instructions = "";
		 this.distance = null;
		 this.start_location = null;
	 }
	 
	 public Step(Duration duration, String html_instructions, Distance distance, Location start_location) {
		
		this.duration = duration;
		this.html_instructions = html_instructions;
		this.distance = distance;
		this.start_location = start_location;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getHtml_instructions() {
		return html_instructions;
	}

	public void setHtml_instructions(String html_instructions) {
		this.html_instructions = html_instructions;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	public Location getStart_location() {
		return start_location;
	}

	public void setStart_location(Location start_location) {
		this.start_location = start_location;
	}
	
	
}
