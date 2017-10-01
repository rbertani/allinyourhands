package ricardombertani.projetos.allinyourhands.apidata.song;

public class Song {
	private String url; // url para ouvir no groovshark
	private String id;
	private String name;
	private String artistId;
	private String artistName;
	private String albumId;
	private String albumName;
	private String imagePreview;
	private String lyricBody;
	private String lyricCopyright;
	
	// atributo utilizado para obter categorias de musicas (criamos o objeto aqui mesmo para evitar de criar
	// outra classe apenas para armazenar uma informação
	private String categoryName;
	
	public Song(){
		url = "";
		id = "";
		name = "";
		artistId = "";
		artistName = "";
		albumId = "";
		albumName = "";
		imagePreview = "";
		lyricBody = "";
		lyricCopyright = "";
		
		categoryName = "";
	}
	 
	public Song(String url, String id, String name, String artistId,
			String artistName, String albumId, String albumName, String imagePreview, String lyricBody, String lyricCopyright,
			String categoryName) {
		
		this.url = url;
		this.id = id;
		this.name = name;
		this.artistId = artistId;
		this.artistName = artistName;
		this.albumId = albumId;
		this.albumName = albumName;
		this.imagePreview = imagePreview;
		this.lyricBody = lyricBody;
		this.lyricCopyright = lyricCopyright;
		this.categoryName = categoryName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getArtistId() {
		return artistId;
	}
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getImagePreview() {
		return imagePreview;
	}

	public void setImagePreview(String imagePreview) {
		this.imagePreview = imagePreview;
	}
		
	public String getLyricBody() {
		return lyricBody;
	}

	public void setLyricBody(String lyricBody) {
		this.lyricBody = lyricBody;
	}

	public String getLyricCopyright() {
		return lyricCopyright;
	}

	public void setLyricCopyright(String lyricCopyright) {
		this.lyricCopyright = lyricCopyright;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	 
	
	 
}
