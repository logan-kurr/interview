package test.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import test.helper.WaitHelper;
import test.services.HomePageService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;


public class FullBookingFlow  {

    private static final Logger log = LoggerFactory.getLogger(WaitHelper.class.getSimpleName());

    private final HomePageService homePageService = new HomePageService();
    private final String ROOM_TYPE = "Single";

    private WebDriver driver;

    @BeforeMethod(description = "Driver Startup")
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver  = new ChromeDriver();
    }

    @Test(description = "Select Room Type")
    public void testSelectRoomType() {

        homePageService.stepNavigateAndVerifyHomePage(driver);
        homePageService.stepSelectRoomType(driver, ROOM_TYPE);
    }

//    @Test(description = "Select Check-In/Check-Out Dates", dependsOnMethods = "testSelectRoomType")
//    public void testSelectStayDates() {
//
//    }
//
//    @Test(description = "Enter Guest Details", dependsOnMethods = "testSelectStayDates")
//    public void testEnterGuestDetails() {
//
//    }
//
//    @Test(description = "Submit Booking", dependsOnMethods = "testEnterGuestDetails")
//    public void testSubmitBooking() {
//
//    }

    @AfterMethod(description = "Driver Cleanup")
    public void cleanUp() {
        // ensures proper closure/cleanup of WebDriver
        driver.close();
        driver.quit();
    }
}
