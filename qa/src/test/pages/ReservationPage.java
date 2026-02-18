package test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ReservationPage {

    public static final String RESERVATION_PAGE_URL = "https://automationintesting.online/reservation/";

    public ReservationPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
