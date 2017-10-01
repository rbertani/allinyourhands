package ricardombertani.projetos.allinyourhands.admin.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.annotation.PostConstruct;

import ricardombertani.projetos.allinyourhands.admin.locator.ServiceLocator;

@ManagedBean
public class AdminController implements Serializable {

	private Date initialDateQuery;
	private Date finalDateQuery;
	private Date initialDateAccess;
	private Date finalDateAccess;
	private Date initialDateAndroidAccess;
	private Date finalDateAndroidAccess;
	private String categoryNameQuery = "musics, music_categories, videos, video_categories, places, books, weather";
	private String categoryNameAccess = "MOBILE ACCESS, IPHONE ACCESS, WINDOWSPHONE ACCESS, BROWSER ACCESS";
	private int resultsCountQueries = 0;
	private int resultsCountAccess = 0;
	private int resultsCountAndroidAccess = 0;
	// variavel para os logs
	private static Logger log = Logger.getLogger(AdminController.class.getName());
	private SimpleDateFormat sdf;
	private SimpleDateFormat sdf2;
	private String systemVersionRadioValue;

	private int daysNumber;
	private int max;
	
	private int maxSearch;

	private BarChartModel animatedSearchModel;
	private BarChartModel animatedModel1;

	@PostConstruct
	public void init() {
		sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
		sdf2 = new SimpleDateFormat("dd-MM");
		daysNumber = Integer.parseInt(System.getProperty("ricardombertani.projetos.allinyourhands.admin.day.numbers"));
		max = 0;
		maxSearch = 0;

		systemVersionRadioValue = "";		
		createAnimatedModels();
		createAnimatedSearchModels();
	}

	public BarChartModel getAnimatedModel1() {
		return animatedModel1;
	}

	public void initialDateQuerySelEvent(SelectEvent event) {
		setInitialDateQuery((Date) event.getObject());
	}

	public BarChartModel getAnimatedSearchModel() {
		return animatedSearchModel;
	}

	public void setAnimatedSearchModel(BarChartModel animatedSearchModel) {
		this.animatedSearchModel = animatedSearchModel;
	}

	public void finalDateQuerySelEvent(SelectEvent event) {
		setFinalDateQuery((Date) event.getObject());

	}

	public void initialDateAccessSelEvent(SelectEvent event) {
		setInitialDateAccess((Date) event.getObject());
	}

	public void finalDateAccessSelEvent(SelectEvent event) {
		setFinalDateAccess((Date) event.getObject());
	}

	public void initialDateAndroidAccessSelEvent(SelectEvent event) {
		setInitialDateAndroidAccess((Date) event.getObject());
	}

	public void finalDateAndroidAccessSelEvent(SelectEvent event) {
		setFinalDateAndroidAccess((Date) event.getObject());
	}

	public Date getInitialDateAndroidAccess() {
		return initialDateAndroidAccess;
	}

	public void setInitialDateAndroidAccess(Date initialDateAndroidAccess) {
		this.initialDateAndroidAccess = initialDateAndroidAccess;
	}

	public Date getFinalDateAndroidAccess() {
		return finalDateAndroidAccess;
	}

	public void setFinalDateAndroidAccess(Date finalDateAndroidAccess) {
		this.finalDateAndroidAccess = finalDateAndroidAccess;
	}

	public int getDaysNumber() {
		return daysNumber;
	}

	public void setDaysNumber(int daysNumber) {
		this.daysNumber = daysNumber;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setAnimatedModel1(BarChartModel animatedModel1) {
		this.animatedModel1 = animatedModel1;
	}

	public void click() {
		RequestContext requestContext = RequestContext.getCurrentInstance();

		requestContext.update("form:display");
		requestContext.execute("PF('dlg').show()");
	}

	private void createAnimatedSearchModels() {
		animatedSearchModel = initBarSearchModel();
		animatedSearchModel.setTitle("Buscas ao Sistema");
		animatedSearchModel.setAnimate(true);
		animatedSearchModel.setLegendPosition("ne");
		Axis yAxis = animatedSearchModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (maxSearch > 1) {
			yAxis.setMax(maxSearch + (maxSearch / 2));
		} else {
			yAxis.setMax(0);
		}
	}

	private void createAnimatedModels() {
		animatedModel1 = initBarModel();
		animatedModel1.setTitle("Acessos ao Sistema");
		animatedModel1.setAnimate(true);
		animatedModel1.setLegendPosition("ne");
		Axis yAxis = animatedModel1.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (max > 1) {
			yAxis.setMax(max + (max / 2));
		} else {
			yAxis.setMax(0);
		}

	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries series1 = new ChartSeries();
		ChartSeries series2 = new ChartSeries();
		ChartSeries series3 = new ChartSeries();

		series1.setLabel("Web");
		series2.setLabel("iOS");
		series3.setLabel("Android");

		max = 0;

		Calendar initialCalendar = Calendar.getInstance();
		initialCalendar.set(Calendar.HOUR_OF_DAY, 0);
		initialCalendar.set(Calendar.MINUTE, 0);
		initialCalendar.set(Calendar.SECOND, 0);
		Calendar finalCalendar = Calendar.getInstance();
		finalCalendar.set(Calendar.HOUR_OF_DAY, 23);
		finalCalendar.set(Calendar.MINUTE, 59);
		finalCalendar.set(Calendar.SECOND, 59);

		initialCalendar.set(Calendar.DAY_OF_MONTH,
				initialCalendar.get(Calendar.DAY_OF_MONTH) - (daysNumber - 1));
		finalCalendar.set(Calendar.DAY_OF_MONTH,
				finalCalendar.get(Calendar.DAY_OF_MONTH) - (daysNumber - 1));
		series1.set(
				sdf2.format(initialCalendar.getTime()),
				searchAccessAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "BROWSER ACCESS"));
		series2.set(
				sdf2.format(initialCalendar.getTime()),
				searchAccessAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "IPHONE ACCESS"));
		series3.set(
				sdf2.format(initialCalendar.getTime()),
				searchAccessAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "ANDROID ACCESS"));

		for (int i = 0; i < (daysNumber - 1); i++) {
			initialCalendar.set(Calendar.DAY_OF_MONTH,
					initialCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			finalCalendar.set(Calendar.DAY_OF_MONTH,
					finalCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			int countWeb = searchAccessAction(initialCalendar.getTime(),
					finalCalendar.getTime(), "BROWSER ACCESS");
			int countIOS = searchAccessAction(initialCalendar.getTime(),
					finalCalendar.getTime(), "IPHONE ACCESS");
			int countAndroid = searchAccessAction(initialCalendar.getTime(),
					finalCalendar.getTime(), "ANDROID ACCESS");

			if (countWeb > max) {
				max = countWeb;
			}
			if (countIOS > max) {
				max = countIOS;
			}
			if (countAndroid > max) {
				max = countAndroid;
			}
			series1.set(sdf2.format(initialCalendar.getTime()), countWeb);
			series2.set(sdf2.format(initialCalendar.getTime()), countIOS);
			series3.set(sdf2.format(initialCalendar.getTime()), countAndroid);

		}

		model.addSeries(series1);
		model.addSeries(series2);
		model.addSeries(series3);

		return model;
	}
	
	private BarChartModel initBarSearchModel(){
		
		BarChartModel modelSearch = new BarChartModel();
		
		ChartSeries series1 = new ChartSeries();
		ChartSeries series2 = new ChartSeries();
		ChartSeries series3 = new ChartSeries();
		ChartSeries series4 = new ChartSeries();
		ChartSeries series5 = new ChartSeries();
		ChartSeries series6 = new ChartSeries();
		ChartSeries series7 = new ChartSeries();
		ChartSeries series8 = new ChartSeries();

		series1.setLabel("Musicas");
		series2.setLabel("Categorias_Musicas");
		series3.setLabel("Videos");
		series4.setLabel("Categorias_Videos");
		series5.setLabel("Livros");
		series6.setLabel("Lugares");
		series7.setLabel("Como Chegar");
		series8.setLabel("Clima");

		maxSearch = 0;

		Calendar initialCalendar = Calendar.getInstance();
		initialCalendar.set(Calendar.HOUR_OF_DAY, 0);
		initialCalendar.set(Calendar.MINUTE, 0);
		initialCalendar.set(Calendar.SECOND, 0);
		Calendar finalCalendar = Calendar.getInstance();
		finalCalendar.set(Calendar.HOUR_OF_DAY, 23);
		finalCalendar.set(Calendar.MINUTE, 59);
		finalCalendar.set(Calendar.SECOND, 59);

		initialCalendar.set(Calendar.DAY_OF_MONTH,
				initialCalendar.get(Calendar.DAY_OF_MONTH) - (daysNumber - 1));
		finalCalendar.set(Calendar.DAY_OF_MONTH,
				finalCalendar.get(Calendar.DAY_OF_MONTH) - (daysNumber - 1));
		series1.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "musics"+ systemVersionRadioValue));
		series2.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "music_categories"+ systemVersionRadioValue));
		series3.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "videos"+ systemVersionRadioValue));
		
		series4.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "video_categories"+ systemVersionRadioValue));
		series5.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "books"+ systemVersionRadioValue));
		
		series6.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "places"+ systemVersionRadioValue));
		
		series7.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "places_howGetThere"+ systemVersionRadioValue));
		
				
		series8.set(
				sdf2.format(initialCalendar.getTime()),
				searchQueriesAction(initialCalendar.getTime(),
						finalCalendar.getTime(), "weather"+ systemVersionRadioValue));
		
		for (int i = 0; i < (daysNumber - 1); i++) {
			initialCalendar.set(Calendar.DAY_OF_MONTH,
					initialCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			finalCalendar.set(Calendar.DAY_OF_MONTH,
					finalCalendar.get(Calendar.DAY_OF_MONTH) + 1);
			
			int musicsCount = searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "musics"+ systemVersionRadioValue);
			
			if (musicsCount > maxSearch) {
				maxSearch = musicsCount;
			}
			
			int musicCatCount = searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "music_categories"+ systemVersionRadioValue);
			
			if (musicCatCount > maxSearch) {
				maxSearch = musicCatCount;
			}
			
			int videosCount = searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "videos"+ systemVersionRadioValue);
			
			if (videosCount > maxSearch) {
				maxSearch = videosCount;
			}
			
			int videoCatCount =	searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "video_categories"+ systemVersionRadioValue);
			
			if (videoCatCount > maxSearch) {
				maxSearch = videoCatCount;
			}
			
			int booksCount = searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "books"+ systemVersionRadioValue);
			
			if (booksCount > maxSearch) {
				maxSearch = booksCount;
			}
			
			int placesCount = searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "places"+ systemVersionRadioValue);
			
			if (placesCount > maxSearch) {
				maxSearch = placesCount;
			}
			
			int placesHowGetThereCount = searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "places_howGetThere"+ systemVersionRadioValue);
			
			if (placesHowGetThereCount > maxSearch) {
				maxSearch = placesHowGetThereCount;
			}
			
					
			int weatherCount = 	searchQueriesAction(initialCalendar.getTime(),
							finalCalendar.getTime(), "weather"+ systemVersionRadioValue);
			
			if (weatherCount > maxSearch) {
				maxSearch = weatherCount;
			}
			
			series1.set(sdf2.format(initialCalendar.getTime()), musicsCount);
			series2.set(sdf2.format(initialCalendar.getTime()), musicCatCount);
			series3.set(sdf2.format(initialCalendar.getTime()), videosCount);
			series4.set(sdf2.format(initialCalendar.getTime()), videoCatCount);
			series5.set(sdf2.format(initialCalendar.getTime()), booksCount);
			series6.set(sdf2.format(initialCalendar.getTime()), placesCount);
			series7.set(sdf2.format(initialCalendar.getTime()), placesHowGetThereCount);
			series8.set(sdf2.format(initialCalendar.getTime()), weatherCount);

		}
		
		modelSearch.addSeries(series1);
		modelSearch.addSeries(series2);
		modelSearch.addSeries(series3);
		modelSearch.addSeries(series4);
		modelSearch.addSeries(series5);
		modelSearch.addSeries(series6);
		modelSearch.addSeries(series7);
		modelSearch.addSeries(series8);
		
		return modelSearch;
		
	}
	
	public void updateSearchModel(AjaxBehaviorEvent event){
		createAnimatedSearchModels();
	}

	public int searchAccessAction(Date initialDate, Date finalDate,
			String systemVersion) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");

		int resultsCountAccess = 0;
		log.debug("\n----->>>>  searchAccessAction called:  initialDate: "
				+ initialDate.toString() + " finalDate: "
				+ finalDate.toString() + " version: " + systemVersion);

		try {
			resultsCountAccess = ServiceLocator
					.getInstance()
					.getReportsFacade()
					.getAccessCountByPeriod(sdf.format(initialDate),
							sdf.format(finalDate), systemVersion);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("\n--->>> Registers: " + resultsCountAccess);

		return resultsCountAccess;
	}

	public void searchAndroidAccessAction(ActionEvent actionEvent) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");

		resultsCountAndroidAccess = 0;
		log.debug("\n----->>>>  searchAndroidAccessAction called:  initialDate: "
				+ initialDateAndroidAccess.toString()
				+ " finalDate: "
				+ finalDateAndroidAccess.toString());

		try {

			resultsCountAndroidAccess = ServiceLocator
					.getInstance()
					.getReportsFacade()
					.getAccessCountByPeriod(
							sdf.format(initialDateAndroidAccess),
							sdf.format(finalDateAndroidAccess),
							"MOBILE ACCESS_Android");

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("\n--->>> Registers: " + resultsCountAndroidAccess);

		addMessage("Resultado: " + resultsCountAndroidAccess + " acessos");
	}

	public int searchQueriesAction(Date initialDate, Date finalDate,String queryParameter) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");

		int resultsCountQueries = 0;
		log.debug("\n----->>>>  searchQueriesAction called:  initialDate: "
				+ initialDate.toString() + " finalDate: "
				+ finalDate.toString() + " category: " + queryParameter);
		// musics, music_categories, videos, video_categories, places, books,
		// weather
		try {

			resultsCountQueries = ServiceLocator.getInstance().getReportsFacade().getQueriesCountByPeriod(sdf.format(initialDate),
							sdf.format(finalDate),
							queryParameter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("\n--->>> Registers: " + resultsCountQueries);
        
		return resultsCountQueries;
		
	}

	public List<String> autoCompleteAction() {
		return new LinkedList<String>();
	}

	public Date getInitialDateQuery() {
		return initialDateQuery;
	}

	public void setInitialDateQuery(Date initialDateQuery) {
		log.debug("\n-->setInitialDateQuery()");
		this.initialDateQuery = initialDateQuery;
	}

	public Date getFinalDateQuery() {
		return finalDateQuery;
	}

	public void setFinalDateQuery(Date finalDateQuery) {
		log.debug("\n-->setFinalDateQuery()");
		this.finalDateQuery = finalDateQuery;
	}

	public Date getInitialDateAccess() {
		return initialDateAccess;
	}

	public void setInitialDateAccess(Date initialDateAccess) {
		log.debug("\n-->setInitialDateAccess()");
		this.initialDateAccess = initialDateAccess;
	}

	public Date getFinalDateAccess() {
		return finalDateAccess;
	}

	public void setFinalDateAccess(Date finalDateAccess) {
		log.debug("\n-->setFinalDateAccess()");
		this.finalDateAccess = finalDateAccess;
	}

	public String getCategoryNameQuery() {
		return categoryNameQuery;
	}

	public void setCategoryNameQuery(String categoryNameQuery) {
		this.categoryNameQuery = categoryNameQuery;
	}

	public String getCategoryNameAccess() {
		return categoryNameAccess;
	}

	public void setCategoryNameAccess(String categoryNameAccess) {
		this.categoryNameAccess = categoryNameAccess;
	}

	public int getResultsCountQueries() {
		return resultsCountQueries;
	}

	public void setResultsCountQueries(int resultsCountQueries) {
		this.resultsCountQueries = resultsCountQueries;
	}

	public int getResultsCountAccess() {
		return resultsCountAccess;
	}

	public void setResultsCountAccess(int resultsCountAccess) {
		this.resultsCountAccess = resultsCountAccess;
	}

	public int getResultsCountAndroidAccess() {
		return resultsCountAndroidAccess;
	}

	public void setResultsCountAndroidAccess(int resultsCountAndroidAccess) {
		this.resultsCountAndroidAccess = resultsCountAndroidAccess;
	}

	public String getSystemVersionRadioValue() {
		return systemVersionRadioValue;
	}

	public void setSystemVersionRadioValue(String systemVersionRadioValue) {
		this.systemVersionRadioValue = systemVersionRadioValue;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
