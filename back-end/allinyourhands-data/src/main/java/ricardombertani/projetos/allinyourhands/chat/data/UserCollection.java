package ricardombertani.projetos.allinyourhands.chat.data;

import java.util.ArrayList;
import java.util.List;

public class UserCollection {

	List<User> users = new ArrayList<User>();
	
	public UserCollection(){
		
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void add(User user){
		users.add(user);
	}
	
	
}
