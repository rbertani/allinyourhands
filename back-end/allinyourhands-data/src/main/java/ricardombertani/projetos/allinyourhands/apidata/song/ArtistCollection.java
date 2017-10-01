package ricardombertani.projetos.allinyourhands.apidata.song;

import java.util.ArrayList;
import java.util.List;

public class ArtistCollection {

	private List<Artist> artists = null;

	public ArtistCollection() {
		
		artists = new ArrayList<Artist>();
	}
	
	public ArtistCollection(List<Artist> artists) {
		
		this.artists = artists;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
	
	public void add(Artist artist){
		artists.add(artist);
	}
	
	
	
}
