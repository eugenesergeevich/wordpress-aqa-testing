package aqajava.webdriver;

import aqajava.enums.BrowserType;
import aqajava.properties.ConfigProperties;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;

@Log4j
public class Browser {

    private static WebDriver driver;
    {
        log.info("Load from properties file BROWSER_TYPE " + ConfigProperties.prop.getProperty("BROWSER_TYPE"));
    }
    private static BrowserType browserType = BrowserType.valueOf(ConfigProperties.prop.getProperty("BROWSER_TYPE"));

    private Browser() {
    }

    public static void initDriver() {
        log.info("Initialize WebDriver");
        driver = BrowserFactory.createDriver(browserType);
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        log.info("Close and quit WebDriver");
        if (driver != null) {
            if (!browserType.equals(BrowserType.FIREFOX)) {
                driver.close();
            }
            driver.quit();
            driver = null;
        }
    }
}
