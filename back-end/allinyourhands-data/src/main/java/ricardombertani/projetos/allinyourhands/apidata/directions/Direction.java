package ricardombertani.projetos.allinyourhands.apidata.directions;

import java.util.ArrayList;
import java.util.List;
        
public class Direction {
    
	 private String status;
	 private Route route;
	 
	 private List<Step> steps = null;
	 private String summary = "";
	 private Duration duration = null;
	 private Distance distance = null;
	 private String staticMapURL = "";
	 private String destinyStreetViewImageURL = "";
	
	 	 
	 public Direction(){
		    this.status = "";
			this.route =   null;
			this.steps =  null;
			this.summary =  "";
			this.duration =  null;
			this.distance =  null;
			this.staticMapURL = "";
			this.destinyStreetViewImageURL = "";
	 }
	 
	 public Direction(String status, Route route, List<Step> steps,
			String summary, Duration duration, Distance distance, String staticMapURL, String destinyStreetViewImageURL) {
		
		this.status = status;
		this.route = route;
		this.steps = steps;
		this.summary = summary;
		this.duration = duration;
		this.distance = distance;
		this.staticMapURL = staticMapURL;
		this.destinyStreetViewImageURL = destinyStreetViewImageURL;
		
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public Distance getDistance() {
		return distance;
	}
	public void setDistance(Distance distance) {
		this.distance = distance;
	}
	public String getStaticMapURL() {
		return staticMapURL;
	}
	public void setStaticMapURL(String staticMapURL) {
		this.staticMapURL = staticMapURL;
	}

	public String getDestinyStreetViewImageURL() {
		return destinyStreetViewImageURL;
	}

	public void setDestinyStreetViewImageURL(String destinyStreetViewImageURL) {
		this.destinyStreetViewImageURL = destinyStreetViewImageURL;
	}
			
	 
}
