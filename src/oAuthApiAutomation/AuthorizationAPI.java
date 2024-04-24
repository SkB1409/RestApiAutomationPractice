package oAuthApiAutomation;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class AuthorizationAPI {

	public static void main(String[] args) {

		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };
		// Getting access token by using client credentials grant type
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String access_token = js.getString("access_token");

		// Get course details
		GetCourse courseDetails = given().log().all().queryParam("access_token", access_token).when()
				.get("/oauthapi/getCourseDetails").as(GetCourse.class);

		System.out.println("The linkedIn url from the response is: " + courseDetails.getlinkedIn());

		System.out.println("The instructor name is: " + courseDetails.getInstructor());

		System.out.println("The 2nd course tilte in API Courses is: "
				+ courseDetails.getCourses().getApi().get(1).getCourseTitle());

		List<Api> ApiCourses = courseDetails.getCourses().getApi();

		for (int i = 0; i < ApiCourses.size(); i++) {
			if (ApiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(
						"The 2nd API course price is: " + courseDetails.getCourses().getApi().get(i).getPrice());

			}
		}

		// Get all course titles of Web Automation and verify

		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> webAutomationCourseTitles = courseDetails.getCourses().getWebAutomation();

		// System.out.println("The web automation course titles are as follows: ");
		for (int j = 0; j < webAutomationCourseTitles.size(); j++) {
			System.out.println(webAutomationCourseTitles.get(j).getCourseTitle());
		}

		// Comparing both actual and expected titles
		for (int k = 0; k < webAutomationCourseTitles.size(); k++) {
			a.add(webAutomationCourseTitles.get(k).getCourseTitle());
		}

		List<String> expectedTitles = Arrays.asList(courseTitles);

		Assert.assertTrue(a.equals(expectedTitles));

		System.out.println("Bothe expected and acutal course titles are same");

	}

}
