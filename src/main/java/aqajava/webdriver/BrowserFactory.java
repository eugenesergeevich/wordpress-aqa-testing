package aqajava.webdriver;

import aqajava.enums.BrowserType;
import aqajava.properties.ConfigProperties;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;

@Log4j
public class BrowserFactory {

    public static WebDriver createDriver(BrowserType browserType) {

        WebDriver driver;
        log.info("Load from properties file WEBDRIVER " + ConfigProperties.prop.getProperty("WEBDRIVER") +
            " and WEBDRIVER_PATH " + ConfigProperties.prop.getProperty("WEBDRIVER_PATH"));
        System.setProperty(ConfigProperties.prop.getProperty("WEBDRIVER"),
                ConfigProperties.prop.getProperty("WEBDRIVER_PATH"));

        switch (browserType) {
            case CHROME:
                log.info("Create ChromeDriver");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                log.info("Create FirefoxDriver");
                driver = new FirefoxDriver();
                break;
            default: throw new RuntimeException("Can't create driver for " + browserType);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        return driver;
    }
}
