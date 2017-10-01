package ricardombertani.projetos.allinyourhands.apidata.geonames;

import java.util.ArrayList;
import java.util.List;

public class CountryCollection {

	private List<Country> countries;

	public CountryCollection(){
		countries = new ArrayList<Country>();
	}
	
	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	public void add(Country country){
		countries.add(country);
	}

}
