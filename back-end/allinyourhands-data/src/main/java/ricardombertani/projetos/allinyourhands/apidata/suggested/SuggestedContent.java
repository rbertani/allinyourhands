package ricardombertani.projetos.allinyourhands.apidata.suggested;

import java.io.Serializable;

public class SuggestedContent implements Serializable{

	private String id;
	private String image;
	private String name;
	private String type;
	private String artist;
	private int count;
	private String extraInformation;
	private String date;
	private int accessedContent; // indica que conteudo foi de fato acessado (1 para sim e 0 para nao)
	
	public SuggestedContent() {
		
		this.id = "";
		this.name = "";
		this.image = "";
		this.type = "";
		this.artist = "";
		this.count = 0;
		this.extraInformation = "";
		this.date = "";
		this.accessedContent = 0;
	}
	
	public SuggestedContent(String id,String image, String name, String type, String artist, int count, String extraInformation,String date, int accessedContent) {
		
		this.id = id;
		this.name = name;
		this.image = image;
		this.type = type;
		this.artist = artist;
		this.count = count;
		this.extraInformation = extraInformation;
		this.date = date;
		this.accessedContent = accessedContent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getExtraInformation() {
		return extraInformation;
	}

	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAccessedContent() {
		return accessedContent;
	}

	public void setAccessedContent(int accessedContent) {
		this.accessedContent = accessedContent;
	}
	
	
	
}
