package ricardombertani.projetos.allinyourhands.reports.dao;


import java.util.List;
import javax.ejb.Local;

@Local
public interface AccessRegisterInterfaceDAO {

	public void saveReportData(String name, String type, String artist, int accessedContent, String Date);

	/**
	 * Quantidade de acessos por tipo de device
	 * 
	 * @param accessName
	 * @return
	 */
	public int getAccessCount(String accessName);

	/**
	 * Quantidade de acessos por tipo de device e período
	 * 
	 * @param accessName
	 * @return
	 */
	public int getAccessCountByPeriod(String initialDate, String finalDate,String accessName); // MOBILE ACCESS, IPHONE ACCESS, WINDOWSPHONE								// ACCESS, BROWSER ACCESS

	/**
	 * Quantia de consultas por tipo
	 * 
	 * @param contentType
	 * @return
	 */
	public int getQueryCount(String contentType); // musics, music_categories,
													// videos, video_categories,
													// places, books, weather ou
													// "" para total

	/**
	 * Consultas por tipo
	 * 
	 * @param contentType
	 * @return
	 */
	public List<String> getQuerysByType(String contentType); // musics,
																	// music_categories,
																	// videos,
																	// video_categories,
																	// places,
																	// books,
																	// weather

	/**
	 * Obtem a quantidade de queries no periodo
	 * 
	 * @param initialDate
	 * @param finalDate
	 * @param type
	 * @return
	 */
	public int getQueriesCountByPeriod(String initialDate, String finalDate, String type); // musics, music_categories, videos,
																							// video_categories, places, books,
																							// weather

	/**
	 * Obtem as queries no periodo
	 * 
	 * @param initialDate
	 * @param finalDate
	 * @param type
	 * @return
	 */
	public List<String> getQueriesByPeriod(String initialDate, String finalDate, String type); // musics, music_categories, videos,
																								// video_categories, places, books,
																								// weather

}
