package org.hussien.core.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void createTest(String testName) {
        test.set(extent.createTest(testName));
    }

    public static void initReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String reportPath = ConfigReader.get("report.dir") + "/ExtentReport " + timestamp + ".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);

    }

    public static void flushReport() {
        extent.flush();
    }

    // Step logging methods
    public static void logStep(String message) {
        getTest().info(message);
    }

    public static void logPass(String message) {
        getTest().pass(message);
    }

    public static void logStepWithScreenshot(String message) {
        if (ConfigReader.getBoolean("capture.screenshots")) {
            String fileName = ScreenshotUtils.capture("step_" + sanitize(message));
            getTest().info(message,
                    MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } else {
            logStep(message);
        }
    }

    public static void logPassWithScreenshot(String message) {
        if (ConfigReader.getBoolean("capture.screenshots")) {
            String fileName = ScreenshotUtils.capture("pass_" + sanitize(message));
            getTest().pass(message,
                    MediaEntityBuilder.createScreenCaptureFromPath(fileName).build());
        } else {
            logPass(message);
        }
    }

    private static String sanitize(String text) {
        return text.replaceAll("[^a-zA-Z0-9]", "_");
    }

    public static void logFailWithScreenshot(String message) {
        String path = ScreenshotUtils.capture("fail_" + sanitize(message));
        getTest().fail(message,
                MediaEntityBuilder.createScreenCaptureFromPath(path).build());
    }

}