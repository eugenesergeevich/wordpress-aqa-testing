package aqajava.listeners;

import aqajava.webdriver.Browser;
import io.qameta.allure.Attachment;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.stream.Stream;

@Log4j
public class TestSuiteListener implements ITestListener, ISuiteListener {

    @Override
    public void onTestFailure(ITestResult result) {
        StringBuilder stringBuilder = new StringBuilder();
        Stream.of(result.getParameters()).forEach(item -> stringBuilder.append(item).append("; "));
        log.error(result.getName() + " is FAILED with parameters: " + stringBuilder.toString() +
                " [REASON]: " + result.getThrowable().getMessage());
        saveScreenShot(result);
    }

    @Attachment(value = "Screenshot on test failure")
    public static byte[] saveScreenShot(ITestResult result) {
        log.warn("Saving screenshot on " + result.getName() + " failure");
        return ((TakesScreenshot) Browser.getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
