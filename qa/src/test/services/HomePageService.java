package test.services;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.model.StayDetails;
import test.pages.HomePage;

import static test.pages.HomePage.HOME_PAGE_URL;

public class HomePageService {

    private static final Logger log = LoggerFactory.getLogger(HomePageService.class.getSimpleName());

    /**
     * Navigates to Shady Meadows Homepage and Verifies
     *
     * @param driver
     */
    public void stepNavigateAndVerifyHomePage(WebDriver driver) {
        log.info("Navigating to Home Page...");
        driver.get(HOME_PAGE_URL);

        log.info("Verifying initial PageLoad...");
        new HomePage(driver)
                .performVerifyInitialLoad(driver);
    }

    /**
     * Navigates to Shady Meadows Homepage and verifies Contact Functionality
     *
     * @param driver
     */
    public void stepVerifyAndSubmitContactSection(WebDriver driver, String name, String email, String phone, String subject, String message) {
        log.info("Verifying Contact section functionality...");
        new HomePage(driver)
                .performClickContactLink(driver)
                .performSubmitContactDetails(driver, name, email, phone, subject, message)
                .performVerifyContactConfirmation(driver, name, subject);
    }

    /**
     * Clicks the Specified Room Types's "Book Now" button
     *
     * @param driver
     */
    public void stepSelectRoomType(WebDriver driver, StayDetails stayDetails) {
        log.info("Selecting specified Room Type = '{}'...", stayDetails.getRoomType().getValue());
        new HomePage(driver)
                .performClickBookNow(driver)
                .performSelectRoomType(driver, stayDetails.getRoomType());
    }
}
