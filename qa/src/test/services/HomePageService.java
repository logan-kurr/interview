package test.services;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * Clicks the Specified Room Types's "Book Now" button
     *
     * @param driver
     */
    public void stepSelectRoomType(WebDriver driver, String roomType) {
        log.info("Selecting specified Room Type = '{}'...", roomType);
        new HomePage(driver)
                .performBookNow(driver)
                .performSelectRoomType(driver, roomType);
    }
}
