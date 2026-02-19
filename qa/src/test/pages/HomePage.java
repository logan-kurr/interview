package test.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import test.helper.ClickHelper;
import test.helper.WaitHelper;
import test.model.StayDetails;


public class HomePage {

    public static final String HOME_PAGE_URL = "https://automationintesting.online/";

    private static final String HOME_PAGE_TITLE = "Restful-booker-platform demo";
    private static final String CONTACT_NAME = "Thanks for getting in touch %s!";
    private static final String CONTACT_REPLY = "We'll get back to you about";

    // ********* Header Section ********
    @FindBy(xpath = "//div[@id='navbarNav']//a[@href='/#rooms']")
    private WebElement roomsLink;

    @FindBy(xpath = "//div[@id='navbarNav']//a[@href='/#contact']")
    private WebElement contactLink;

    // ********* Hero Image Section ********
    @FindBy(css = ".hero.py-5")
    private WebElement heroSection;

    @FindBy(xpath = "//div[contains(@class,'hero-content')]//a[@href='#booking']")
    private WebElement bookNowBtn;

    // ********* Availability Section ********
    @FindBy(xpath = "//section[@id='booking']//a[text()='Check Availability']")
    private WebElement checkAvailabilityBtn;

    // ********* Rooms Section ********
    @FindBy(id = "rooms")
    private WebElement roomsSection;

    @FindBy(xpath = "//section[@id='rooms']//a[contains(@href,'/reservation/1')]")
    private WebElement singleBookBtn;

    @FindBy(xpath = "//section[@id='rooms']//a[contains(@href,'/reservation/2')]")
    private WebElement doubleBookBtn;

    @FindBy(xpath = "//section[@id='rooms']//a[contains(@href,'/reservation/3')]")
    private WebElement suiteBookBtn;

    // ********* Location Section ********
    @FindBy(id = "location")
    private WebElement locationSection;

    // ********* Contact Section ********
    @FindBy(id = "contact")
    private WebElement contactSection;

    @FindBy(xpath = "//input[@data-testid='ContactName']")
    private WebElement contactNameField;

    @FindBy(xpath = "//input[@data-testid='ContactEmail']")
    private WebElement contactEmailField;

    @FindBy(xpath = "//input[@data-testid='ContactPhone']")
    private WebElement contactPhoneField;

    @FindBy(xpath = "//input[@data-testid='ContactSubject']")
    private WebElement contactSubjectField;

    @FindBy(xpath = "//textarea[@data-testid='ContactDescription']")
    private WebElement contactMessageField;

    @FindBy(xpath = "//section[@id='contact']//button[text()='Submit']")
    private WebElement submitContactBtn;

    @FindBy(xpath = "//section[@id='contact']//div[contains(@class,'card-body')]")
    private WebElement contactConfirmationMessage;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies initla Loading of the Homepage
     * @param driver
     * @return
     */
    public HomePage performVerifyInitialLoad(WebDriver driver) {

        // verifies pageTitle
        Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());

        // ensures visibility of Hero Section
        WaitHelper.isElementVisible(driver, heroSection);
        return new HomePage(driver);
    }

    /**
     * CLicks the Contact Header link
     * @param driver
     * @return
     */
    public HomePage performClickContactLink(WebDriver driver) {

        // clicks 'Contact' header link
        WaitHelper.isElementVisible(driver, contactLink);
        ClickHelper.click(driver, contactLink);

        // ensures visibility of Contact Section
        WaitHelper.isElementVisible(driver, contactSection);
        return new HomePage(driver);
    }

    /**
     * Clicks the Book Now button to navigate down to the Booking section
     * @param driver
     * @return
     */
    public HomePage performClickBookNow(WebDriver driver) {

        // clicks 'Book Now' button
        WaitHelper.isElementVisible(driver, bookNowBtn);
        ClickHelper.click(driver, bookNowBtn);

        // ensures visibility of Rooms
        WaitHelper.isElementVisible(driver, roomsSection);
        return new HomePage(driver);
    }

    /**
     * Selects the specified Room Type via it's Card
     * @param driver
     * @param roomType: Room type to be clicked
     * @return
     */
    public ReservationPage performSelectRoomType(WebDriver driver, StayDetails.RoomType roomType) {

        switch (roomType) {
            case SINGLE:
                ClickHelper.click(driver, singleBookBtn);
                break;
            case DOUBLE:
                ClickHelper.click(driver, doubleBookBtn);
                break;
            case SUITE:
                ClickHelper.click(driver, suiteBookBtn);
                break;
            default:
                throw new RuntimeException(
                        String.format("Specified Room Type = '%s' Does Not Exist/Not Available!", roomType.getValue()));
        }
        return new ReservationPage(driver);
    }

    /**
     * Enters all necessary Contact details and submits
     * @param driver
     * @param name: User's name to be entered in the Contact Form
     * @param email: Email to be entered into the Contact form
     * @param phone: Phone number to be entered in the Contact form
     * @param subject: Subject to be entered into the Contact form
     * @param message: Message to be entered into the Contact form
     * @return
     */
    public HomePage performSubmitContactDetails(WebDriver driver, String name, String email, String phone, String subject, String message) {

        // enters all necessary contact details
        ClickHelper.clearAndSendKeys(driver, contactNameField, name);
        ClickHelper.clearAndSendKeys(driver, contactEmailField, email);
        ClickHelper.clearAndSendKeys(driver, contactPhoneField, phone);
        ClickHelper.clearAndSendKeys(driver, contactSubjectField, subject);
        ClickHelper.clearAndSendKeys(driver, contactMessageField, message);

        // clicks Contact Submit button
        ClickHelper.click(driver, submitContactBtn);
        return new HomePage(driver);
    }

    /**
     * Verifies Confirmation messaging for Contact Submission
     * @param driver
     * @param name: User's name to be entered in the Contact Form
     * @param subject: Subject to be entered into the Contact Form
     */
    public void performVerifyContactConfirmation(WebDriver driver, String name, String subject) {

        WaitHelper.isElementVisible(driver, contactConfirmationMessage);
        WaitHelper.isConditionSuccess(driver, ExpectedConditions.textToBePresentInElement(contactConfirmationMessage, CONTACT_REPLY), WaitHelper.DEFAULT_WAIT);

        // validates confirmation of contact submission
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(contactConfirmationMessage.getText().contains(String.format(CONTACT_NAME, name)));
        softAssert.assertTrue(contactConfirmationMessage.getText().contains(subject));
        softAssert.assertAll();
    }
}
