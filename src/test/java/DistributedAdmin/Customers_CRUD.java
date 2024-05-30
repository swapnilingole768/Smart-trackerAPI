package DistributedAdmin;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import Auth.Authentication;
import Auth.Credentials;
import io.restassured.response.Response;

public class Customers_CRUD {

    Credentials cred = new Credentials();
    String companyLocationId;
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
                .post(auth.API_KEY + "/customers/company");

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
                companyLocationId = String.valueOf(companyLocationIdObj);
            } else if (companyLocationIdObj instanceof String) {
                companyLocationId = (String) companyLocationIdObj;
            } else {
                throw new ClassCastException("Unexpected type for company_location_id: " + companyLocationIdObj.getClass().getName());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract company_location_id. Response body: " + response.getBody().asString(), e);
        }
    }
    @Test(priority = 3, description = "Update Location", dependsOnMethods = { "createCompanyLocationTest" })
    public void updateCompanyLocationTest() {

        Map<String, String> updatedFields = new HashMap<>();
        updatedFields.put("company_name", cred.getUpdatedGodown());
        updatedFields.put("company_address", cred.getGasSize() +"Updated Kalyani nagar");
        updatedFields.put("company_city", "Updated Pune");
        updatedFields.put("company_state", "Maharashtra");
        updatedFields.put("company_pincode", "411014");
        updatedFields.put("company_email", cred.getEmail());
        updatedFields.put("company_phone", cred.getPhone());
        updatedFields.put("company_gstin", cred.getGst());
        updatedFields.put("company_pan", cred.getPan());

        Response response = given()
                .cookie("jwt", auth.JWT_COOKIE) // Include JWT token in Cookie header
                .contentType("application/json")
                .body(updatedFields)
                .when()
                .put(auth.API_KEY + "/customers/company/" + companyLocationId);

        // Log the response body to understand why the request failed
        response.then().log().all();

        // Check the status code
        int statusCode = response.getStatusCode();
        if (statusCode != 200) { // Assuming successful update returns 200 OK
            throw new AssertionError("Expected status code <200> but was <" + statusCode + ">. Response body: " + response.getBody().asString());
        }
    }
}
