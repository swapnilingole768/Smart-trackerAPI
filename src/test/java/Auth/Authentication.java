package Auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class Authentication {

	Map<String, String> credentials = new HashMap<>();
	String ACCESS_TOKEN;
	public String JWT_COOKIE;

	String Response_Message;
	public String API_KEY = "https://stdev.appwizersolutions.com/api";
	int ADMIN_ROLE_ID = 2;

	public Authentication() {
		super();
	}

	public Authentication(String username, String pass, int role_id) {
		credentials.put("username", username);
		credentials.put("password", pass);
		this.ADMIN_ROLE_ID = role_id;
	}

	@Test(priority = 1, description = "Valid Credentials Test")
	public void superAdminLoginTest() {
		long startTime = System.currentTimeMillis();

		io.restassured.response.Response response = given().contentType("application/json").body(credentials).when()
				.post(API_KEY + "/auth/login").then().statusCode(200)
				.body("result.user.role_id", equalTo(ADMIN_ROLE_ID)) // Validate that the returned user ID matches the
																		// requested ID
				.log().all().header("Set-Cookie", containsString("jwt=")) // Assert cookie is received
				.extract().response(); // Extract the response

		// Save the JWT cookie
		JWT_COOKIE = response.getCookie("jwt");
		ACCESS_TOKEN = response.getSessionId();

		System.out.println("JWT_COOKIE : " + JWT_COOKIE + "");

		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.println("Response Time: " + responseTime + " milliseconds");

	}

	@Test(priority = 2, description = "Invalid Credentials Test")
	public void invalidCredentialsTest() {
		String message = "Invalid email or password.";
		Map<String, String> credentials = new HashMap<>();
		credentials.put("username", " "); // Replace with invalid username
		credentials.put("password", " "); // Replace with invalid password

		long startTime = System.currentTimeMillis();

		given().contentType("application/json").body(credentials).when().post(API_KEY + "/auth/login").then()
				.body("message", equalTo(message)) // Validate that the returned user ID matches the requested ID
				.statusCode(400); // Assuming 401 Unauthorized status code for invalid credentials

		long endTime = System.currentTimeMillis();
		long responseTime = endTime - startTime;
		System.out.println("Response Time: " + responseTime + " milliseconds");
	}

	@Test(priority = 3, dependsOnMethods = { "superAdminLoginTest" })
	public void logoutTest() {
		// Make sure to replace ACCESS_TOKEN with the actual access token obtained from
		// the login test
		given().header("Authorization", "Bearer " + ACCESS_TOKEN).when().post(API_KEY + "/auth/logout").then()
				.statusCode(200); // Assuming successful logout returns 200 OK
	}

}
