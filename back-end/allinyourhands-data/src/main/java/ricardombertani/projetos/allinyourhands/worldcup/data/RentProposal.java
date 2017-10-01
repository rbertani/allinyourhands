package ricardombertani.projetos.allinyourhands.worldcup.data;

public class RentProposal {

	private String type;
	private String neighborhood;
	private String contactName;
	private String contactEmail;
	private String contactCelPhone;
	private int days;
	private String price;
	private int roomsNumber;
	private String description;
	private String id;
	private String deviceId;
	
	public RentProposal(){
		type = "";
		neighborhood = "";
		contactName = "";
		contactEmail = "";
		contactCelPhone = "";
		days = 0;
		price = "0";
		roomsNumber = 0;
		description = "";
		id = "";
		deviceId = "";
	}
	
	public RentProposal(String type,String neighborhood, String contactName, String contactEmail,
			String contactCelPhone, int days, String price, int roomsNumber,
			String description, String id, String deviceId) {		
		this.type = type;
		this.neighborhood = neighborhood;
		this.contactName = contactName;
		this.contactEmail = contactEmail;
		this.contactCelPhone = contactCelPhone;
		this.days = days;
		this.price = price;
		this.roomsNumber = roomsNumber;
		this.description = description;
		this.id = id;
		this.deviceId = deviceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactCelPhone() {
		return contactCelPhone;
	}
	public void setContactCelPhone(String contactCelPhone) {
		this.contactCelPhone = contactCelPhone;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getRoomsNumber() {
		return roomsNumber;
	}
	public void setRoomsNumber(int roomsNumber) {
		this.roomsNumber = roomsNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	
	
	

}
