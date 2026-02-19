package test.tests.uiTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.services.HomePageService;


public class ContactFlow {

    private final HomePageService homePageService = new HomePageService();

    private final String NAME = "John Smith";
    private final String EMAIL = "jsmith@gmail.com";
    private final String PHONE = "555-111-2222";
    private final String SUBJECT = "Room Upgrade Options";
    private final String MESSAGE = "Hello! I was just curious if I would be able to upgrade my room to a Suite.";

    private WebDriver driver;

    @BeforeClass(description = "Driver Startup")
    public void startUp() {
        // spins up new WebDriver
        WebDriverManager.chromedriver().setup();
        driver  = new ChromeDriver();
    }

    @Test(description = "Enters and Submits a Contact Message")
    public void testSubmitContactRequest() {

        homePageService.stepNavigateAndVerifyHomePage(driver);
        homePageService.stepVerifyAndSubmitContactSection(driver, NAME, EMAIL, PHONE, SUBJECT, MESSAGE);
    }

    @AfterClass(description = "Driver Cleanup")
    public void cleanUp() {
        // ensures proper closure/cleanup of WebDriver
        driver.close();
        driver.quit();
    }
}
