package org.hussien.core.utils;

import org.hussien.core.driver.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import java.time.Duration;

public class WaitUtils {
    public static final int DEFAULT_TIMEOUT = 30; // Timeout in seconds
    public static final int POLL_INTERVAL = 500; // Poll interval in milliseconds

    private static WebDriver getDriver() {
        return WebDriverFactory.getDriver();
    }

    // Core wait method with int timeout (in seconds)
    public static <T> T waitFor(Function<WebDriver, T> condition, int timeoutInSeconds) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds))  // Convert int to Duration
                .pollingEvery(Duration.ofMillis(POLL_INTERVAL))
                .ignoring(StaleElementReferenceException.class)
                .until(condition);
    }

    // ----------- Helper for default fallback ------------
    private static int resolveTimeout(int customTimeoutInSeconds) {
        return customTimeoutInSeconds > 0 ? customTimeoutInSeconds : DEFAULT_TIMEOUT;
    }

    // Visibility of element
    public static WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForVisibility(By locator, int timeoutInSeconds) {
        return waitFor(ExpectedConditions.visibilityOfElementLocated(locator), resolveTimeout(timeoutInSeconds));
    }

    // Presence of all elements
    public static List<WebElement> waitForPresenceOfElements(By locator) {
        return waitForPresenceOfElements(locator, DEFAULT_TIMEOUT);
    }

    public static List<WebElement> waitForPresenceOfElements(By locator, int timeoutInSeconds) {
        return waitFor(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), resolveTimeout(timeoutInSeconds));
    }

    // Clickable element
    public static WebElement waitForClickable(By locator) {
        return waitForClickable(locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForClickable(By locator, int timeoutInSeconds) {
        return waitFor(ExpectedConditions.elementToBeClickable(locator), resolveTimeout(timeoutInSeconds));
    }

    public static WebElement waitForClickable(WebElement element) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT)) // Convert to Duration
                .pollingEvery(Duration.ofMillis(POLL_INTERVAL))
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

