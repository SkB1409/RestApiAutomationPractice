package files;

public class payload {

	// This Method is defined as static in order to use it directly into the test
	// without creating an object of this class
	public static String Addplace() {

		return "{\r\n" + "    \"location\": {\r\n" + "        \"lat\": -38.383495,\r\n"
				+ "        \"lng\": 33.427362\r\n" + "    },\r\n" + "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"Shaik Khadhar Basha\",\r\n" + "    \"phone_number\": \"(+91) 7013771877\",\r\n"
				+ "    \"address\": \"29, side layout, cohen 09\",\r\n" + "    \"types\": [\r\n"
				+ "        \"shoe park\",\r\n" + "        \"shop\"\r\n" + "    ],\r\n"
				+ "    \"website\": \"http://google.com\",\r\n" + "    \"language\": \"French-IN\"\r\n" + "}";
	}

	public static String mockJson() {

		return "{ \r\n" + "\r\n" + "\"dashboard\": { \r\n" + "\"purchaseAmount\": 1735, \r\n"
				+ "\"website\": \"rahulshettyacademy.com\" \r\n" + "\r\n" + "}, \r\n" + "\r\n" + "\"courses\": [ \r\n"
				+ "\r\n" + "{ \r\n" + "\r\n" + "\"title\": \"Selenium Python\", \r\n" + "\"price\": 50, \r\n"
				+ "\"copies\": 6 \r\n" + "\r\n" + "}, \r\n" + "\r\n" + "{ \r\n" + "\r\n"
				+ "\"title\": \"Cypress\", \r\n" + "\"price\": 40, \r\n" + "\"copies\": 4 \r\n" + "\r\n" + "}, \r\n"
				+ "\r\n" + "{ \r\n" + "\r\n" + "\"title\": \"RPA\", \r\n" + "\"price\": 45, \r\n"
				+ "\"copies\": 10 \r\n" + "\r\n" + "},\r\n" + "{ \r\n" + "\r\n"
				+ "\"title\": \"Rest API Automation\", \r\n" + "\"price\": 55, \r\n" + "\"copies\": 15 \r\n" + "\r\n"
				+ "} \r\n" + "\r\n" + "] \r\n" + "\r\n" + "} ";
	}

	public static String AddBook(String isbn, String aisle) {

		return "{\r\n" + "\r\n" + "\"name\":\"REST API Automation\",\r\n" + "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n" + "\"author\":\"Khadar Sk\"\r\n" + "}\r\n" + "";
		
	}

}
