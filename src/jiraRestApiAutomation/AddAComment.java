package jiraRestApiAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class AddAComment {

	public static void main(String[] args) {

		// Login to Jira Dashboard
		// Base URI
		RestAssured.baseURI = "http://localhost:8088";

		// In order to get the session id, instead of using Json parser we are using
		// SessionFilter to catch the session filter cookie
		SessionFilter session = new SessionFilter();

		String response = given().log().all().header("Content-Type", "application/Json")
				.body("{\r\n" + "    \"username\": \"khadhar.sel\",\r\n" + "    \"password\": \"Khadar@1409\"\r\n"
						+ "}")
				.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().assertThat()
				.statusCode(200).extract().asString();

		// In order to get the session id from the above response, we are using Session
		// filter
		System.out.println(response);

		// Adding a comment to the issue created
		String actualComment = "New comment is added to jira issue via rest automation by individual test";
		String addCommentResponse = given().log().all().pathParam("key", "10101")
				.header("Content-Type", "application/Json")
				.body("{\r\n" + "    \"body\": \"" + actualComment + "\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201).body("body", equalTo(actualComment)).extract().response().asString();
		
		System.out.println(addCommentResponse);

	}

}
