package ricardombertani.projetos.allinyourhands.apidata.places;

public class Tip {
  
	private String text;
	private String canonicalUrl; // fazer replaceAll("\",""); // remover caracteres "\"
	
	public Tip(){
		this.text = "";
		this.canonicalUrl = "";		
	}
	
	public Tip(String text, String canonicalUrl) {	
		this.text = text;
		this.canonicalUrl = canonicalUrl;		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCanonicalUrl() {
		return canonicalUrl;
	}

	public void setCanonicalUrl(String canonicalUrl) {
		this.canonicalUrl = canonicalUrl;
	}

	
}
