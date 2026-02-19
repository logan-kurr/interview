package test.tests.apiTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.helper.DateHelper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static test.pages.HomePage.HOME_PAGE_URL;


public class FullBookingAPIFlow {

    private final String BASE_URL = "https://restful-booker.herokuapp.com";
    private final String ADMIN_URL = "https://automationintesting.online/admin";

    String authToken;
    int bookingID;

    String firstName = "John";
    String lastName = "Smith";
    int totalPrice = 250;
    boolean depositPaid = true;
    String checkInDate = "";
    String checkOutDate = "";
    String additionalNeeds = "Late checkout please";

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
                .post(BASE_URL + "/auth");

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
        bookingJSONObj.put("firstname", firstName);
        bookingJSONObj.put("lastname", lastName);
        bookingJSONObj.put("totalprice", totalPrice);
        bookingJSONObj.put("depositpaid", depositPaid);

        checkInDate = DateHelper.getFutureDate(DateHelper.getCurrentDate(), 7);
        checkOutDate = DateHelper.getFutureDate(DateHelper.getCurrentDate(), 7);
        JSONObject datesJsonObj = new JSONObject();
        datesJsonObj.put("checkin", checkInDate);
        datesJsonObj.put("checkout", checkOutDate);

        bookingJSONObj.put("bookingdates", datesJsonObj);
        bookingJSONObj.put("additionalneeds", additionalNeeds);

        Response response = given()
                .contentType(ContentType.JSON)
                .cookie("token", authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingJSONObj.toString())
                .when()
                .post(BASE_URL + "/booking");

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
                        .get(BASE_URL + "/booking/" + bookingID);

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("firstname", equalTo(firstName))
                .body("lastname", equalTo(lastName))
                .body("totalprice", equalTo(totalPrice))
                .body("depositpaid", equalTo(depositPaid))
                .body("bookingdates.checkin", equalTo(checkInDate))
                .body("bookingdates.checkout", equalTo(checkOutDate))
                .body("additionalneeds", equalTo(additionalNeeds));
    }

    @Test(description = "PUT | Updates an existing Booking", dependsOnMethods = "testRetrieveBooking")
    public void testUpdateBooking() {

        // updates dates and additional request
        checkInDate = DateHelper.getFutureDate(DateHelper.getCurrentDate(), 14);
        checkOutDate = DateHelper.getFutureDate(DateHelper.getCurrentDate(), 15);
        JSONObject datesJsonObj = new JSONObject();
        datesJsonObj.put("checkin", checkInDate);
        datesJsonObj.put("checkout", checkOutDate);
        additionalNeeds = "Apologies can I get a drink package as well!";
        bookingJSONObj.put("bookingdates", datesJsonObj);
        bookingJSONObj.put("additionalneeds", additionalNeeds);

        Response response = given()
                .contentType(ContentType.JSON)
                .cookie("token", authToken)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingJSONObj.toString())
                .when()
                .put(BASE_URL + "/booking/" + bookingID);

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("bookingdates.checkin", equalTo(checkInDate))
                .body("bookingdates.checkout", equalTo(checkOutDate))
                .body("additionalneeds", equalTo(additionalNeeds));
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
                        .delete(BASE_URL + "/booking/" + bookingID);

        response.then()
                .log()
                .all()
                .assertThat()
                .statusCode(201);
    }

    /**
     * || BONUS BONUS BONUS ||
     */
    @Test(description = "Verifies Admin Authentication", dependsOnMethods = "testDeleteBooking")
    public void testAdminAuthentication() {

        // spins up new WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(HOME_PAGE_URL);

        // sets authentication cookie
        driver.manage().addCookie(new Cookie("token", authToken));

        // navigates to Admin page
        driver.get(ADMIN_URL);

        // TODO: Add ADMIN Page Verifications

        // ensures proper closure/cleanup of WebDriver
        driver.close();
        driver.quit();
    }
}
