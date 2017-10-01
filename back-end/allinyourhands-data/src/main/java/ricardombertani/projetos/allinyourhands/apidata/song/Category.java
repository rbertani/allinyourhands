package ricardombertani.projetos.allinyourhands.apidata.song;

public class Category {

	private String id;
	
	private String name;

	public Category() {
		id = "";
		name = "";
	}
	
	public Category(String name,String id) {
		
		this.id = id;
		this.name = name;
	}
	
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
