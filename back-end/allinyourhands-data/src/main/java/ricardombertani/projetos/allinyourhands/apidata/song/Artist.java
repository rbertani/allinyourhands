package ricardombertani.projetos.allinyourhands.apidata.song;

public class Artist {

	private String name;
    private String previewImage;
	
	public Artist() {
		
		name = "";
		previewImage = "";
	}
	
	public Artist(String name, String previewImage) {
		
		this.name = name;
		this.previewImage = previewImage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreviewImage() {
		return previewImage;
	}

	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}
	
	
	
}
