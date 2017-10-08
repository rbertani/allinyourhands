package ricardombertani.projetos.allinyourhands.core.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Implementa o Conjunto de Espalhamento - solucao utilizada para melhorar o desempenho de busca de
// um determinado termo em um conjunto grande de dados de texto
public class ScatteringSet {
   
	private List<List<String>> table = new ArrayList<List<String>>();
	private int tamanho = 0;
 
	// Definimos que nossa tabela ter� 26 posi��es (26 grupos de words, onde
	// o grupo �
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
	 * M�todo que calcula um codigo de espalhamento considerando toda a word
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
	 * Este m�todo implementa a fun��o de espalhamento, respons�vel por analisar
	 * uma word e atraves de sua primeira letra determinar um index, que
	 * ser� utilizado para determinar em qual conjunto de words ela ser�
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
														// est� sobrecarregada,
														// se for menor est� ok

		if (loadCap > 0.75) {
			resizeTable(capacity * 2);
		} else if (loadCap < 0.25) {
			resizeTable(Math.max(capacity / 2, 10));
		}
	}

	public String removeAccents(String passa){  
        passa = passa.replaceAll("[�����]","A");  
        passa = passa.replaceAll("[�����]","a");  
        passa = passa.replaceAll("[����]","E");  
        passa = passa.replaceAll("[����]","e");  
        passa = passa.replaceAll("����","I");  
        passa = passa.replaceAll("����","i");  
        passa = passa.replaceAll("[�����]","O");  
        passa = passa.replaceAll("[�����]","o");  
        passa = passa.replaceAll("[����]","U");  
        passa = passa.replaceAll("[����]","u");  
        passa = passa.replaceAll("�","C");  
        passa = passa.replaceAll("�","c");   
        passa = passa.replaceAll("[��]","y");  
        passa = passa.replaceAll("�","Y");  
        passa = passa.replaceAll("�","n");  
        passa = passa.replaceAll("�","N");  
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