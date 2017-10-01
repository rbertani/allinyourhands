package ricardombertani.projetos.allinyourhands.apidata.general;

public class Result {

	private String media_type;
	private String response;
	
	public Result(){
		
	}
	
	public Result(String media_type, String response) {
		
		this.media_type = media_type;
		this.response = response;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
	
}
