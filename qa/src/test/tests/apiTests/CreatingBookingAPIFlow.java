package test.tests.apiTests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CreatingBookingAPIFlow {

    String baseURL = "https://restful-booker.herokuapp.com";
    String authToken;
    int bookingID;

    JSONObject bookingJSONObj;

    @BeforeClass(description = "Retrieve Auth Token")
    public void getAuthToken() {

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", "admin");
        jsonObj.put("password", "password123");

        Response response = given()
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

        bookingJSONObj = new JSONObject();
        bookingJSONObj.put("firstname", "John");
        bookingJSONObj.put("lastname", "Smith");
        bookingJSONObj.put("totalprice", 250);
        bookingJSONObj.put("depositpaid", true);
            JSONObject datesJsonObj = new JSONObject();
            datesJsonObj.put("checkin", "2026-04-10");
            datesJsonObj.put("checkout", "2026-04-11");
        bookingJSONObj.put("bookingdates", datesJsonObj);
        bookingJSONObj.put("additionalneeds", "Late checkout please");

        Response response = given()
                .contentType(ContentType.JSON)
                .cookie("token", authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingJSONObj.toString())
                .when()
                .post(baseURL + "/booking");

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
        bookingID = response.body().jsonPath().getInt("bookingid");
    }

    @Test(description = "GET | Retrieves specified Booking Info", dependsOnMethods = "testCreateBooking")
    public void testRetrieveBooking() {

        Response response =
                given()
                .contentType(ContentType.JSON)
                .cookie("token", authToken)
                .header("Accept", "application/json")
                .when()
                .get(baseURL + "/booking/" + bookingID);

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo("John"));
    }

    @Test(description = "PUT | Updates an existing Booking", dependsOnMethods = "testRetrieveBooking")
    public void testUpdateBooking() {

        // updates dates and additional request
        JSONObject datesJsonObj = new JSONObject();
        datesJsonObj.put("checkin", "2026-04-17");
        datesJsonObj.put("checkout", "2026-04-18");
        bookingJSONObj.put("bookingdates", datesJsonObj);
        bookingJSONObj.put("additionalneeds", "Apologies can I get a drink package as well");

        Response response = given()
                .contentType(ContentType.JSON)
                .cookie("token", authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingJSONObj.toString())
                .when()
                .put(baseURL + "/booking/" + bookingID);

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);
    }

    @Test(description = "DELETE | Deletes a Booking", dependsOnMethods = "testUpdateBooking")
    public void testDeleteBooking() {

        // deletes booking
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .cookie("token", authToken)
                        .header("Accept", "application/json")
                        .when()
                        .delete(baseURL + "/booking/" + bookingID);

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
    }
}
