package files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {

	public static JsonPath rawtoJson(String response) {

		JsonPath js = new JsonPath(response); // parsing the json and stored in js

		return js;
	}

	public static JsonPath rawToJson(String response) {

		JsonPath js1 = new JsonPath(response);

		return js1;
	}

}
