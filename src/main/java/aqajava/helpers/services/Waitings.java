package aqajava.helpers.services;

import aqajava.webdriver.Browser;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

@Log4j
public class Waitings {

    private WebDriver driver = Browser.getDriver();
    private static int DEFAULT_WAIT_IN_SECONDS = 30;

    public WebElement waitForElement(By locator) {
        log.debug("Wait for element is present with locator " + locator);
        return new WebDriverWait(driver, DEFAULT_WAIT_IN_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForElements(By locator) {
        log.debug("Wait for elements are visible with locator " + locator);
        org.openqa.selenium.support.ui.Wait wait = new WebDriverWait(driver, DEFAULT_WAIT_IN_SECONDS);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }

    public WebElement waitForElementClickable(By locator) {
        log.debug("Wait for element is clickable with locator " + locator);
        return new WebDriverWait(driver, DEFAULT_WAIT_IN_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

//    public WebElement waitForElement(By locator) {
//        org.openqa.selenium.support.ui.Wait wait = new WebDriverWait(driver, DEFAULT_WAIT_IN_SECONDS);
//        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//        return driver.findElement(locator);
//    }

//    public void sleeping(int seconds) {
//        try {
//            Thread.sleep(seconds * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
