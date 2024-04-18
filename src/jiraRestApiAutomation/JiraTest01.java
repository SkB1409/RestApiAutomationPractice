package jiraRestApiAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest01 {

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

		/*
		 * JsonPath js = new JsonPath(response); String sessionId =
		 * js.get("session.value");
		 * 
		 * System.out.println("The session id from the above response is: " +
		 * sessionId);
		 */

		System.out.println(response);

		// Adding a comment to the issue created
		String actualComment = "New comment is added to jira issue via rest automation";
		String addCommentResponse = given().log().all().pathParam("key", "10101")
				.header("Content-Type", "application/Json")
				// .header("Cookie","JSESSIONID=DDCC27417B926318AB822E6CE222DE68")
				.body("{\r\n" + "    \"body\": \"" + actualComment + "\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201).body("body", equalTo(actualComment)).extract().response().asString();

		JsonPath js1 = new JsonPath(addCommentResponse);
		String commentId = js1.get("id");
		System.out.println("The added comment is: " + commentId);

		// Adding an attachment
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10101")
				.header("Content-Type", "multipart/form-data").multiPart("file", new File("sampleFile.txt")).when()
				.post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

		// Get an issue with response having required fields
		String issueDetails = given().header("Content-Type", "application/json").filter(session)
				.pathParam("key", "10101").queryParam("fields", "comment").log().all().when()
				.get("/rest/api/2/issue/{key}").then().assertThat().statusCode(200).log().all().extract().response()
				.asString();

		System.out.println("The issue details are as follows: " + issueDetails);

		JsonPath js = new JsonPath(issueDetails);
		int commentsCount = js.get("fields.comment.comments.size()");

		for (int i = 0; i < commentsCount; i++) {

			String commentIssueId = js.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIssueId.equalsIgnoreCase(commentId)) {
				String expectedComment = js.get("fields.comment.comments[" + i + "].body").toString();
				Assert.assertEquals(expectedComment, actualComment);

				System.out.println("The actual comment added: " + actualComment + " and "
						+ "The exepcted comment are same: " + expectedComment);
			}

		}

	}

}
