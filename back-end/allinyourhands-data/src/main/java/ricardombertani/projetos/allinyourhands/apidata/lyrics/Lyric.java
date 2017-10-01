package ricardombertani.projetos.allinyourhands.apidata.lyrics;

public class Lyric {

	private String track_id;
	private String track_name;
	private String album_name;
	private String artist_name;
	private String lyricBody;
	private String copyright;
	
	public Lyric(){
		this.track_id = "";
		this.track_name = "";
		this.album_name = "";
		this.artist_name = "";		
		this.lyricBody = "";
		this.copyright = "";
	}
	
	public Lyric(String track_id, String track_name, String album_name,
			String artist_name, String lyricBody, String copyright) {
		
		this.track_id = track_id;
		this.track_name = track_name;
		this.album_name = album_name;
		this.artist_name = artist_name;		
		this.lyricBody = lyricBody;
		this.copyright = copyright;
	}
	public String getTrack_id() {
		return track_id;
	}
	public void setTrack_id(String track_id) {
		this.track_id = track_id;
	}
	public String getTrack_name() {
		return track_name;
	}
	public void setTrack_name(String track_name) {
		this.track_name = track_name;
	}
	public String getAlbum_name() {
		return album_name;
	}
	public void setAlbum_name(String album_name) {
		this.album_name = album_name;
	}
	public String getArtist_name() {
		return artist_name;
	}
	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}
	public String getLyricBody() {
		return lyricBody;
	}
	public void setLyricBody(String lyricBody) {
		this.lyricBody = lyricBody;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	
	
	
}
