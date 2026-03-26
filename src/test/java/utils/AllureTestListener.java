package utils;

import Tests.BaseTest;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        attachArtifacts(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) {
            attachText("Skip reason", result.getThrowable().getMessage());
        }
    }

    private void attachArtifacts(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            WebDriver driver = ((BaseTest) instance).getDriver();
            if (driver != null) {
                attachScreenshot(driver);
                attachPageSource(driver.getPageSource());
                attachText("Current URL", driver.getCurrentUrl());
            }
        }

        if (result.getThrowable() != null) {
            attachText("Failure details", result.getThrowable().toString());
        }
    }

    @Attachment(value = "Failure screenshot", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html")
    private String attachPageSource(String pageSource) {
        return pageSource;
    }

    @Attachment(value = "{name}", type = "text/plain")
    private String attachText(String name, String value) {
        return value == null ? "" : value;
    }
}
