package SuperAdmin;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import Auth.Authentication;
import Auth.Credentials;
import io.restassured.response.Response;

public class Distributor_Admin_Crud {

	Authentication auth = new Authentication("rupam", "Rupam@123", 2);
	Credentials cred = new Credentials();
	int userId;
	int companyUserId;

	@Test
	public void superAdminLoginTest() {
		auth.superAdminLoginTest();
	}

	@Test(priority = 1, description = "Call API with Token")
	public void createDistributorAdminTest() {
		auth.superAdminLoginTest();

		Map<String, String> fields = new HashMap<>();

		fields.put("username", cred.getUsername());
		// company_user
		fields.put("company_user_name", cred.getUsername() + "_Company");
		fields.put("company_user_email", cred.getEmail());
		fields.put("company_user_phone", cred.getPhone());
		fields.put("company_user_pan", cred.getPan());

		// company
		fields.put("company_name", cred.getUsername() + "_interprise ");
		fields.put("company_address", "some address");
		fields.put("company_city", "Babdhan");
		fields.put("company_state", "Pune");
		fields.put("company_pincode", "411038");

		// company_contact
		fields.put("company_email", "At" + cred.getEmail());
		fields.put("company_phone", cred.getPhone());
		fields.put("company_gstin", cred.getGst());
		fields.put("company_pan", cred.getPan());

		Response response = given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").body(fields).when().post(auth.API_KEY + "/distributors/admin").then()
				.log().all().extract().response();
		userId = response.path("result.user_id");
		companyUserId = response.path("result.company_user_id");

	}

	@Test(priority = 2, description = "All Distributor Admins List", dependsOnMethods = { "superAdminLoginTest" })
	public void getAlldistributorAdmins() {

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/distributors/admin").then().statusCode(200) // Assuming
																															// successful
																															// creation
																															// returns
																															// 200
																															// OK
				.log().all();
	}

	@Test(priority = 3, description = " Distributor admin By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void getDistributorAdminById() {
		String company_user_id = String.valueOf(companyUserId);

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/distributors/admin/" + company_user_id)
				.then().statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}

	@Test(priority = 4, description = "Update distributor admin By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void updateDistributorAdminById() {
		String company_user_id = String.valueOf(companyUserId);
		;

		Map<String, String> fields = new HashMap<>();

		fields.put("username", cred.getUsername());
		// company_user
		fields.put("company_user_name", cred.getUsername() + "_CompanyUpdate");
		fields.put("company_user_email", cred.getEmail());
		fields.put("company_user_phone", cred.getPhone());
		fields.put("company_user_pan", cred.getPan());

		// company
		fields.put("company_name", cred.getUsername() + "_interprise ");
		fields.put("company_address", "some address");
		fields.put("company_city", "Babdhan");
		fields.put("company_state", "Pune");
		fields.put("company_pincode", "411038");

		// company_contact
		fields.put("company_email", "At" + cred.getEmail());
		fields.put("company_phone", cred.getPhone());
		fields.put("company_gstin", cred.getGst());
		fields.put("company_pan", cred.getPan());

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(fields).when().put(auth.API_KEY + "/distributors/admin/" + company_user_id).then().statusCode(200) // Assuming
																															// successful
																															// creation
																															// returns
																															// 200
																															// OK
				.log().all();
	}

	@Test(priority = 5, description = " Distributor admin By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void DeleteDistributorAdminById() {
		String company_user_id = String.valueOf(companyUserId);
		;

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().delete(auth.API_KEY + "/distributors/admin/" + company_user_id)
				.then().statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
		auth.logoutTest();

	}
}
