package org.hussien.core.driver;

import org.hussien.core.utils.ConfigReader;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

// DriverOptions.java
public class DriverOptions {
    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        if (ConfigReader.get("headless").equalsIgnoreCase("true")) {
            options.addArguments("--headless=new");
        }
        options.addArguments(
                "--disable-notifications",
                "--disable-geolocation",
                "--ignore-certificate-errors"
        );
        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        if (ConfigReader.get("headless").equalsIgnoreCase("true")) {
            options.addArguments("-headless");
        }
        options.addPreference("dom.webnotifications.enabled", false);
        return options;
    }
}