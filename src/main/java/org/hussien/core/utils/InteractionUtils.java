package org.hussien.core.utils;

import org.hussien.core.driver.WebDriverFactory;
import org.hussien.core.exceptions.ElementNotFoundException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.hussien.core.utils.WaitUtils.waitFor;

public class InteractionUtils {

    public static void click(By locator) {
        WebElement element = WaitUtils.waitForClickable(locator);
        element.click();
    }

    public static void clickOnButtonWithText(String text) {
        By locator = By.xpath(String.format(
                "//*[normalize-space()='%s'][self::button or self::a or self::input or self::span]",
                text
        ));
        try {
            click(locator);
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException(locator, e);
        }
    }

    public static void type(By locator, String text) {
        WebElement element = WaitUtils.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public static void hover(By locator) {
        new Actions(WebDriverFactory.getDriver())
                .moveToElement(WaitUtils.waitForVisibility(locator))
                .perform();
    }

    public static boolean isDisplayed(By locator) {
        try {
            return WaitUtils.waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public static void clickWithJS(By locator) {
        WebElement element = WaitUtils.waitForVisibility(locator);
        executeJS(element);
    }

//    public static void clickWithRetry(WebElement element, int maxAttempts) {
//        for (int attempt = 0; attempt < maxAttempts; attempt++) {
//            try {
//                WebElement freshElement = WaitUtils.waitForClickable(element);
//                freshElement.click();
//                return;
//            } catch (StaleElementReferenceException e) {
//                System.out.println("Attempt " + (attempt + 1) + ": Element went stale, retrying...");
//            } catch (ElementClickInterceptedException e) {
//                System.out.println("Attempt " + (attempt + 1) + ": Element not clickable, trying JS click...");
//                clickWithJS(element);
//                return;
//            }
//        }
//        throw new RuntimeException("Failed to click after " + maxAttempts + " attempts");
//    }

    private static void executeJS(Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", args);
    }


    public static List<WebElement> findChildElements(WebElement parent, By childLocator) {
        return waitFor(driver -> parent.findElements(childLocator), WaitUtils.DEFAULT_TIMEOUT);
    }


    public static WebElement findChildElement(WebElement parent, By childLocator) {
        return waitFor(driver -> {
            List<WebElement> elements = parent.findElements(childLocator);
            if (elements.isEmpty()) {
                throw new NoSuchElementException("Child element not found: " + childLocator);
            }
            return elements.getFirst();
        }, WaitUtils.DEFAULT_TIMEOUT);
    }

}