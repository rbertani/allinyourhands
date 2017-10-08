package ricardombertani.projetos.allinyourhands.core.util;

import java.util.Comparator;

import ricardombertani.projetos.allinyourhands.apidata.suggested.SuggestedContent;

public class SuggestedContentsComparator  implements Comparator<SuggestedContent>{
 
	public int compare(SuggestedContent o1, SuggestedContent o2) {	
		return o2.getCount() - o1.getCount();
	}
 
	  

}
