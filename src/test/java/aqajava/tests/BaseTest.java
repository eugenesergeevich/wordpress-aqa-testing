package aqajava.tests;

import aqajava.helpers.services.AppManager;
import aqajava.webdriver.Browser;
import io.qameta.allure.Attachment;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.*;

@Log4j
public class BaseTest {

    protected static AppManager app;

    @BeforeSuite
    public void setUp() {
        log.info("Execute BeforeClass setUp");
        app = AppManager.getAppInstance();
    }

    @BeforeMethod
    public void beforeMethod() {
        log.info("Execute BeforeMethod");
        app.getAuthentication().openLoginPage();
    }

    @AfterSuite
    public void tearDown() {
        log.info("Execute AfterClass tearDown");
        app.stop();
    }

    @Attachment (value = "Screenshot")
    public static byte[] saveScreenShot() {
        log.info("Saving screenshot");
        return ((TakesScreenshot) Browser.getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
