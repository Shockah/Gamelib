package tests;

import pl.shockah.json.JSONList;
import pl.shockah.json.JSONParser;
import pl.shockah.json.JSONPrettyPrinter;

public class JSONTest {
	public static void main(String[] args) {
		JSONList<?> j = new JSONParser().parseList("[{\"value\": \"off\",\"display\": \"Disabled\"},{\"value\": \"sao\",\"display\": \"Sword Art Online\"},{\"value\": \"alo\",\"display\": \"ALfheim Online\"}]");
		
		System.out.println(new JSONPrettyPrinter().print(j));
	}
}