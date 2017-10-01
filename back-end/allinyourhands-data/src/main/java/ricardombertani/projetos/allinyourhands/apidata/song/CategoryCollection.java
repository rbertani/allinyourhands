package ricardombertani.projetos.allinyourhands.apidata.song;

import java.util.ArrayList;
import java.util.List;

public class CategoryCollection {

	private List<Category> categories = null;
	
	public CategoryCollection(){
		categories = new ArrayList<Category>();
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void add(Category category){
		categories.add(category);
	}
	
}
