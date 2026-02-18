package test.helper;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static test.helper.WaitHelper.DEFAULT_WAIT;

public class ClickHelper {

    private static final Logger log = LoggerFactory.getLogger(ClickHelper.class.getSimpleName());

    /**
     * Wait for element to be clickable and click on it
     *
     * @param driver
     * @param element
     */
    public static void click(WebDriver driver, WebElement element) {
        try {
            WaitHelper.wait(ExpectedConditions.elementToBeClickable(element), driver, DEFAULT_WAIT);
            moveTo(driver, element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            log.error("clickElement ElementClickInterceptedException: {}", e.getMessage());
            moveTo(driver, element);
            jsClick(driver, element);
        } catch (StaleElementReferenceException e) {
            log.error("clickElement StaleElementReferenceException: {}", e.getMessage());
            try {
                WaitHelper.wait(ExpectedConditions.elementToBeClickable(element), driver, DEFAULT_WAIT);
                element.click();
            } catch (ElementClickInterceptedException ex) {
                log.error("clickElement ElementClickInterceptedException from StaleElementReferenceException: {}", e.getMessage());
                moveTo(driver, element);
                jsClick(driver, element);
            }
        }
    }

    private static void jsClick(WebDriver driver, WebElement element) {
        WaitHelper.wait(ExpectedConditions.elementToBeClickable(element), driver, DEFAULT_WAIT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private static void moveTo(WebDriver driver, WebElement element) {
        Actions builder = new Actions(driver);
        builder.moveToElement(element).build().perform();
    }
}
