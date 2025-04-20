package Utils;

import java.io.FileReader;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonObject;

public class JsonReader {
	public static Object obj = null;
	public static JSONObject jo = null;
	public static Map<String,String> dataFromJSON;
	public static JSONParser parser = new JSONParser();
	
	// ------To get jsondata 'dataName' from JSON file 'jsonFileName'
		@SuppressWarnings({ "unchecked" })
		public static Map<String, String> getJsonDataFromJsonFile(String jsonFileName, String dataName) {

			try {
				obj = parser.parse(new FileReader("src/test/resources/JsonFiles/" + jsonFileName + ".json"));
			} catch (Exception e) {
			}
			jo = (JSONObject) obj;
			dataFromJSON = ((Map<String, String>) jo.get(dataName));
			return dataFromJSON;
		}
		
		public static void main(String[] args) {
			Map<String, String> ErrorMessagges = getJsonDataFromJsonFile("SignUp","ErrorMessages");
			System.out.println(ErrorMessagges.toString());
			System.out.println(ErrorMessagges.get("PasswordErrorMessage"));
		}
}
