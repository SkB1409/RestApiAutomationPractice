package basics;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test
	public void addBook() {
		//Passing the payload from the test i.e. aisle, isbn values instead from the payload like in previous classes
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payload.AddBook("wOsF", "5405")).when().post("Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().asString();

		System.out.println("The response is: " + response);

		JsonPath js = ReUsableMethods.rawtoJson(response);

		String Id = js.get("ID");
		
		System.out.println("The id of the added book is: " + Id);
	}

}
