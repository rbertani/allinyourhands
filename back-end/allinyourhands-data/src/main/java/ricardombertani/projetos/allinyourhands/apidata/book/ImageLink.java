package ricardombertani.projetos.allinyourhands.apidata.book;

public class ImageLink {
      
	private String smallThumbnail;
	                                                
	private String thumbnail;             
	              
	private String medium;          
	
	private String large;
	
	private String extraLarge;

	public ImageLink() {
		              
		this.smallThumbnail = "";
		this.thumbnail = "";
		this.medium = "";
		this.large = "";
		this.extraLarge = "";
	}
	
	  
	public ImageLink(String smallThumbnail, String thumbnail, String medium,
			String large, String extraLarge) {
		
		this.smallThumbnail = smallThumbnail;
		this.thumbnail = thumbnail;
		this.medium = medium;
		this.large = large;
		this.extraLarge = extraLarge;
	}

	public String getSmallThumbnail() {
		return smallThumbnail;
	}

	public void setSmallThumbnail(String smallThumbnail) {
		this.smallThumbnail = smallThumbnail;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
  
	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}

	public String getExtraLarge() {
		return extraLarge;
	}

	public void setExtraLarge(String extraLarge) {
		this.extraLarge = extraLarge;
	}
	
	public String toString(){
		return "smallThumbnail: "+smallThumbnail+"  thumbnail: "+thumbnail+" medium: "+medium+" large: "+large+" extraLarge: "+extraLarge;
  	
	}
	
	
}
