package org.hussien.pages.base;

import org.hussien.core.driver.WebDriverFactory;
import org.hussien.core.exceptions.ElementNotFoundException;
import org.hussien.core.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class BasePage {
    protected WebDriver driver;


    public BasePage() {
        this.driver = WebDriverFactory.getDriver();
    }

    protected void click(By locator) {
        WaitUtils.waitForClickable(locator).click();
    }

    protected void clickOnButtonWithText(String buttonText) {
        By locator = By.xpath("//*[normalize-space(text())='" + buttonText + "' and (self::button or self::a or self::input or self::span)]");
        try {

            WaitUtils.waitForClickable(locator).click();
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException(locator, e);
        }
    }

    protected void type(By locator, String text) {
        WebElement element = WaitUtils.waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    public static void hoverOverElement(By locator) {

        WebElement element = WaitUtils.waitForVisibility(locator);
        Actions actions = new Actions(WebDriverFactory.getDriver());
        actions.moveToElement(element).perform();
    }


    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtils.waitForVisibility(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}