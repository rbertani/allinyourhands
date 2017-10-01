package ricardombertani.projetos.allinyourhands.apidata.sales;

public class Product {
	
	 private String title;
	 private String subtitle;
	 private String priceRange; // faixa de preço no formato "50-100"
	 private int available_quantity ; // deve ser > 0
	 private int sold_quantity; // quantidade vendida
	 private String stop_time; // disponivel ate esta data
	 private String permalink; // -link para a loja
	 private String seller_address_country_name; 
	 private String seller_address_state_name;
	 private String seller_address_city_name; 
	 
	 public Product(){
		    this.title = "";
			this.subtitle = "";
			this.priceRange = "";
			this.available_quantity = 0;
			this.sold_quantity = 0;
			this.stop_time = "";
			this.permalink = "";
			this.seller_address_country_name = "";
			this.seller_address_state_name = "";
			this.seller_address_city_name = "";
	 }
	 	 
	public Product(String title, String subtitle, String priceRange,
			int available_quantity, int sold_quantity, String stop_time,
			String permalink, String seller_address_country_name,
			String seller_address_state_name, String seller_address_city_name) {
	
		this.title = title;
		this.subtitle = subtitle;
		this.priceRange = priceRange;
		this.available_quantity = available_quantity;
		this.sold_quantity = sold_quantity;
		this.stop_time = stop_time;
		this.permalink = permalink;
		this.seller_address_country_name = seller_address_country_name;
		this.seller_address_state_name = seller_address_state_name;
		this.seller_address_city_name = seller_address_city_name;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public int getAvailable_quantity() {
		return available_quantity;
	}

	public void setAvailable_quantity(int available_quantity) {
		this.available_quantity = available_quantity;
	}

	public int getSold_quantity() {
		return sold_quantity;
	}

	public void setSold_quantity(int sold_quantity) {
		this.sold_quantity = sold_quantity;
	}

	public String getStop_time() {
		return stop_time;
	}

	public void setStop_time(String stop_time) {
		this.stop_time = stop_time;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
              
	public String getSeller_address_country_name() {
		return seller_address_country_name;
	}

	public void setSeller_address_country_name(String seller_address_country_name) {
		this.seller_address_country_name = seller_address_country_name;
	}

	public String getSeller_address_state_name() {
		return seller_address_state_name;
	}

	public void setSeller_address_state_name(String seller_address_state_name) {
		this.seller_address_state_name = seller_address_state_name;
	}

	public String getSeller_address_city_name() {
		return seller_address_city_name;
	}

	public void setSeller_address_city_name(String seller_address_city_name) {
		this.seller_address_city_name = seller_address_city_name;
	}
	
	public String toString(){
		return   title +  "  "+subtitle+"  "+priceRange+"  "+available_quantity+"  "+sold_quantity+"  "+stop_time+"  "+permalink+"  "+seller_address_country_name+"  "+
		seller_address_state_name+"  "+seller_address_city_name;
	}
	 
	 
}
