package org.hussien.core.utils;


import org.hussien.core.driver.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;


public class WaitUtils {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);


    public static WebElement waitForVisibility(By locator) {
        return new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }



    public static WebElement waitForClickable(By locator) {
        return new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static void waitForAddToCartComplete(WebElement element) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT);
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.invisibilityOf(element),
                    ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element))
            ));
        } catch (TimeoutException e) {
            System.out.println("Add to cart button did not disappear or become non-clickable within timeout.");
        }
    }

    public static WebElement waitForClickableElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(),DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }



    public static void waitForPageLoad() {
        new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT)
                .until(driver -> Objects.equals(((JavascriptExecutor) driver)
                        .executeScript("return document.readyState"), "complete"));
    }

    public static List<WebElement> waitForPresenceOfElements(By locator) {
        return new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForPresenceOfElement(WebElement parent, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT);
            return wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, locator));
        } catch (TimeoutException e) {
            return null; // Return null if the element is not found within the timeout
        }
    }

    public static List<WebElement> waitForPresenceOfElements(WebElement parent, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), DEFAULT_TIMEOUT);
            return wait.until(driver -> {
                List<WebElement> elements = parent.findElements(locator);
                return elements.isEmpty() ? null : elements;
            });
        } catch (TimeoutException e) {
            return List.of(); // Return an empty list if elements are not found
        }
    }

}