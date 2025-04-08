package org.hussien.core.utils;

import org.apache.commons.io.FileUtils;
import org.hussien.core.driver.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    public static String capture(String actionName) {
        if (!ConfigReader.getBoolean("capture.screenshots")) return "";

        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = sanitize(actionName) + "_" + timestamp + ".png";
            File destDir = new File(ConfigReader.get("screenshot.dir"));
            destDir.mkdirs();

            File srcFile = ((TakesScreenshot) WebDriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
            File destFile = new File(destDir, fileName);
            FileUtils.copyFile(srcFile, destFile);

            // Return relative path from report directory
            return "screenshots/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Screenshot failed", e);
        }
    }

    private static String sanitize(String text) {
        return text.replaceAll("[^a-zA-Z0-9]", "_");
    }
}