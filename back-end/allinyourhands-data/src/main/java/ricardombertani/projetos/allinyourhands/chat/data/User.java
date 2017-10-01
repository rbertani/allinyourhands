package ricardombertani.projetos.allinyourhands.chat.data;

public class User {

	private String id;
	private String name;
	private String regid;
	
	public User() {
		
		id = "";
		name = "";
		regid = "";
	}
	
	public User(String id, String name, String regid) {
		
		this.id = id;
		this.name = name;
		this.regid = regid;
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
	public String getRegid() {
		return regid;
	}
	public void setRegid(String regid) {
		this.regid = regid;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		User user = (User)obj;
		if(  this.id.equals(user.getId())  ) return true;
		else return false;
		
	}
	
	
}
