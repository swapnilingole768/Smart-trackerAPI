package DistributedAdmin;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import Auth.Authentication;
import Auth.Credentials;
import io.restassured.response.Response;

public class Distributor_User_CRUD {

	int company_id;
	Credentials cred = new Credentials();
	Authentication auth = new Authentication("Shubhangi", "e%>xp2)fdJMH>bQ8", 3);

	@Test
	public void distributorAdminLoginTest() {
		auth.superAdminLoginTest();
	}

	@Test(priority = 1, description = "Create Distributor User", dependsOnMethods = { "distributorAdminLoginTest" })
	public void createDistributorUserTest() {

		Map<String, String> fields = new HashMap<>();

		fields.put("username", cred.getUsername());
		fields.put("password", cred.getUsername() + "@123");
		// company_user
		fields.put("company_user_name", cred.getUsername() + "sg");
		fields.put("company_user_email", cred.getEmail());
		fields.put("company_user_phone", cred.getPhone());
		fields.put("company_user_pan", cred.getPan());

		Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").body(fields).when().post(auth.API_KEY + "/distributors/user/").then()
				.statusCode(201) // Assuming successful creation returns 201 OK
				.log().all().extract().response();

		company_id = response.path("result.company_user_id");

	}

	@Test(priority = 2, description = "Distributor User By ID", dependsOnMethods = { "distributorAdminLoginTest" })
	public void getDistributorUserById() {

		String company_id = String.valueOf(this.company_id);
		io.restassured.response.Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/distributors/user/" + company_id);

		int statusCode = response.getStatusCode();
		String responseBody = response.getBody().asString();

		if (statusCode == 200) {
			System.out.println("\nResponse Body: " + responseBody);
		} else if (statusCode == 404) {
			System.out.println("\nResource Not Found: " + responseBody);
		} else {
			System.out.println("\nUnexpected Response: " + responseBody);
		}
		Assert.assertTrue(statusCode == 200 || statusCode == 404, "Unexpected status code: " + statusCode);
	}

	@Test(priority = 3, description = "Update Distributor User", dependsOnMethods = { "distributorAdminLoginTest" })
	public void updateDistributorUserTest() {

		String company_id = String.valueOf(this.company_id);
		auth.superAdminLoginTest();
		Map<String, String> fields = new HashMap<>();

		fields.put("username", "Atuljhjh");
		fields.put("password", "Atul@123588");

		// company_user
		fields.put("company_user_name", "Atulj123 Lokhande");
		fields.put("company_user_email", "swapnil.ingole184@gmail.com");
		fields.put("company_user_phone", "9850564340");
		fields.put("company_user_pan", "AKMWE12" + "40" + "A");

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").body(fields).when()
				.put(auth.API_KEY + "/distributors/user/" + company_id).then().statusCode(200) // Assuming successful
																								// creation returns 201
																								// OK
				.log().all();
	}

	@Test(priority = 4, description = " Distributor User By ID", dependsOnMethods = { "distributorAdminLoginTest" })
	public void DeleteDistributorUserById() {
		String company_id = String.valueOf(this.company_id);

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().delete(auth.API_KEY + "/distributors/user/" + company_id).then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
		auth.logoutTest();
	}

}
