package org.hussien.core.utils;

import org.hussien.core.driver.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class WaitUtils {
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration POLL_INTERVAL = Duration.ofMillis(500);

    private static WebDriver getDriver() {
        return WebDriverFactory.getDriver();
    }

    // Core wait method
    public static <T> T waitFor(Function<WebDriver, T> condition, Duration timeout) {
        return new WebDriverWait(getDriver(), timeout)
                .pollingEvery(POLL_INTERVAL)
                .ignoring(StaleElementReferenceException.class)
                .until(condition);
    }

    // ----------- Helper for default fallback ------------
    private static Duration resolveTimeout(Duration customTimeout) {
        return customTimeout != null ? customTimeout : DEFAULT_TIMEOUT;
    }

    // Visibility of element
    public static WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, null);
    }

    public static WebElement waitForVisibility(By locator, Duration timeout) {
        return waitFor(ExpectedConditions.visibilityOfElementLocated(locator), resolveTimeout(timeout));
    }

    // Presence of all elements
    public static List<WebElement> waitForPresenceOfElements(By locator) {
        return waitForPresenceOfElements(locator, null);
    }

    public static List<WebElement> waitForPresenceOfElements(By locator, Duration timeout) {
        return waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), resolveTimeout(timeout));
    }

    // Clickable element
    public static WebElement waitForClickable(By locator) {
        return waitForClickable(locator, null);
    }

    public static WebElement waitForClickable(By locator, Duration timeout) {
        return waitFor(ExpectedConditions.elementToBeClickable(locator), resolveTimeout(timeout));
    }

    public static WebElement waitForClickable(WebElement element) {
        return new WebDriverWait(getDriver(), DEFAULT_TIMEOUT)
                .pollingEvery(POLL_INTERVAL)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    // Page load wait
    public static void waitForPageLoad() {
        waitFor(driver ->
                        Objects.equals(
                                ((JavascriptExecutor) driver).executeScript("return document.readyState"),
                                "complete"
                        ),
                DEFAULT_TIMEOUT
        );
    }


    // Sleep helper
    public static void waitForMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
