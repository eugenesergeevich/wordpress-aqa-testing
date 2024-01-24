package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;

@Log4j
public class MainMenu extends BasePage {

    private static final String ITEM_PATTERN = "//ul[@id='adminmenu']//div[contains(text(), '%s')]";

    public MainMenu(AppManager app) {
        super(app);
    }

    public void clickOnItem(String itemName) {
        log.debug(String.format("Click on Main menu item %s with locator %s", itemName, String.format(ITEM_PATTERN, itemName)));
        driver.findElement(By.xpath(String.format(ITEM_PATTERN, itemName))).click();
    }

    public boolean isItemPresent(String itemName) {
        try {
            log.debug(String.format("Try to find in Main menu item %s with locator %s", itemName, String.format(ITEM_PATTERN, itemName)));
            driver.findElement(By.xpath(String.format(ITEM_PATTERN, itemName)));
            return true;
        } catch (NoSuchElementException e) {
            log.warn(String.format("Item %s with locator %s is not present in Main menu", itemName, String.format(ITEM_PATTERN, itemName)));
            return false;
        }
    }

    public void hoverOverItem(String itemName) {
        log.debug(String.format("Hover to Main menu item %s with locator %s", itemName, String.format(ITEM_PATTERN, itemName)));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(String.format(ITEM_PATTERN, itemName))))
                .build().perform();
    }
}
