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
import io.restassured.response.Response;

public class Refiller_Crud {
	
	String company_id;
	Credentials cred = new Credentials();
	Authentication auth = new Authentication("Shubhangi", "e%>xp2)fdJMH>bQ8", 3);
	
	@Test
	public void distributorAdminLoginTest() {
		auth.superAdminLoginTest();
	}	
	 @Test(priority = 2, description = "Create Location", dependsOnMethods = { "distributorAdminLoginTest" })
	    public void createCompanyLocationTest() {

	        Map<String, String> fields = new HashMap<>();
	        fields.put("company_name", "Godown");
	        fields.put("company_address", "Kalyani nagar");
	        fields.put("company_city", "Pune");
	        fields.put("company_state", "Maharashtra");
	        fields.put("company_pincode", "411014");
	        fields.put("company_email", cred.getEmail());
	        fields.put("company_phone", cred.getPhone());
	        fields.put("company_gstin", cred.getGst());
	        fields.put("company_pan", cred.getPan());

	        Response response = given()
	                .cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
	                .contentType("application/json")
	                .body(fields)
	                .when()
	                .post(auth.API_KEY + "/refillers/company");

	        // Log the response body to understand why the request failed
	        response.then().log().all();

	        // Check the status code
	        int statusCode = response.getStatusCode();
	        if (statusCode != 201) {
	            throw new AssertionError("Expected status code <201> but was <" + statusCode + ">. Response body: " + response.getBody().asString());
	        }

	        // Extracting the company_location_id correctly
	        try {
	            Object companyLocationIdObj = response.path("result.company_id");
	            if (companyLocationIdObj == null) {
	                throw new NullPointerException("company_id is null. Response body: " + response.getBody().asString());
	            } else if (companyLocationIdObj instanceof Integer) {
	                company_id = String.valueOf(companyLocationIdObj);
	            } else if (companyLocationIdObj instanceof String) {
	                company_id = (String) companyLocationIdObj;
	            } else {
	                throw new ClassCastException("Unexpected type for company_location_id: " + companyLocationIdObj.getClass().getName());
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to extract company_location_id. Response body: " + response.getBody().asString(), e);
	        }
	    }
	    @Test(priority = 3, description = "Get Location", dependsOnMethods = { "createCompanyLocationTest" })
	    public void getCompanyLocationTest() {
	    	io.restassured.response.Response response = given() // Include JWT token in the request header
					.cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
					.contentType("application/json").when().get(auth.API_KEY + "/refillers/company");

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
}
