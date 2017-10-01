package ricardombertani.projetos.allinyourhands.apidata.book;

import java.util.List;

public class Book implements Comparable<Book>{
                     
	private String id;
	                                                    
	private String etag;
	  
	private VolumeInfo volumeInfo;
	
	private String webReaderLink;
	
	private boolean isReaderLinkHtmlFormat;
	
	private String pdfDownloadLink;
	
	private boolean isPdfFile;
	
	private boolean isFull;
	
	private String accessViewStatus;
		
	//private String viewability;
			
	
	public Book() {
		
		id = "";
		etag = "";
		volumeInfo = null;
		webReaderLink = "";
		isReaderLinkHtmlFormat = false;
		pdfDownloadLink = "";
		accessViewStatus = "";
		//viewability = "";
		isPdfFile = false;
		isFull = false;
	}  
	
	public Book(String id, String etag, VolumeInfo volumeInfo,
		 List<ImageLink> imageLinks,
			String webReaderLink, boolean isReaderLinkHtmlFormat, String pdfDownloadLink, String accessViewStatus, boolean isPdfFile, boolean isFull/*,String viewability*/) {
		
		this.id = id;
		this.etag = etag;
		this.volumeInfo = volumeInfo;
		this.webReaderLink = webReaderLink;
		this.isReaderLinkHtmlFormat = isReaderLinkHtmlFormat;
		this.pdfDownloadLink = pdfDownloadLink;
		this.accessViewStatus = accessViewStatus;
		//this.viewability = viewability;
		this.isPdfFile = isPdfFile;
		this.isFull = isFull;
		
	}

	public String getId() {
		return id;
	}
  
	public void setId(String id) {
		this.id = id;
	}
  
	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}
  
	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}
  
	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}
	
	public String getWebReaderLink() {
		return webReaderLink;
	}
  
	public void setWebReaderLink(String webReaderLink) {
		this.webReaderLink = webReaderLink;
	}
		
	public boolean isReaderLinkHtmlFormat() {
		return isReaderLinkHtmlFormat;
	}

	public void setReaderLinkHtmlFormat(boolean isReaderLinkHtmlFormat) {
		this.isReaderLinkHtmlFormat = isReaderLinkHtmlFormat;
	}

	public String getPdfDownloadLink() {
		return pdfDownloadLink;
	}

	public void setPdfDownloadLink(String pdfDownloadLink) {
		this.pdfDownloadLink = pdfDownloadLink;
	}

	public String getAccessViewStatus() {
		return accessViewStatus;
	}

	public void setAccessViewStatus(String accessViewStatus) {
		this.accessViewStatus = accessViewStatus;
	}
	
	
	/*public String getViewability() {
		return viewability;
	}

	public void setViewability(String viewability) {
		this.viewability = viewability;
	}*/
	
	public String toString(){
		return "ID: "+id+" ETAG: "+etag+" Title: "+volumeInfo.getTitle()+" SubTitle: "+volumeInfo.getSubtitle()+" Authors: "+volumeInfo.getAuthors()+" publisher: "+volumeInfo.getPublisher()+
               "pageCount: "+volumeInfo.getPageCount()+" publishedDate: "+volumeInfo.getPublishedDate()+" description: "+volumeInfo.getDescription()+" categories: "+volumeInfo.getCategories()+"  imageLinks: "+volumeInfo.getImageLink().toString()+"  accessViewStatus: "+accessViewStatus+" isFull: "+isFull+" isPdfFile: "+isPdfFile+/*"  viewability: "+viewability+*/"  readerLink: "+webReaderLink+" downloadFileLink: "+pdfDownloadLink;
	
	
	}

	public boolean isPdfFile() {
		return isPdfFile;
	}

	public void setPdfFile(boolean isPdfFile) {
		this.isPdfFile = isPdfFile;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int compareTo(Book book) {
		
		return volumeInfo.getTitle().compareTo(book.getVolumeInfo().getTitle());
	}
	
	
}