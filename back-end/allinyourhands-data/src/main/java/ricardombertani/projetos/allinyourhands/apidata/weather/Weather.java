package ricardombertani.projetos.allinyourhands.apidata.weather;


public class Weather {

	
	private String temperature; // em kelvin
	
	private String temp_min;
	
	private String temp_max;

	private String humidity;
	
	private String pressure;
		
	private String windSpeed;
	
	private String weatherDescription;//descrição do tempo
	
	private String staticMapImageURL;
	
	private String forecastText;
	
	public Weather(){
		temperature = "";
		humidity = "";
		pressure = "";		
		weatherDescription = "";
		temp_min = "";
		temp_max = "";
		windSpeed = "";
		staticMapImageURL = "";
		forecastText = "";
	}

	

	public Weather(String temperature, String temp_min, String temp_max,
			String humidity, String pressure, String lastupdate,
			String windSpeed, String weatherDescription, String staticMapImageURL, String forecastText) {
		
		this.temperature = temperature;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
		this.humidity = humidity;
		this.pressure = pressure;		
		this.windSpeed = windSpeed;
		this.weatherDescription = weatherDescription;
		this.staticMapImageURL = staticMapImageURL;
		this.forecastText = forecastText;
	}



	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	public String getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(String temp_min) {
		this.temp_min = temp_min;
	}

	public String getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(String temp_max) {
		this.temp_max = temp_max;
	}



	public String getWindSpeed() {
		return windSpeed;
	}



	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}



	public String getStaticMapImageURL() {
		return staticMapImageURL;
	}



	public void setStaticMapImageURL(String staticMapImageURL) {
		this.staticMapImageURL = staticMapImageURL;
	}



	public String getForecastText() {
		return forecastText;
	}



	public void setForecastText(String forecastText) {
		this.forecastText = forecastText;
	}
	
			

}
