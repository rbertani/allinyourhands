package ricardombertani.projetos.allinyourhands.apidata.directions;

import java.util.ArrayList;
import java.util.List;

import ricardombertani.projetos.allinyourhands.apidata.directions.Step;

public class Leg {
         
	private List<Step> steps  = new ArrayList<Step>();
	private Duration duration;
	private Distance distance;
	private Location start_location;
	
	public Leg(){
		this.steps = null;
		this.duration = null;
		this.distance = null;
		this.start_location = null;
	}
	
	public Leg(List<Step> steps, Duration duration, Distance distance, Location start_location) {
		
		this.steps = steps;
		this.duration = duration;
		this.distance = distance;
		this.start_location = start_location;
	}
	
	public List<Step> getSteps() {
		return steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
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

	public Location getStart_location() {
		return start_location;
	}

	public void setStart_location(Location start_location) {
		this.start_location = start_location;
	}
	
	 
}
