package serialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest {

	public static void main(String[] args) {

		// Creating an object of the AddApi class
		AddPlaceJson a = new AddPlaceJson();
		a.setAccuracy(50);
		a.setName("Technoidentity");
		a.setPhone_number("+91-7013771877");
		a.setLanguage("British-EN");
		a.setAddress("15-158/A, Sanat Nagar, Hyderabad, Telangana 500091");
		a.setWebsite("https://www.facebook.com");

		// Creating object for location class is this is the sub Json
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		a.setLocation(l);

		// Creating object for Array list class as it contains dynamic numbers
		List<String> myTypes = new ArrayList<String>();
		myTypes.add("Shoe Park");
		myTypes.add("Shop");
		a.setTypes(myTypes);

		// Introducing Req, Res Spec builders to optimize the code
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		/*
		 * RestAssured.baseURI = "https://rahulshettyacademy.com";
		 * .log().all().queryParam("key", "qaclick123").header("Content-Type",
		 * "application/Json")
		 */

		// log().all().statusCode(200)

		RequestSpecification request = given().spec(req).body(a);

		Response response = request.when().post("maps/api/place/add/json").then().spec(resp).extract().response();

		String responseString = response.asString();
		System.out.println(responseString);

	}

}
