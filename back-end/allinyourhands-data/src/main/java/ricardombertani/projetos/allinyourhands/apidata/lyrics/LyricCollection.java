package ricardombertani.projetos.allinyourhands.apidata.lyrics;

import java.util.ArrayList;
import java.util.List;

public class LyricCollection {

	private List<Lyric> lyrics = new ArrayList<Lyric>();
	
	public LyricCollection(){
		
	}
	
	public void add(Lyric lyric){
		lyrics.add(lyric);
	}

	public List<Lyric> getLyrics() {
		return lyrics;
	}

	public void setLyrics(List<Lyric> lyrics) {
		this.lyrics = lyrics;
	}
	
	
	
	
}
