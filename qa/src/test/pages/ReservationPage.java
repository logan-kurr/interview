package test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import test.helper.ClickHelper;
import test.helper.WaitHelper;
import test.model.StayDetails;

import java.time.Month;
import java.util.List;

import static test.helper.DateHelper.*;


public class ReservationPage {

    public static final String CONFIRMATION_TITLE = "Booking Confirmed";
    public static final String CONFIRMATION_MESSAGE = "Your booking has been confirmed for the following dates:";

    // ********* Room Description Section ********
    @FindBy(xpath = "//img[@alt='Room Image']")
    private WebElement roomImage;

    // ********* Booking Section ********
    @FindBy(css = ".rbc-toolbar-label")
    private WebElement monthAndYearTxt;

    @FindBy(xpath = "//span[@class='rbc-btn-group']/button[text()='Next']")
    private WebElement nextBtn;

    @FindBy(xpath = "//div[contains(@class,'rbc-day-bg')]")
    private List<WebElement> calendarDays;

    @FindBy(xpath = "//*[@aria-label='Firstname']")
    private WebElement firstNameField;

    @FindBy(xpath = "//*[@aria-label='Lastname']")
    private WebElement lastNameField;

    @FindBy(xpath = "//*[@aria-label='Email']")
    private WebElement emailField;

    @FindBy(xpath = "//*[@aria-label='Phone']")
    private WebElement phoneField;

    @FindBy(xpath = "//button[@id='doReservation' or text()='Reserve Now']")
    private WebElement reserveNowBtn;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelBtn;

    @FindBy(xpath = "//div[contains(@class,'booking-card')]/div[@class='card-body']")
    private WebElement confirmationMessage;

    @FindBy(xpath = "//div[contains(@class,'booking-card')]//a[text()='Return home']")
    private WebElement returnHomeBtn;


    public ReservationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies the Room Details page loads as expected
     * @param driver
     * @return
     */
    public ReservationPage performVerifyInitialLoad(WebDriver driver) {

        // ensures visibility of Room Image
        WaitHelper.isElementVisible(driver, roomImage);
        return new ReservationPage(driver);
    }

    /**
     *
     * @param driver
     * @param stayDetails: stayDetails object containing all client/room info
     * @return
     */
    public ReservationPage performSelectDates(WebDriver driver, StayDetails stayDetails) {

        // selects Check-In & Check-Out dates
        Month month = parseMonth(stayDetails.getCheckInDate());
        String year = parseYear(stayDetails.getCheckInDate());

        // selects correct month
        int attemptCount = 0; // prevents infinite loop
        boolean correctDate = monthAndYearTxt.getText().toUpperCase().contains(String.format("%s %s", month, year));
        while (!correctDate && attemptCount <= 12 ) {
            attemptCount++;
            ClickHelper.click(driver, nextBtn);
            correctDate = monthAndYearTxt.getText().toUpperCase().contains(String.format("%s %s", month, year));
        }

        // selects dates on calendar
        WebElement startDateCell = calendarDays.get(parseDay(stayDetails.getCheckInDate()) - 1);
        WebElement endDateCell = calendarDays.get(parseDay(stayDetails.getCheckOutDate()) - 1);
        selectDates(driver, startDateCell, endDateCell);
        return new ReservationPage(driver);
    }

    /**
     * Enters all necessary client/contact info for Room Reservation
     * @param driver
     * @param stayDetails: stayDetails object containing all client/room info
     * @return
     */
    public ReservationPage performEnterUserDetails(WebDriver driver, StayDetails stayDetails) {

        // enters all necessary user details for booking
        ClickHelper.clearAndSendKeys(driver, firstNameField, stayDetails.getFirstName());
        ClickHelper.clearAndSendKeys(driver, lastNameField, stayDetails.getLastName());
        ClickHelper.clearAndSendKeys(driver, emailField, stayDetails.getEmail());
        ClickHelper.clearAndSendKeys(driver, phoneField, stayDetails.getPhoneNumber());

        // clicks the reserve button
        ClickHelper.click(driver, reserveNowBtn);
        return new ReservationPage(driver);
    }

    /**
     * Verifies room Confirmation and Dates
     * @param driver
     * @param stayDetails: stayDetails object containing all client/room info
     * @return
     */
    public ReservationPage performVerifyConfirmation(WebDriver driver, StayDetails stayDetails) {

        WaitHelper.isElementVisible(driver, confirmationMessage);
        WaitHelper.isConditionSuccess(driver, ExpectedConditions.textToBePresentInElement(confirmationMessage, CONFIRMATION_TITLE), WaitHelper.DEFAULT_WAIT);

        // verifies all Confirmation Details are accurate
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(confirmationMessage.getText().contains(CONFIRMATION_TITLE), String.format("Confirmation Title does not Equal = %s", CONFIRMATION_TITLE));
        softAssert.assertTrue(confirmationMessage.getText().contains(CONFIRMATION_MESSAGE), String.format("Confirmation Message does not Contain = %s", CONFIRMATION_MESSAGE));
        softAssert.assertTrue(confirmationMessage.getText().contains(stayDetails.getCheckInDate()));
        softAssert.assertTrue(confirmationMessage.getText().contains(getFutureDate(stayDetails.getCheckOutDate(), 1)));
        softAssert.assertAll();

        // clicks 'Return Home' button
        WaitHelper.isElementVisible(driver, returnHomeBtn);
        ClickHelper.click(driver, returnHomeBtn);
        return new ReservationPage(driver);
    }

    /**
     * Clicks the 'Reserve' button on the Reservation page
     * @param driver
     * @return
     */
    public ReservationPage performClickReserve(WebDriver driver) {

        // clicks 'Reserve Now' button
        WaitHelper.isElementVisible(driver, reserveNowBtn);
        ClickHelper.click(driver, reserveNowBtn);
        return new ReservationPage(driver);
    }

    /**
     * Necessary method to handle drag-and-drop behavior of Calendar
     * @param driver
     * @param startDateCell: Starting Date WebElement in the Calendar
     * @param endDateCell: Ending Date WebElement in the Calendar
     */
    private void selectDates(WebDriver driver, WebElement startDateCell, WebElement endDateCell) {
        Actions actions = new Actions(driver);
        actions.clickAndHold(startDateCell)
                .pause(250)
                .moveByOffset(-10, 10)
                .pause(250)
                .moveToElement(endDateCell)
                .pause(250)
                .release(endDateCell)
                .build()
                .perform();
    }
}
