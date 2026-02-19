package test.services;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import test.model.StayDetails;
import test.pages.ReservationPage;

public class ReservationPageService {

    private static final Logger log = LoggerFactory.getLogger(ReservationPageService.class.getSimpleName());

    /**
     * Verifies Room Reservation Page has been navigated to, and is Correct
     *
     * @param driver
     * @param roomType: Specified Room Type to be verified against
     */
    public void stepVerifyReservationPage(WebDriver driver, StayDetails.RoomType roomType) {
        log.info("Verifying correct Room Type = {}...", roomType.getValue());

        switch (roomType) {
            case SINGLE:
                Assert.assertTrue(driver.getCurrentUrl().contains("reservation/1"));
                break;
            case DOUBLE:
                Assert.assertTrue(driver.getCurrentUrl().contains("reservation/2"));
                break;
            case SUITE:
                Assert.assertTrue(driver.getCurrentUrl().contains("reservation/3"));
                break;
            default:
                throw new RuntimeException(
                        String.format("Specified Room Type = '%s' Does Not Exist/Not Available!", roomType.getValue()));
        }
        log.info("Verifying initial Reservation Page PageLoad...");
        new ReservationPage(driver)
                .performVerifyInitialLoad(driver);
    }

    /**
     * Selects Available Desired dates from the Room Reservation page
     *
     * @param driver
     * @param stayDetails: stayDetails object containing all client/room info
     */
    public void stepReserveAvailableDates(WebDriver driver, StayDetails stayDetails) {
        log.info("Selecting Check-In & Check-Out Dates...");
        new ReservationPage(driver)
                .performSelectDates(driver, stayDetails)
                .performClickReserve(driver);
    }

    /**
     * Enters provided User Details and Confirms booking
     *
     * @param driver
     * @param stayDetails: stayDetails object containing all client/room info
     */
    public void stepEnterUserDetailsAndSubmit(WebDriver driver, StayDetails stayDetails) {
        log.info("Entering User Details and Submitting Booking...");
        new ReservationPage(driver)
                .performEnterUserDetails(driver, stayDetails)
                .performVerifyConfirmation(driver, stayDetails);
    }
}
