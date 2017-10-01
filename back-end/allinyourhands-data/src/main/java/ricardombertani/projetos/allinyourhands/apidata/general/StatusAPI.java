package ricardombertani.projetos.allinyourhands.apidata.general;

public class StatusAPI {
	
	private int audio;
	private int video;
	private int lyric;
	private int book;
	private int placeSug;
	private int placeNear;
	private int weather;
	private int directions;
	private int goods;
	private int chat;
	private String streamingSessionID;
	private String billingKey;
	private String gcmProjectNumber;
	
	public StatusAPI(){
		
	}
	
	public StatusAPI(int audio, int video, int lyric, int book, int placeSug,
			int placeNear, int weather, int directions, int goods, int chat, String streamingSessionID, String billingKey, String gcmProjectNumber) {
		
		this.audio = audio;
		this.video = video;
		this.lyric = lyric;
		this.book = book;
		this.placeSug = placeSug;
		this.placeNear = placeNear;
		this.weather = weather;
		this.directions = directions;
		this.goods = goods;
		this.chat = chat;
		this.streamingSessionID = streamingSessionID;
		this.billingKey = billingKey;
		this.gcmProjectNumber = gcmProjectNumber;
	}
	public int getAudio() {
		return audio;
	}
	public void setAudio(int audio) {
		this.audio = audio;
	}
	public int getVideo() {
		return video;
	}
	public void setVideo(int video) {
		this.video = video;
	}
	public int getLyric() {
		return lyric;
	}
	public void setLyric(int lyric) {
		this.lyric = lyric;
	}
	public int getBook() {
		return book;
	}
	public void setBook(int book) {
		this.book = book;
	}
	public int getPlaceSug() {
		return placeSug;
	}
	public void setPlaceSug(int placeSug) {
		this.placeSug = placeSug;
	}
	public int getPlaceNear() {
		return placeNear;
	}
	public void setPlaceNear(int placeNear) {
		this.placeNear = placeNear;
	}
	public int getWeather() {
		return weather;
	}
	public void setWeather(int weather) {
		this.weather = weather;
	}
	public int getDirections() {
		return directions;
	}
	public void setDirections(int directions) {
		this.directions = directions;
	}
	public int getGoods() {
		return goods;
	}
	public void setGoods(int goods) {
		this.goods = goods;
	}
	public int getChat() {
		return chat;
	}
	public void setChat(int chat) {
		this.chat = chat;
	}

	public String getStreamingSessionID() {
		return streamingSessionID;
	}

	public void setStreamingSessionID(String streamingSessionID) {
		this.streamingSessionID = streamingSessionID;
	}

	public String getBillingKey() {
		return billingKey;
	}

	public void setBillingKey(String billingKey) {
		this.billingKey = billingKey;
	}

	public String getGcmProjectNumber() {
		return gcmProjectNumber;
	}

	public void setGcmProjectNumber(String gcmProjectNumber) {
		this.gcmProjectNumber = gcmProjectNumber;
	}
	
		
	
			
}
