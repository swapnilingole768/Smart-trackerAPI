package SuperAdmin;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import Auth.Authentication;
import Auth.Credentials;

public class Cylinder_CRUD {
	Authentication auth = new Authentication("rupam", "Rupam@123", 2);
	Credentials cred = new Credentials();

	@Test
	public void superAdminLoginTest() {
		auth.superAdminLoginTest();
	}

	@Test(priority = 1, description = "Call API without Token")
	public void createCylinderSizeTestwithToken() {
		auth.superAdminLoginTest();
		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", cred.getGasSize()); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.contentType("application/json").body(requestBody).when()
				.post(auth.API_KEY + "/cylinders/cylinder-sizes").then().statusCode(401); // Assuming successful
																							// creation returns 200 OK
	}

	@Test(priority = 2, description = "Call API with Token", dependsOnMethods = { "superAdminLoginTest" })
	public void createCylinderSizeTest() {

		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", cred.getGasSize()); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().post(auth.API_KEY + "/cylinders/cylinder-sizes").then().statusCode(201) 
				.log().all();
	}

	@Test(priority = 3, description = "All Cylinder List")
	public void getAllCylinder() {

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/cylinders/cylinder-sizes/").then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}

	@Test(priority = 4, description = "All Cylinder By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void getCylinderById() {
		int CylinderId = 29;
		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		//requestBody.put("name", "xl1"); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().get(auth.API_KEY + "/cylinders/" + CylinderId).then()
				.statusCode(403) // Assuming super admin should not have access to get cylinder data
				.log().all();
	}

	@Test(priority = 5, description = "Update Cylinder By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void updateCylinderById() {
		int CylinderId = 55;
		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("name", "nbnv"); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().put(auth.API_KEY + "/cylinders/cylinder-sizes/" + CylinderId).then()
				.statusCode(200) // Assuming successful creation returns 200 OK and superadmin dont have access to update cylinder
				.log().all();
	}

	// _______________________Cylinder Gas Create , Update,
	// Get_________________________________

	@Test(priority = 6, description = "Create Gas API", dependsOnMethods = { "superAdminLoginTest" })
	public void createCylinderGasTest() {
		auth.superAdminLoginTest();

		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("gas_name", cred.getGasName()); // Replace with actual cylinder size name
		requestBody.put("gas_symbol", cred.getGasSymbol()); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().post(auth.API_KEY + "/cylinders/cylinder-gases").then().statusCode(201) // Assuming
																													// successful
																													// creation
																													// returns
																													// 200
																													// OK
				.log().all();
	}

	@Test(priority = 7, description = "All Cylinder Gas List", dependsOnMethods = { "superAdminLoginTest" })
	public void getAllCylinderGas() {

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json").when().get(auth.API_KEY + "/cylinders/cylinder-gases").then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}

	@Test(priority = 8, description = "Cylinder Gas By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void getCylinderGasById() {
		int CylinderId = 23;
		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("id", "16"); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().get(auth.API_KEY + "/cylinders/cylinder-gases/" + CylinderId).then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
	}

	@Test(priority = 9, description = "Update Cylinder Gas By ID", dependsOnMethods = { "superAdminLoginTest" })
	public void updateCylinderGasById() {
		Credentials cred2 = new Credentials();

		int CylinderId = 17;
		// Define the request body for creating a cylinder size
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("gas_name", cred2.getGasName()); // Replace with actual cylinder size name
		requestBody.put("gas_symbol", cred2.getGasSymbol()); // Replace with actual cylinder size name

		given() // Include JWT token in the request header
				.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
				.contentType("application/json")

				.body(requestBody).when().put(auth.API_KEY + "/cylinders/cylinder-gases/" + CylinderId).then()
				.statusCode(200) // Assuming successful creation returns 200 OK
				.log().all();
		auth.logoutTest();
	}

}
