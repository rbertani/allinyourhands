package ricardombertani.projetos.allinyourhands.apidata.suggested;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuggestedCollection implements Serializable{

	private List<SuggestedContent> suggestedContents = null;
	
	public SuggestedCollection(){
		suggestedContents = new ArrayList<SuggestedContent>();
	}

    
	public List<SuggestedContent> getSuggestedContents() {
		return suggestedContents;
	}

	public void setSuggestedContents(List<SuggestedContent> suggestedContents) {
		this.suggestedContents = suggestedContents;
	}



	public void add(SuggestedContent suggestedContent){
		suggestedContents.add(suggestedContent);
	}
}
