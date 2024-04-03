package basics;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	// Add Books
	@Test(dataProvider = "BooksInfo")
	public void addBooks(String isbn, String aisle) {
		addBook(isbn, aisle);
		System.out.println("Book is added");

	}

	//Delete Added Books
	@Test(dependsOnMethods = "addBooks", dataProvider = "BooksInfo")
	public void deleteBooks(String isbn, String aisle) {
		String result1 = isbn + "" + aisle;
		deleteBook(result1);
		System.out.println("Book is deleted");

	}

	public String addBook(String isbn, String aisle) {
		// Passing the payload from the test i.e. aisle, isbn values instead from the
		// payload like in previous classes
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payload.AddBook(isbn, aisle)).when().post("Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().asString();

		System.out.println("The response is: " + response);

		JsonPath js = ReUsableMethods.rawtoJson(response);

		String bookId = js.get("ID");

		System.out.println("The id of the added book is: " + bookId);

		return bookId;

	}

	// Delete Books

	public void deleteBook(String bookId) {

		RestAssured.baseURI = "http://216.10.245.166";
		String resp = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"ID\" : \"" + bookId + "\"\r\n" + "}").when().delete("/Library/DeleteBook.php")
				.then().log().all().assertThat().statusCode(200).extract().asString();

		System.out.println(resp);

	}


	@DataProvider(name = "BooksInfo")
	public Object[][] getData() {
		return new Object[][] { { "qe1fq", "qf24" }, { "iq1w", "i4q8" }, { "wn4q", "q281" } };
	}

}
