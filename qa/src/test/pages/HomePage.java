package test.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import test.helper.ClickHelper;
import test.helper.WaitHelper;

import java.util.List;

public class HomePage {

    public static final String HOME_PAGE_URL = "https://automationintesting.online/";
    private static final String HOME_PAGE_TITLE = "Restful-booker-platform demo";

    // ********* Header Section ********
    @FindBy(xpath = "//div[@id='navbarNav']//a[@href='/#rooms']")
    private WebElement roomsLink;

    // ********* Hero Image Section ********
    @FindBy(css = ".hero.py-5")
    private WebElement heroSection;

    @FindBy(xpath = "//div[contains(@class,'hero-content')]//a[@href='#booking']")
    private WebElement bookNowBtn;

    // ********* Availability Section ********
    @FindBy(xpath = "//section[@id='booking']//a[text()='Check Availability']")
    private List<WebElement> checkAvailabilityBtn;

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

    // ********* Footer Section ********
    @FindBy(xpath = "//footer")
    private List<WebElement> footerSection;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public HomePage performVerifyInitialLoad(WebDriver driver) {

        // verifies pageTitle
        Assert.assertEquals(HOME_PAGE_TITLE, driver.getTitle());

        // ensures visibility of
        WaitHelper.isElementVisible(driver, heroSection);
        return new HomePage(driver);
    }

    public HomePage performBookNow(WebDriver driver) {

        // clicks 'Book Now' button
        WaitHelper.wait(Long.valueOf(5000));
        ClickHelper.click(driver, bookNowBtn);

        // ensures visibility of Rooms
        WaitHelper.isElementVisible(driver, roomsSection);
        return new HomePage(driver);
    }

    public ReservationPage performSelectRoomType(WebDriver driver, String roomType) {

        switch (roomType.toUpperCase()) {
            case "SINGLE":
                ClickHelper.click(driver, singleBookBtn);
                return new ReservationPage(driver);
            case "DOUBLE":
                ClickHelper.click(driver, doubleBookBtn);
                return new ReservationPage(driver);
            case "SUITE":
                ClickHelper.click(driver, suiteBookBtn);
                return new ReservationPage(driver);
            default:
                throw new RuntimeException(
                        String.format("Specified Room Type = '%s' Does Not Exist/Not Available!", roomType));
        }
    }
}