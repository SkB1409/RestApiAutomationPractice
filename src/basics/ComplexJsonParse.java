package basics;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(payload.mockJson());

		// Print no of courses
		int courseCount = js.getInt("courses.size()");
		System.out.println("The no. of courses present in the given Json are: " + courseCount);

		// Print purchase amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("The purchase amount in the giveb Json is: " + purchaseAmount);

		// Print title of the first course
		String firstCourseTitle = js.get("courses[0].title");
		System.out.println("The title of the first course is: " + firstCourseTitle);

		// Print title of the third course
		String thirdCourseTitle = js.get("courses[2].title");
		System.out.println("The title of the first course is: " + thirdCourseTitle);

		// Print all the courses titles and their respective prices

		for (int i = 0; i < courseCount; i++) {

			System.out.println(js.get("courses[" + i + "].title").toString());

			int coursePrices = js.getInt("courses[" + i + "].price");
			System.out.println("The above course price is: " + coursePrices + " rupees");
		}

		// Print no of copies sold by RPA Course
		for (int i = 0; i < courseCount; i++) {

			String courseTitles = js.get("courses[" + i + "].title");
			if (courseTitles.equalsIgnoreCase("RPA")) {

				int copiesSold = js.getInt("courses[" + i + "].copies");
				System.out.println("No of copies sold by RPA Course are: " + copiesSold);
				break;
			}

		}

	}
}
