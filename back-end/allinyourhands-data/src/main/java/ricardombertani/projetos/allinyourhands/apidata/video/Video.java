package ricardombertani.projetos.allinyourhands.apidata.video;

public class Video {

	 private String id;
	 private String publishedAt;
	 private String title;
	 private String previewImage;
	 private String channelId;
	 private String description;
	 private String channelTitle;
	 private String directURL;
	 
	 /*Estes dois atributos sã utilizados para obter informações sobre a categoria de videos(preferi implementar
	  * nesta mesma classe ao invés de utilizar outra classe para o mesmo processo)*/
	private String categoryId;
	private String categoryName;
	 
	public Video(){

		id = "";
		publishedAt = "";
		title = "";
		previewImage = "";
		channelId = "";
		description = "";
		channelTitle = "";
		directURL = "";
		categoryId = "";
		categoryName = "";
	}
	

	public Video(String id, String publishedAt, String title, String previewImage, 
			String channelId,
			String description, String channelTitle, String directURL,
			String categoryId, String categoryName) {
		
		this.id = id;
		this.publishedAt = publishedAt;
		this.title = title;
		this.previewImage = previewImage;
		this.channelId = channelId;
		this.description = description;
		this.channelTitle = channelTitle;
		this.directURL = directURL;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPreviewImage() {
		return previewImage;
	}


	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}


	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getDirectURL() {
		return directURL;
	}

	public void setDirectURL(String directURL) {
		this.directURL = directURL;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

   
	 
	
}
