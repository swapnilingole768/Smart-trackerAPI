package DistributedAdmin;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.Assert;
import org.testng.annotations.Test;

import Auth.Authentication;
import io.restassured.response.Response;

public class Create_company_CRUD {

	String companyLocationId;
	Authentication auth = new Authentication("Shubhangi", "e%>xp2)fdJMH>bQ8", 3);

	@Test
	public void distributorAdminLoginTest() {
		auth.superAdminLoginTest();
	}

	@Test(priority = 1, description = "Get Company Locations", dependsOnMethods = { "distributorAdminLoginTest" })
	public void getCompanyLocations() {

		io.restassured.response.Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/companies/:/company-locations");

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

	@Test(priority = 2, description = "Create Location", dependsOnMethods = { "distributorAdminLoginTest" })
	public void createCompanyLocationTest() {

		 Map<String, String> fields = new HashMap<>();

		    fields.put("name", " Godown");
		    fields.put("address", "Kalyani nagar");
		    fields.put("city", "Pune");
		    fields.put("state", "Maharashtra");
		    fields.put("pincode", "411014");

		    Response response = given()
		            .cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
		            .contentType("application/json")
		            .body(fields)
		            .when()
		            .post(auth.API_KEY + "/companies/:/company-locations")
		            .then()
		            .statusCode(201) // Assuming successful creation returns 201 OK
		            .log().all()
		            .extract().response();

		    // Extracting the company_location_id correctly
		    Object companyLocationIdObj = response.path("result.company_location_id");
		    if (companyLocationIdObj instanceof Integer) {
		        companyLocationId = String.valueOf(companyLocationIdObj);
		    } else if (companyLocationIdObj instanceof String) {
		        companyLocationId = (String) companyLocationIdObj;
		    } else {
		        throw new ClassCastException("Unexpected type for company_location_id: " + companyLocationIdObj.getClass().getName());
		    }
	}

	@Test(priority = 3, description = "Get Company Locations By ID", dependsOnMethods = { "distributorAdminLoginTest" })
	public void getCompanyLocationsById() {

		String company_id = "40";
		String company_location_id = "50";

		io.restassured.response.Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when()
				.get(auth.API_KEY + "/companies/" + company_id + "/company-locations/" + company_location_id);

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

	@Test(priority = 4, description = "Update Location For Company", dependsOnMethods = { "distributorAdminLoginTest" })
	public void updateCompanyLocationByIdTest() {

		String company_id = "40";
		String company_location_id = "49";

		Map<String, String> fields = new HashMap<>();

		fields.put("name", "sasdas");
		fields.put("address", "shdash");
		fields.put("city", "pune");
		fields.put("state", "Maharashtra");
		fields.put("pincode", "335263");

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").body(fields).when()
				.put(auth.API_KEY + "/companies/" + company_id + "/company-locations/" + company_location_id).then()
				.statusCode(200) // Assuming successful creation returns 201 OK
				.log().all();
	}

	@Test(enabled = false, priority = 5, description = " Delete Location", dependsOnMethods = {
			"distributorAdminLoginTest" })
	public void DeleteDistributorUserById() {
		String company_id = "40";
		String company_location_id = "49";

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when()
				.delete(auth.API_KEY + "/companies/" + company_id + "/company-locations/" + company_location_id).then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
		auth.logoutTest();

	
	}
}
