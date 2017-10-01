package ricardombertani.projetos.allinyourhands.chat.data;

import java.util.ArrayList;
import java.util.List;

public class MessageCollection {

	private List<Message> messages = new ArrayList<Message>();
	
	public MessageCollection(){
		
	}
		
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void add(Message message){
		messages.add(message);
	}
	
}
