package ricardombertani.projetos.allinyourhands.web.util;

import java.util.HashMap;

public class EncodingFixer{

	public static String fixEncodingIssues(String originalMessage) {
		HashMap<String, String> hashMap = new HashMap<String, String>();

		 hashMap.put("Ã€","À");
		 hashMap.put("Ã","Á");
		 hashMap.put("Ã‚","Â");
		 hashMap.put("Ãƒ","Ã");
		 hashMap.put("Ã„","Ä");
		 hashMap.put("Ã…","Å");
		 hashMap.put("Ã†","Æ");
		 hashMap.put("Ã‡","Ç");
		 hashMap.put("Ãˆ","È");
		 hashMap.put("Ã‰","É");
		 hashMap.put("ÃŠ","Ê");
		 hashMap.put("Ã‹","Ë");
		 hashMap.put("ÃŒ","Ì");
		 hashMap.put("Ã","Í");
		 hashMap.put("ÃŽ","Î");
		 hashMap.put("Ã","Ï");
		 hashMap.put("Ã","Ð");
		 hashMap.put("Ã‘","Ñ");
		 hashMap.put("Ã’","Ò");
		 hashMap.put("Ã“","Ó");
		 hashMap.put("Ã”","Ô");
		 hashMap.put("Ã•","Õ");
		 hashMap.put("Ã–","Ö");
		 hashMap.put("Ã—","×");
		 hashMap.put("Ã˜","Ø");
		 hashMap.put("Ã™","Ù");
		 hashMap.put("Ãš","Ú");
		 hashMap.put("Ã›","Û");
		 hashMap.put("Ãœ","Ü");
		 hashMap.put("Ã","Ý");
		 hashMap.put("Ãž","Þ");
		 hashMap.put("ÃŸ","ß");
		 hashMap.put("Ã","à");
		 hashMap.put("Ã¡","á");
		 hashMap.put("Ã¢","â");
		 hashMap.put("Ã£","ã");
		 hashMap.put("Ã¤","ä");
		 hashMap.put("Ã¥","å");
		 hashMap.put("Ã¦","æ");
		 hashMap.put("Ã§","ç");
		 hashMap.put("Ã¨","è");
		 hashMap.put("Ã©","é");
		 hashMap.put("Ãª","ê");
		 hashMap.put("Ã«","ë");
		 hashMap.put("Ã¬","ì");
		 hashMap.put("Ã­","í");
		 hashMap.put("Ã®","î");
		 hashMap.put("Ã¯","ï");
		 hashMap.put("Ã°","ð");
		 hashMap.put("Ã±","ñ");
		 hashMap.put("Ã²","ò");
		 hashMap.put("Ã³","ó");
		 hashMap.put("Ã´","ô");
		 hashMap.put("Ãµ","õ");
		 hashMap.put("Ã¶","ö");
		 hashMap.put("Ã·","÷");
		 hashMap.put("Ã¸","ø");
		 hashMap.put("Ã¹","ù");
		 hashMap.put("Ãº","ú");
		 hashMap.put("Ã»","û");
		 hashMap.put("Ã¼","ü");
		 hashMap.put("Ã½","ý");
		 hashMap.put("Ã¾","þ");
		 hashMap.put("Ã¿","ÿ");

		if (originalMessage != null) {
			for (String s : hashMap.keySet()) { // percorrendo os valores do key do map
												// (valores estranhos de
												// encoding)

				if (originalMessage.contains(s)) {
					originalMessage = originalMessage.replaceAll(s,	hashMap.get(s));
				}

			}			
			
		}

		return originalMessage;

	}
	
}