package DistributorUser;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Auth.Authentication;

public class DistributorUserAccess {

	Authentication auth = new Authentication("SwapnilQa", "User@123", 4);

	@Test
	public void distributorUserLoginTest() {
		auth.superAdminLoginTest();
	}

	// ______________GET CYLINDER Sizes_______________
	@Test(priority = 1, description = "All Cylinder Size List", dependsOnMethods = { "distributorUserLoginTest" })
	public void getAllCylinderSize() {

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/cylinders/cylinder-sizes/").then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}

	@Test(priority = 2, description = "All Cylinder Size By ID", dependsOnMethods = { "distributorUserLoginTest" })
	public void getCylinderSizeById() {
		int CylinderId = 9;

		 given()
	        .cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
	        .contentType("application/json")
	    .when()
	        .get(auth.API_KEY + "/cylinders/cylinder-sizes/" + CylinderId)
	    .then()
	        .statusCode(200) // Assuming successful retrieval returns 200 OK
	        .log().all();
	}
	// _______________________GET CYLINDER GAS______________________

	@Test(priority = 3, description = "All Cylinder Gas List", dependsOnMethods = { "distributorUserLoginTest" })
	public void getAllCylinderGas() {

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/cylinders/cylinder-gases").then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}

	@Test(priority = 4, description = "Cylinder Gas By ID", dependsOnMethods = { "distributorUserLoginTest" })
	public void getCylinderGasById() {
		int Id = 7;
		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		//requestBody.put("id", "16"); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().get(auth.API_KEY + "/cylinders/cylinder-gases/" +Id).then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}
	// ______________________Get Cylinders__________________

	@Test(priority = 5, description = "Get Company All Cylinder", dependsOnMethods = { "distributorUserLoginTest" })
	public void getCompanyCylinder() {

		io.restassured.response.Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/cylinders");

		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonObject json = jsonParser.parse(responseBody).getAsJsonObject();
		String formattedResponse = gson.toJson(json);

		if (statusCode == 200) {
			System.out.println("\nResponse Body: \n" + formattedResponse + "\n");
		} else if (statusCode == 404) {
			System.out.println("\nResource Not Found: " + formattedResponse + "\n");
		} else {
			System.out.println("\nUnexpected Response: " + formattedResponse + "\n");
		}
		Assert.assertTrue(statusCode == 200 || statusCode == 404, "Unexpected status code: " + statusCode);
	}

	@Test(priority = 6, description = "Get Company Cylinder By Id", dependsOnMethods = { "distributorUserLoginTest" })
	public void getCompanyCylinderById() {
		String cylinder_id = "58";

		io.restassured.response.Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/cylinders/" + cylinder_id);

		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		@SuppressWarnings("deprecation")
		JsonParser jsonParser = new JsonParser();
		@SuppressWarnings("deprecation")
		JsonObject json = jsonParser.parse(responseBody).getAsJsonObject();
		String formattedResponse = gson.toJson(json);

		if (statusCode == 200) {
			System.out.println("\nResponse Body: \n" + formattedResponse + "\n");
		} else if (statusCode == 404) {
			System.out.println("\nResource Not Found: " + formattedResponse + "\n");
		} else {
			System.out.println("\nUnexpected Response: " + formattedResponse + "\n");
		}
		Assert.assertTrue(statusCode == 200 || statusCode == 404, "Unexpected status code: " + statusCode);
		auth.logoutTest();
	}

}
