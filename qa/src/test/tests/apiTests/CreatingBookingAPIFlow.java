package test.tests.apiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class CreatingBookingAPIFlow {

    String baseURL = "https://restful-booker.herokuapp.com";
    String authToken;

    @BeforeClass(description = "Retrieve Auth Token")
    public void getAuthToken() {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", "admin");
        jsonObj.put("password", "password123");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonObj.toString())
                .when()
                .post(baseURL + "/auth");

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
        authToken = response.body().jsonPath().getString("token");
    }

    @Test(description = "POST | Creates a Booking")
    public void testCreateBooking() {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("firstname", "John");
        jsonObj.put("lastname", "Smith");
        jsonObj.put("totalprice", 250);
        jsonObj.put("depositpaid", true);
            JSONObject datesJsonObj = new JSONObject();
            datesJsonObj.put("checkin", "2026-04-10");
            datesJsonObj.put("checkout", "2026-04-11");
        jsonObj.put("bookingdates", datesJsonObj);
        jsonObj.put("additionalneeds", "Late checkout please");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie("token", authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonObj.toString())
                .when()
                .post(baseURL + "/booking");

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }
}
