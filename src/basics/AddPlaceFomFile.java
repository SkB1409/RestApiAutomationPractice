package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;

public class AddPlaceFomFile {

	// Convert the content of file in String -> Byte -> Byte data to String by using Files class
	public static void main(String[] args) throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		given().log().all().queryParam("key", "qaclick123").header("Content.Type", "application/Json")
				.body(new String(
						Files.readAllBytes(Paths.get("C:\\Users\\khadh\\OneDrive\\Desktop\\AddPlace.json"))))
				.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)");

		// Instead of log().all() we can convert the response by extract().asString()
		// into a String and can print it

	}
}
