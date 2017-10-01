package ricardombertani.projetos.allinyourhands.apidata.geonames;

public class Country {

	private String name;
	private String code;
	private String geonameId;
	
	public Country() {
		
		this.name = "";
		this.code = "";
		this.geonameId = "";
	}
	
	public Country(String name, String code, String geonameId) {
		
		this.name = name;
		this.code = code;
		this.geonameId = geonameId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getGeonameId() {
		return geonameId;
	}

	public void setGeonameId(String geonameId) {
		this.geonameId = geonameId;
	}
	
	
	
	

}
