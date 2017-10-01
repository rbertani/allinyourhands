package ricardombertani.projetos.allinyourhands.apidata.book;

import java.util.List;

public class VolumeInfo {
                                        
	private String title;
	private String subtitle;                         
	private String authors;
	private int pageCount;
    private String publisher;	
	private String publishedDate;	
	private String description;
	private String categories;
    private ImageLink imageLink;	
	
	    		  
	public VolumeInfo() {
		    
		this.title = "";
		this.subtitle = "";
		this.authors = "";
		this.pageCount = 0;  
		this.publisher = "";
		this.publishedDate = "";
		this.description = "";	
		this.categories = "";
		this.imageLink = null;
		
	}
	      
	public VolumeInfo(String title, String subtitle, String authors, int pageCount, String publisher, String publishedDate, String description,
			 String categories, ImageLink imageLink) {
		
		this.title = title;
		this.subtitle = subtitle;
		this.authors = authors; 
		this.pageCount = pageCount;
		this.publisher = publisher;
		this.publishedDate = publishedDate;
		this.description = description;		
		this.categories = categories;
		this.imageLink = imageLink;
		
	}

	public String getTitle() {
		return title;
	}
  
	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}
  
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}  

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public ImageLink getImageLink() {
		return imageLink;
	}

	public void setImageLink(ImageLink imageLink) {
		this.imageLink = imageLink;
	}

	
	
	
}
