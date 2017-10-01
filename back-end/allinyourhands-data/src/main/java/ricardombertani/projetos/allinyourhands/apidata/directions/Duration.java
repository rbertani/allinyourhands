package ricardombertani.projetos.allinyourhands.apidata.directions;

public class Duration {
    private String text;
            
    public Duration(){
    	text = "";
    }
    
	public Duration(String text) {
	
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
   
   
}
