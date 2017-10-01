package ricardombertani.projetos.allinyourhands.chat.data;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomCollection {
	
	private List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
	
	public ChatRoomCollection(){
		
	}

	
	public List<ChatRoom> getChatRooms() {
		return chatRooms;
	}



	public void setChatRooms(List<ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}



	public void add(ChatRoom chatRoom){
		chatRooms.add(chatRoom);
	}
	
}
