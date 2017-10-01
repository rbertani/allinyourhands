package ricardombertani.projetos.allinyourhands.chat.data;


public class ChatRoom {

	private String id;
	private String name;	
	private int countUsers;
	private int countMessages;
	private int regionalizedChatRoom;
	private String regionID;
	private String regionName;
	
	public ChatRoom(){
		id = "";
		name = "";
		countUsers = 0;
		countMessages = 0;
		regionalizedChatRoom = 0;
		regionID = "";
		regionName = "";
	}
		

	public ChatRoom(String id, String name, int countUsers, int countMessages, int regionalizedChatRoom,
			String regionID, String regionName) {
		
		this.id = id;
		this.name = name;
		this.countUsers = countUsers;
		this.countMessages = countMessages;
		this.regionalizedChatRoom = regionalizedChatRoom;
		this.regionID = regionID;
		this.regionName = regionName;
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
	
	
	public int getCountUsers() {
		return countUsers;
	}

	public void setCountUsers(int countUsers) {
		this.countUsers = countUsers;
	}


	public int getCountMessages() {
		return countMessages;
	}


	public void setCountMessages(int countMessages) {
		this.countMessages = countMessages;
	}


	public int getRegionalizedChatRoom() {
		return regionalizedChatRoom;
	}

	public void setRegionalizedChatRoom(int regionalizedChatRoom) {
		this.regionalizedChatRoom = regionalizedChatRoom;
	}

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
		
	
	
	
	
}
