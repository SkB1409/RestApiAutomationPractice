package basics;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {

		int sum = 0;
		JsonPath js = new JsonPath(payload.mockJson());

		int count = js.getInt("courses.size()");
		for (int i = 0; i < count; i++) {

			String courseTitle = js.getString("courses[" + i + "].title");
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			int amount = price * copies;
			System.out.println("The total amount of individual course " + courseTitle + " is: " + amount);
			sum = sum + amount;

		}

		System.out.println("The total sum of all the courses is: " + sum);
		int purchaseAmount = js.get("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
	}
}
