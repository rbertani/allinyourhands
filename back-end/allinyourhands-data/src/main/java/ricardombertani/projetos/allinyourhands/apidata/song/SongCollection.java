package ricardombertani.projetos.allinyourhands.apidata.song;

import java.util.ArrayList;
import java.util.List;

public class SongCollection {

	private List<Song> songs = null;
	
	public SongCollection(){
		songs = new ArrayList<Song>();
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	public void add(Song song){
		songs.add(song);
	}
	
}
