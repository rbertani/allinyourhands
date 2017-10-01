package ricardombertani.projetos.allinyourhands.apidata.bannerweb;

public class Banner {

	private String urlImage;
	private String urlClick;
	private String imgsize;
	private String cid;

	public Banner() {
		urlImage = "";
		urlClick = "";
		imgsize = "";
		cid = "";
	}

	public Banner(String urlImage, String urlClick, String imgsize, String cid) {
		this.urlImage = urlImage;
		this.urlClick = urlClick;
		this.imgsize = imgsize;
		this.cid = cid;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getUrlClick() {
		return urlClick;
	}

	public void setUrlClick(String urlClick) {
		this.urlClick = urlClick;
	}

	public String getImgsize() {
		return imgsize;
	}

	public void setImgsize(String imgsize) {
		this.imgsize = imgsize;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	

}
