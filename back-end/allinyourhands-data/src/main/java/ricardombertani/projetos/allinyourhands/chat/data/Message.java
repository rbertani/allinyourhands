package ricardombertani.projetos.allinyourhands.chat.data;

public class Message {

	private String text;
	private String data;
	private String userId;
	private String user;
	private String regid;
	
	public Message(){
		text = "";
		data = "";
		userId = "";
		user = "";
		regid = "";
	}
	
	public Message(String text, String data,String userId, String user, String regid) {
	
		this.text = text;
		this.data = data;
		this.userId = userId;
		this.user = user;
		this.regid = regid;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
		
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRegid() {
		return regid;
	}

	public void setRegid(String regid) {
		this.regid = regid;
	}
	
	
	
	
}
