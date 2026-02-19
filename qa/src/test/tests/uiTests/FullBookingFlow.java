package test.tests.uiTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import test.helper.DateHelper;
import test.model.StayDetails;
import test.services.HomePageService;
import org.openqa.selenium.WebDriver;
import test.services.ReservationPageService;


public class FullBookingFlow  {

    private final HomePageService homePageService = new HomePageService();
    private final ReservationPageService reservationPageService = new ReservationPageService();

    private final String FIRST_NAME = "John";
    private final String LAST_NAME = "Smith";
    private final String EMAIL = "jsmith@gmail.com";
    private final String PHONE = "555-111-2222";

    private StayDetails stayDetails;
    private WebDriver driver;

    @BeforeClass(description = "Driver Startup")
    public void startUp(@Optional("SINGLE") String roomType) {
        // spins up new WebDriver
        WebDriverManager.chromedriver().setup();
        driver  = new ChromeDriver();

        // generate stayDetails obj
        String checkInDate = DateHelper.getFutureDate(DateHelper.getCurrentDate(), 7);
        String checkOutDate = DateHelper.getFutureDate(DateHelper.getCurrentDate(), 8);
        stayDetails = new StayDetails(StayDetails.RoomType.valueOf(roomType), checkInDate, checkOutDate,
                FIRST_NAME, LAST_NAME, EMAIL, PHONE);
    }


    @Test(description = "Select Room Type")
    public void testSelectRoomType() {

        homePageService.stepNavigateAndVerifyHomePage(driver);
        homePageService.stepSelectRoomType(driver, stayDetails);
    }

    @Test(description = "Select Check-In/Check-Out Dates", dependsOnMethods = "testSelectRoomType")
    public void testSelectStayDates() {

        reservationPageService.stepVerifyReservationPage(driver, stayDetails.getRoomType());
        reservationPageService.stepReserveAvailableDates(driver, stayDetails);
    }

    @Test(description = "Enter Guest Details and Submit", dependsOnMethods = "testSelectStayDates")
    public void testEnterGuestDetailsAndSubmit() {

        reservationPageService.stepEnterUserDetailsAndSubmit(driver, stayDetails);
    }

    @AfterClass(description = "Driver Cleanup")
    public void cleanUp() {
        // ensures proper closure/cleanup of WebDriver
        driver.close();
        driver.quit();
    }
}
