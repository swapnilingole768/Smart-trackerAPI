package DistributedAdmin;

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
import Auth.Credentials;

public class Create_Cylinder_CRUD {
	Authentication auth = new Authentication("Shubhangi", "e%>xp2)fdJMH>bQ8", 3);
	Credentials cred = new Credentials();

	@Test
	public void distributorAdminLoginTest() {
		auth.superAdminLoginTest();
	}

	@Test(priority = 1, description = "Get Company All Cylinder", dependsOnMethods = { "distributorAdminLoginTest" })
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

	@Test(priority = 2, description = "Get Company Cylinder By Id", dependsOnMethods = { "distributorAdminLoginTest" })
	public void getCompanyCylinderById() {
		String cylinder_id = "51";

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
	}

	@Test(priority = 3, description = "Create Cylinder", dependsOnMethods = { "distributorAdminLoginTest" })
	public void createCompanyCylinderTest() {

		Map<String, Object> cylinderData = new HashMap<>();

		// Add data to the HashMap
		// cylinder_masters
		cylinderData.put("gas_id", 23);
		cylinderData.put("size_id", 4);
		cylinderData.put("cylinder_code", cred.getGasName() + "code1");
		cylinderData.put("cylinder_qr_code", cred.getGasName() + "code1");
		cylinderData.put("status", "WITH_CUSTOMER");
		cylinderData.put("is_filled", false);
		cylinderData.put("current_company_id", 42);
		cylinderData.put("current_location_id", 51);

		// cylinder_details
		cylinderData.put("manufacture_serial_number", "DXDCFSFCFCS");
//	         cylinderData.put("manufacture_date", "2024-04-20");
//	         cylinderData.put("certification_issue_date", "2024-04-20");
//	         cylinderData.put("certification_expiry_date", "2024-04-20");
//	         cylinderData.put("last_hydraulic_test_date", "2024-04-20");
//	         cylinderData.put("next_hydraulic_test_date", "2024-04-20");
//	         cylinderData.put("hydraulic_pressure_expiry_date", "2024-04-20");

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").body(cylinderData).when().post(auth.API_KEY + "/cylinders").then()
				.statusCode(201) // Assuming successful creation returns 201 OK
				.log().all();
	}

	@Test(priority = 4, description = "Update Cylinder", dependsOnMethods = { "distributorAdminLoginTest" })
	public void updateCompanyLocationByIdTest() {

		String cylinder_id = "56";

		Map<String, Object> cylinderData = new HashMap<>();

		// cylinder_masters
	//	cylinderData.put("gas_id", 11);
	//	cylinderData.put("size_id", 10);
		cylinderData.put("cylinder_code", 106);
		cylinderData.put("cylinder_qr_code", 106);
		cylinderData.put("status", "AT_GODOWN");
		cylinderData.put("is_filled", true);
	//	cylinderData.put("current_company_id", 40);
	//	cylinderData.put("current_location_id", 50);

		// cylinder_details
	//	cylinderData.put("manufacture_serial_number", " ");
		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").body(cylinderData).when()
				.put(auth.API_KEY + "/cylinders/" + cylinder_id).then().statusCode(200) // Assuming successful creation
																						// returns 201 OK
				.log().all();
		
	}

	@Test(priority = 5, description = " Delete Location", dependsOnMethods = { "distributorAdminLoginTest" })
	public void DeleteDistributorUserById() {
		String cylinder_id = "77";

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().delete(auth.API_KEY + "/cylinders/" + cylinder_id).then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
		auth.logoutTest();

	}
}
