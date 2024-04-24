package e2eEommerceApiTests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

public class EndToEndTest {

	public static void main(String[] args) {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginReq = new LoginRequest();
		loginReq.setUserEmail("skn@yopmail.com");
		loginReq.setUserPassword("Khadhar@0000");

		// In order to bypass SSL certificate, we should use relaxedHTTPSValidation()
		RequestSpecification requestLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginReq);

		LoginResponse loginResponse = requestLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);

		String userId = loginResponse.getUserId();
		System.out.println("The userId extracted from the response is: " + userId);

		String token = loginResponse.getToken();
		System.out.println("The token extracted from the response is: " + token);

		// Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
				.param("productName", "Dell Laptop").param("productAddedBy", userId)
				.param("productCategory", "Electronics").param("productSubCategory", "Laptops")
				.param("productPrice", "50000").param("productDescription", "DELL")
				.param("productFor", "It Professionals")
				.multiPart("productImage", new File("C://Users//khadh//Downloads//laptop.png"));

		String addProducResponse = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
				.statusCode(201).extract().response().asString();

		// Instead of using Response spec builder we use Json path to parse the response
		// as this is not a complex json
		JsonPath js = new JsonPath(addProducResponse);
		String productId = js.get("productId");

		System.out.println("The productId is: " + productId);

		// Place Order
		RequestSpecification placeOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		// Creating an object for OrderDetails class to set values
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);

		List<OrderDetails> OrderDetailList = new ArrayList<OrderDetails>();
		OrderDetailList.add(orderDetails);
		Orders orders = new Orders();
		orders.setOrders(OrderDetailList);

		RequestSpecification createOrderBaseRequest = given().log().all().spec(placeOrderBaseReq).body(orders);

		String createOrderResponse = createOrderBaseRequest.when().post("/api/ecom/order/create-order").then().log()
				.all().extract().response().asString();

		
		System.out.println(createOrderResponse);
		JsonPath js0 = new JsonPath(createOrderResponse);
		String orderId = js0.getString("orders[0]");
        System.out.println("Order ID: " + orderId);

		// Delete created Order
		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification deleteProd = given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);

		String deleteProdResponse = deleteProd.when().delete("/api/ecom/product/delete-product/{productId}").then()
				.log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(deleteProdResponse);
		String deleteProdMessage = js1.get("message");

		Assert.assertEquals("Product Deleted Successfully", deleteProdMessage);

		// Delete placed Order
		RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification deleteOrder = given().log().all().spec(deleteOrderBaseReq).pathParam("orderId", orderId);

		String deleteOrderResponse = deleteOrder.when()
				.delete("/api/ecom/order/delete-order/{orderId}").then().log().all().extract().response().asString();

		JsonPath js2 = new JsonPath(deleteOrderResponse);
		String deleteOrderMessage = js2.get("message");
		Assert.assertEquals("Orders Deleted Successfully", deleteOrderMessage);

	}

}
