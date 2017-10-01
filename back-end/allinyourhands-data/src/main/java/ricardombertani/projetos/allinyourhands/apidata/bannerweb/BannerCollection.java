package ricardombertani.projetos.allinyourhands.apidata.bannerweb;

import java.util.ArrayList;
import java.util.List;

public class BannerCollection {

	List<Banner> banners = null;
	
	public BannerCollection(){
		banners = new ArrayList<Banner>();
	}

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}
	
	public void add(Banner banner){
		banners.add(banner);
	}
}
