package test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;
import test.helper.ClickHelper;
import test.helper.WaitHelper;
import test.model.StayDetails;

public class ReservationPage {

    public static final String RESERVATION_PAGE_URL = "https://automationintesting.online/reservation/";

    public static final String CONFIRMATION_TITLE = "Booking Confirmed";
    public static final String CONFIRMATION_MESSAGE = "Your booking has been confirmed for the following dates:";
    public static final String CONFIRMATION_DATES = "%s - %s";

    // ********* Room Description Section ********
    @FindBy(xpath = "//img[@alt='Room Image']")
    private WebElement roomImage;

    // ********* Booking Section ********
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

    @FindBy(xpath = "//div[contains(@class,'booking-card')]//a[text()='Return Home']")
    private WebElement returnHomeBtn;


    public ReservationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public ReservationPage performVerifyInitialLoad(WebDriver driver) {

        // ensures visibility of Room Image & Description
        WaitHelper.isElementVisible(driver, roomImage);
        return new ReservationPage(driver);
    }

    public ReservationPage performSelectDates(WebDriver driver, StayDetails stayDetails) {

        // selects Check-In & Check-Out dates
        return new ReservationPage(driver);
    }

    public ReservationPage performEnterUserDetails(WebDriver driver, StayDetails stayDetails) {

        // enters all necessary user details for booking
        WaitHelper.isElementVisible(driver, firstNameField);
        ClickHelper.click(driver, firstNameField);
        firstNameField.clear();
        firstNameField.sendKeys(stayDetails.getFirstName());

        ClickHelper.click(driver, lastNameField);
        lastNameField.clear();
        lastNameField.sendKeys(stayDetails.getLastName());

        ClickHelper.click(driver, emailField);
        emailField.clear();
        emailField.sendKeys(stayDetails.getEmail());

        ClickHelper.click(driver, phoneField);
        phoneField.clear();
        phoneField.sendKeys(stayDetails.getPhoneNumber());

        ClickHelper.click(driver, reserveNowBtn);
        return new ReservationPage(driver);
    }

    public ReservationPage performVerifyConfirmation(WebDriver driver, StayDetails stayDetails) {

        // verifies all Confirmation Details are accurate
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(confirmationMessage.getText().contains(CONFIRMATION_TITLE), String.format("Confirmation Title does not Equal = %s", CONFIRMATION_TITLE));
        softAssert.assertTrue(confirmationMessage.getText().contains(CONFIRMATION_MESSAGE), String.format("Confirmation Message does not Contain = %s", CONFIRMATION_MESSAGE));
        softAssert.assertTrue(confirmationMessage.getText().contains(String.format(
                CONFIRMATION_DATES, stayDetails.getCheckInDate(), stayDetails.getCheckOutDate())));
        softAssert.assertAll();

        // clicks 'Return Home' button
        WaitHelper.isElementVisible(driver, returnHomeBtn);
        ClickHelper.click(driver, returnHomeBtn);
        return new ReservationPage(driver);
    }

    public ReservationPage performClickReserve(WebDriver driver) {

        // clicks 'Reserve Now' button
        WaitHelper.isElementVisible(driver, reserveNowBtn);
        ClickHelper.click(driver, reserveNowBtn);
        return new ReservationPage(driver);
    }
}
