package org.hussien.pages.base;

import org.hussien.core.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;


public abstract class BasePage {
    protected final WebDriver driver;

    public BasePage() {
        this.driver = WebDriverFactory.getDriver();
    }
}