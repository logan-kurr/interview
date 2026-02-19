package test.helper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;

public class WaitHelper {

    private static final Logger log = LoggerFactory.getLogger(WaitHelper.class.getSimpleName());

    public static final Long DEFAULT_WAIT = 2500L;

    /**
     * Generic wait method that takes a time and forces the thread to sleep
     *
     * @param waitTimeMillis wait time to force sleep
     */
    public static void wait(Long waitTimeMillis) {
        try {
            Thread.sleep(waitTimeMillis);
        } catch (InterruptedException e) {
            log.error("Error while waiting: {}", e.getMessage());
        }
    }

    /**
     * Wait for specified Condition within specific timeout
     *
     * @param condition
     * @param driver
     * @param waitTimeMillis
     */
    public static void wait(ExpectedCondition<?> condition, WebDriver driver, Long waitTimeMillis) {
        try {
            // reset implicit wait to 0 and wait only specified `timeout`
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            new WebDriverWait(driver, Duration.ofMillis(waitTimeMillis))
                    .ignoreAll(Arrays.asList(
                            NoSuchSessionException.class,
                            StaleElementReferenceException.class,
                            WebDriverException.class))
                    .until(condition);
        } catch (Exception e) {
            log.error("Error while waiting: {}", e.getMessage());
        }
    }

    /**
     * Returns whether element is visible
     *
     * @param driver
     * @param byLocator
     * @return true if element is visible
     */
    public static boolean isElementVisible(WebDriver driver, By byLocator) {
        log.debug("Checking if element is visible...");
        return isConditionSuccess(driver, ExpectedConditions.visibilityOfElementLocated(byLocator), DEFAULT_WAIT);
    }

    /**
     * Returns whether element is visible
     *
     * @param driver
     * @param element
     * @return true if element is visible
     */
    public static boolean isElementVisible(WebDriver driver, WebElement element) {
        log.debug("Checking if element is visible...");
        return isConditionSuccess(driver, ExpectedConditions.visibilityOf(element), DEFAULT_WAIT);
    }

    /**
     * Wait for specified condition within specific timeout and return true in case of successful condition
     *
     * @param driver
     * @param condition
     * @param waitTimeMillis
     * @return true if condition is successful
     */
    public static boolean isConditionSuccess(WebDriver driver, ExpectedCondition<?> condition, Long waitTimeMillis) {
        try {
            wait(condition, driver, waitTimeMillis);
            log.debug("SUCCESS in {}", condition.toString());
            return true;
        } catch (TimeoutException e) {
            log.error("NOT SUCCESS in {}", condition.toString());
            return false;
        }
    }
}
