package org.hussien.core.utils;

import org.hussien.core.driver.WebDriverFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;

public class BrowserUtils {
    public static void navigateTo(String url) {
        WebDriverFactory.getDriver().get(url);
    }

    public static void maximizeWindow() {
        WebDriverFactory.getDriver().manage().window().maximize();
    }

    public static void setWindowSize(int width, int height) {
        WebDriverFactory.getDriver().manage().window()
                .setSize(new Dimension(width, height));
    }

    public static void addCookie(String name, String value) {
        WebDriverFactory.getDriver().manage()
                .addCookie(new Cookie(name, value));
    }

    public static void refreshPage() {
        WebDriverFactory.getDriver().navigate().refresh();
    }
}