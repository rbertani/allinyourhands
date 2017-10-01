package ricardombertani.projetos.allinyourhands.apidata.places;

import java.util.ArrayList;
import java.util.List;

public class Place {
	
    private String id ; // venue id no foursquare
    private String name;
    private String phone; 
    private String  address;
    private String state;
    private String country;
    private String latLong;
    private String  distance;
    private String  postalCode;     
    private String  categoryName;
    private String  urlSite;    
    private int checkinsCount ; //qtdade de chekins
    private int usersCount; //qtdade de users         
    private String statusText; // se esta aberto ou não agora
    private boolean isOpen; // se esta aberto ou não agora
    // photos - obj - explorar mais locais para saber oq obter aqui
    private int count; // pessoas que estão agora no local
    private String imagePreviewURL;
    private List<Tip> tips = null;
  
    public  Place(){
    	 id = "";
    	 name = "";
    	 phone = ""; 
    	 address = "";
    	 state = "";
    	 country = "";
    	 latLong = "";
    	 distance = "";
    	 postalCode = "";     
    	 categoryName = "";
    	 urlSite = "";    	 
    	 checkinsCount  = 0; 
    	 usersCount = 0;    
    	 statusText = ""; 
    	 isOpen = false;     	
    	 count = 0; 
    	 imagePreviewURL = "";
    	 tips = new ArrayList<Tip>();
    }

    
    
	public Place(String id, String name, String phone, String address,
			String state, String country, String latLong,
			String distance, String postalCode, String categoryName,
			String urlSite, int checkinsCount,
			int usersCount, String statusText, boolean isOpen, int count, String imagePreviewURL,
			List<Tip> tips) {
		
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.state = state;
		this.country = country;
		this.latLong = latLong;
		this.distance = distance;
		this.postalCode = postalCode;
		this.categoryName = categoryName;
		this.urlSite = urlSite;		
		this.checkinsCount = checkinsCount;
		this.usersCount = usersCount;
		this.statusText = statusText;
		this.isOpen = isOpen;
		this.count = count;
		this.imagePreviewURL = imagePreviewURL;
		this.tips = tips;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getLatLong() {
		return latLong;
	}

	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}



	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}
	
	public int getCheckinsCount() {
		return checkinsCount;
	}

	public void setCheckinsCount(int checkinsCount) {
		this.checkinsCount = checkinsCount;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Tip> getTips() {
		return tips;
	}
		
	public String getImagePreviewURL() {
		return imagePreviewURL;
	}

	public void setImagePreviewURL(String imagePreviewURL) {
		this.imagePreviewURL = imagePreviewURL;
	}



	public void setTips(List<Tip> tips) {
		this.tips = tips;
	}
    
	public void add(Tip tip){
		tips.add(tip);
	}
    
	
}
