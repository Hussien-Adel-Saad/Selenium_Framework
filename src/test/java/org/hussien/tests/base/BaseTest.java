package org.hussien.tests.base;

import org.apache.commons.io.FileUtils;
import org.hussien.core.driver.WebDriverFactory;
import org.hussien.core.utils.BrowserUtils;
import org.hussien.core.utils.ConfigReader;
import org.hussien.core.utils.ReportManager;
import org.hussien.pages.ShoppingCartPage;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;

public abstract class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void initReport() {
        ReportManager.initReport();
    }
    @BeforeSuite(alwaysRun = true)
    public void cleanOldReports() {
        try {
            cleanDirectory("test-output/screenshots");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            cleanDirectory("test-output/reports");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cleanDirectory(String path) throws IOException {
        File directory = new File(path);
        if (directory.exists()) {
            FileUtils.cleanDirectory(directory);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult result) {
        // Initialize WebDriver and test report entry
        BrowserUtils.maximizeWindow();
        BrowserUtils.navigateTo(ConfigReader.get("url"));
        ReportManager.createTest(result.getMethod().getMethodName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {

            if (result.getStatus() == ITestResult.FAILURE) {
                if (ConfigReader.getBoolean("capture.screenshots")) {
                    ReportManager.logFailWithScreenshot("Test Failed - " + result.getThrowable().getMessage());
                }
            }

            try {
                new ShoppingCartPage().clearCart();
                ReportManager.logStepWithScreenshot("Cart is cleared successfully");
            } catch (Exception e) {
                System.err.println("Cart cleanup error: " + e.getMessage());
                if (ConfigReader.getBoolean("capture.screenshots")) {
                    ReportManager.logFailWithScreenshot("Cart cleanup encountered an error");
                }
            }

        } finally {

            WebDriverFactory.quitDriver();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        ReportManager.flushReport();
    }
}