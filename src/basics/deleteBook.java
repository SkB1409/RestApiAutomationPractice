package basics;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;

public class deleteBook<bookId> {

	private List<String> addedBookIds = new ArrayList<>();

	@Test(dataProvider = "BooksInfo")
	public void testAddBooks(String isbn, String aisle) {
		// Add books and store their IDs
		String bookId1 = addBookAndGetId(isbn, aisle);
		/*
		 * String bookId2 = addBookAndGetId(isbn, aisle); String bookId3 =
		 * addBookAndGetId(isbn, aisle);
		 */

		addedBookIds.add(bookId1);
		/*
		 * addedBookIds.add(bookId2); addedBookIds.add(bookId3);
		 */
		
		System.out.println("Book Id's are: " + addedBookIds);
	}

	@Test(dependsOnMethods = "testAddBooks")
	public void testDeleteBooks() {
		// Delete books using stored IDs
		/*
		 * for (String bookId : addedBookIds) { System.out.println("######");
		 * System.out.println(bookId); deleteBook(bookId);
		 * 
		 * System.out.println("****************");
		 * 
		 * 
		 * }
		 */
		
		for(int i=1;i<addedBookIds.size();i++)
		{
			deleteBook(addedBookIds.get(i));
			
			System.out.println("Books are deleted");
		}
	}

	// Helper method to add a book and return its ID
	private String addBookAndGetId(String isbn, String aisle) {
		// Send POST request to add the book and extract the book ID
		RestAssured.baseURI = "http://216.10.245.166";
		String bookId = given().log().all().header("Content-Type", "application/json")
				.body(payload.AddBook(isbn, aisle)).when().post("Library/Addbook.php").then().assertThat()
				.statusCode(200).extract().path("ID");
		System.out.println(bookId);
		return bookId;
	}

	// Helper method to delete a book by its ID
	private void deleteBook(String bookId) {
		// Send DELETE request to delete the book using the book ID
		RestAssured.baseURI = "http://216.10.245.166";
		given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"ID\" : \"" + bookId + "\"\r\n" + "}").when().delete("/Library/DeleteBook.php")
				.then().log().all().statusCode(200);
	}

	@DataProvider(name = "BooksInfo")
	public Object[][] getData() {
		return new Object[][] { { "qqanf0", "e2156" }, { "wx12e", "i70828" }, { "vaf2bi", "061" }, { "u2fh", "508a2" } };
	}
}
