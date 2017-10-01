package ricardombertani.projetos.allinyourhands.apidata.book;   

import java.util.ArrayList;
import java.util.List;
import ricardombertani.projetos.allinyourhands.apidata.book.Book;
      
public class BooksVolumeCollection {
                                   
	                 
	private List<Book> books = new ArrayList<Book>();
	private int totalItems;

	public BooksVolumeCollection(){
		
	}
	      
	public void add(Book item) {
		books.add(item);
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
	
	    
	
	
}
