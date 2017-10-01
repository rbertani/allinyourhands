package ricardombertani.projetos.allinyourhands.apidata.video;

import java.util.ArrayList;
import java.util.List;



public class VideoCollection {

	private List<Video> videos = new ArrayList<Video>(); 
	private String nextPageToken; // token para a próxima pagina
	
	public VideoCollection(){
		
	}
	
		
	public List<Video> getVideos() {
		return videos;
	}



	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}



	public void add(Video video){
		videos.add(video);
	}


	public String getNextPageToken() {
		return nextPageToken;
	}


	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}
	
	
	
}
