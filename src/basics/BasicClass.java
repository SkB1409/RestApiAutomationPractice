package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BasicClass {

	public static void main(String[] args) {

		// Validate AddPlace API is working as expected

		// given - all input details
		// when - submit the API - resource, http methods
		// then - validations

		// ADD PLACE
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content.Type", "application/json")
				.body(payload.Addplace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println("The response for the request sent is: " + response);

		// In order to extract the place id to use it in another tests we need to parse the JSON body to get the values
		JsonPath js = ReUsableMethods.rawtoJson(response); // Removed the java code and moved it to re usable method
		String placeId = js.getString("place_id");
		System.out.println("The extracted place id from the response body is : " + placeId);

		// UPDATE PLACE
		String updatedAddress = "Telangana, Hyderabad 500091";
		given().log().all().queryParam("key", "qaclick123").header("Content.Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + updatedAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// GET UPDATED PLACE
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.asString();

		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse); // Re used the java code from reusable methods to
																	// make it more readable
		String actualAddres = js1.get("address");

		System.out.println("The actual updated address is: " + actualAddres);

		Assert.assertEquals(actualAddres, updatedAddress);

		System.out.println("The updated address and the address fetched from Get Place API are same");
	}

}
