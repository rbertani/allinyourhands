package ricardombertani.projetos.allinyourhands.apidata.sales;

import java.util.ArrayList;
import java.util.List;
import ricardombertani.projetos.allinyourhands.apidata.sales.Product;

  
public class ProductList { 
             
	private List<Product> products = new ArrayList<Product>();
	
	
	public ProductList(){
		
	}

	
	public List<Product> getProducts() {
		return products;
	}


	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void add(Product product){
		products.add(product);
	}
	
	
	
}
