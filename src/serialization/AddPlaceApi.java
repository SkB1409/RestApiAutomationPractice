package serialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class AddPlaceApi {

	public static void main(String[] args) {

		// Creating an object of the AddApi class
		AddPlaceJson a = new AddPlaceJson();
		a.setAccuracy(50);
		a.setName("Technoidentity");
		a.setPhone_number("+91-7013771877");
		a.setLanguage("British-EN");
		a.setAddress("15-158/A, Sanat Nagar, Hyderabad, Telangana 500091");
		a.setWebsite("https://www.facebook.com");

		//Creating object for location class is this is the sub Json
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		a.setLocation(l);

		// Creating object for Array list class as it contains dynamic numbers
		List<String> myTypes = new ArrayList<String>();
		myTypes.add("Shoe Park");
		myTypes.add("Shop");
		a.setTypes(myTypes);

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/Json")
				.body(a).when().post("maps/api/place/add/json").then().log().all().statusCode(200).extract().response()
				.asString();

		System.out.println(response);

	}

}
