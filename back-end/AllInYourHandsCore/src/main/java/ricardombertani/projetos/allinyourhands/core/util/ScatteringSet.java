package ricardombertani.projetos.allinyourhands.core.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Implementa o Conjunto de Espalhamento - solucao utilizada para melhorar o desempenho de busca de
// um determinado termo em um conjunto grande de dados de texto
public class ScatteringSet {
   
	private List<List<String>> table = new ArrayList<List<String>>();
	private int tamanho = 0;
 
	// Definimos que nossa tabela terá 26 posições (26 grupos de words, onde
	// o grupo é
	// determinado pelas letras do alfabeto)
	public ScatteringSet() {
		for (int i = 0; i < 26; i++) {
			LinkedList<String> list = new LinkedList<String>();
			table.add(list);
		}
	}

	public void add(String word) {
		if (!contains(word)) { // a word nao foi inserida ainda, a
								// adicionamos
			verifyCapacityLoad();
			
			word = removeAccents(word).toLowerCase(); // consideramos a word em letras minusculas e sem acentos
			
			int index = calculateTableIndex( word );
			table.get(index).add( word );
			tamanho++;
		}

	}

	public void remove(String word) {
		if (contains(word)) {
			word = removeAccents(word).toLowerCase(); // consideramos a word em letras minusculas e sem acentos
			
			int index = calculateTableIndex(word);
			table.get(index).remove(word);
			tamanho--;
			verifyCapacityLoad();
		}

	}

	public boolean contains(String word) {

		word = removeAccents(word).toLowerCase(); // consideramos a frase em letras minusculas e sem acentos		
		
	    int index = calculateTableIndex(word);
		if (table.get(index).contains(word)) {
			return true;
		}else return false;
		
		

	}
	
	public boolean containsSubstring(String word) {

		word = removeAccents(word).toLowerCase(); // consideramos a frase em letras minusculas e sem acentos		
		
		int index = calculateTableIndex(word);
		for(String wordOfSet : table.get(index)){
			if(word.indexOf(wordOfSet) > -1){
				return true;
			}
		}
		return false;

	}
		
	public List<String> getAllWords() {
		List<String> words = new ArrayList<String>();

		for (int i = 0; i < table.size(); i++) {
			words.addAll(table.get(i));
		}

		return words;
	}

	public int tamanho() {
		return tamanho;
	}

	/**
	 * Método que calcula um codigo de espalhamento considerando toda a word
	 * 
	 * @param word
	 * @return
	 */
	private int calculateScatteringCode(String word) {
		int code = 1;
		for (int i = 0; i < word.length(); i++) {
			code = 31 * code + word.charAt(i);
		}
		return code;
	}

	/**
	 * Este método implementa a função de espalhamento, responsável por analisar
	 * uma word e atraves de sua primeira letra determinar um index, que
	 * será utilizado para determinar em qual conjunto de words ela será
	 * classificada
	 * 
	 * @param word
	 * @return
	 */
	public int calculateTableIndex(String word) {
		int scatteringCode = calculateScatteringCode(word);
		return Math.abs(scatteringCode) % table.size();
	}

	private void resizeTable(int newCapacity) {
		List<String> words = getAllWords();
		table.clear();

		for (int i = 0; i < newCapacity; i++) {
			table.add(new LinkedList<String>());
		}

		for (String word : words) {
			add(word);
		}
	}

	private void verifyCapacityLoad() {
		int capacity = table.size();
		double loadCap = (double) tamanho / capacity; // calculo de carga da
														// tabela, se for > 0.75
														// está sobrecarregada,
														// se for menor está ok

		if (loadCap > 0.75) {
			resizeTable(capacity * 2);
		} else if (loadCap < 0.25) {
			resizeTable(Math.max(capacity / 2, 10));
		}
	}

	public String removeAccents(String passa){  
        passa = passa.replaceAll("[ÂÀÁÄÃ]","A");  
        passa = passa.replaceAll("[âãàáä]","a");  
        passa = passa.replaceAll("[ÊÈÉË]","E");  
        passa = passa.replaceAll("[êèéë]","e");  
        passa = passa.replaceAll("ÎÍÌÏ","I");  
        passa = passa.replaceAll("îíìï","i");  
        passa = passa.replaceAll("[ÔÕÒÓÖ]","O");  
        passa = passa.replaceAll("[ôõòóö]","o");  
        passa = passa.replaceAll("[ÛÙÚÜ]","U");  
        passa = passa.replaceAll("[ûúùü]","u");  
        passa = passa.replaceAll("Ç","C");  
        passa = passa.replaceAll("ç","c");   
        passa = passa.replaceAll("[ýÿ]","y");  
        passa = passa.replaceAll("Ý","Y");  
        passa = passa.replaceAll("ñ","n");  
        passa = passa.replaceAll("Ñ","N");  
        passa = passa.replaceAll("['<>\\|/]","");  
        
        return passa;  
    }	
	
	public void printTable() {
		for (List<String> lista : table) {
			System.out.print("[");
			for (int i = 0; i < lista.size(); i++) {
				System.out.print("*");
			}
			System.out.println("]");
		}
	}

}