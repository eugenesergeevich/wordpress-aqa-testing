package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import aqajava.webdriver.Browser;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

@Log4j
public class BasePage {

    private static final String SUB_ITEM_PATTERN = "//ul[@class='subsubsub']//a[contains(text(), '%s')]";
    private static final String TITLE_IN_TABLE_PATTERN = "//table[contains(@class, 'wp-list-table widefat fixed striped table-view-list')]" +
            "//td//a[contains(text(), '%s')]";
    private static final String HOVER_ROW_ACTIONS_ITEM_IN_TABLE_PATTERN = "//div[@class='row-actions']//a[contains(text(), '%s')]['%s']";

    private static final By SEARCH_INPUT = By.cssSelector("#post-search-input");
    private static final By SEARCH_SUBMIT = By.cssSelector("#search-submit");
    private static final By TABLE_LIST = By.cssSelector("tbody#the-list tr");
    private static final By SAVE_DRAFT_BUTTON = By.xpath("//button[text()='Save draft']");
    private static final By VIEW_PRIVIEW_SNACK_BAR = By.xpath("//div//div[contains(text(),'Draft saved')]/a[contains(text(), 'View Preview')]");
    private static final By EDITED_URL = By.cssSelector("div.components-dropdown.edit-post-post-url__dropdown");
    private static final By SEARCH_RESULTS_FOR = By.xpath("//div/span[contains(text(), 'Search results for:')]");
    private static final By POSTS_LIST_ON_MAIN_PAGE = By.cssSelector("main ul li");

    protected WebDriver driver = Browser.getDriver();
    protected AppManager app;
    protected String baseUrl;

    protected BasePage(AppManager app) {
        this.app = app;
        this.baseUrl = app.baseUrl;
    }

    protected boolean isElementPresent(By locator) {
        try {
            log.debug("Try to find DOM element with locator " + locator);
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            log.warn("Element " + locator + " is not present in DOM");
            return false;
        }
    }

    protected void fillFormsField(By locator, String text) {
        log.debug("Fill form's field with locator " + locator + " and data " + text);
        driver.findElement(locator).click();
        driver.findElement(locator).sendKeys(Keys.CONTROL, "a");
        driver.findElement(locator).sendKeys(Keys.DELETE);
        driver.findElement(locator).sendKeys(text);
    }

    protected void clickOnSubItem(String itemName) {
        log.debug(String.format("Click on Pages sub item %s with locator %s", itemName, String.format(SUB_ITEM_PATTERN, itemName)));
        driver.findElement(By.xpath(String.format(SUB_ITEM_PATTERN, itemName))).click();
    }

    protected boolean isSubItemPresent(String itemName) {
        try {
            log.debug(String.format("Try to find in DOM sub item %s with locator %s", itemName, String.format(SUB_ITEM_PATTERN, itemName)));
            driver.findElement(By.xpath(String.format(SUB_ITEM_PATTERN, itemName)));
            return true;
        } catch (NoSuchElementException e) {
            log.warn(String.format("Sub item %s with locator %s is not present in DOM", itemName, String.format(SUB_ITEM_PATTERN, itemName)));
            return false;
        }
    }

    protected void hoverRowInTableByIndex(int index) {
        log.debug("Hover in table with locator " + TABLE_LIST + " over the row with index " + index);
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(TABLE_LIST).get(index))
                .build().perform();
    }

    protected void hoverRowInTableByTitle(String title) {
        log.debug(String.format("Hover in table over the row with title %s and locator %s", title, String.format(TITLE_IN_TABLE_PATTERN, title)));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(String.format(TITLE_IN_TABLE_PATTERN, title))))
                .build().perform();
    }

    protected void clickOnItemInRowActionsMenu(String itemName, int index) {
        index++;
        log.debug(String.format("Click on item %s in row actions menu with locator %s", itemName, String.format(HOVER_ROW_ACTIONS_ITEM_IN_TABLE_PATTERN, itemName, index)));
        driver.findElement(By.xpath(String.format(HOVER_ROW_ACTIONS_ITEM_IN_TABLE_PATTERN, itemName, index))).click();
    }

    protected void searchTitleInTableByTitle(String title) {
        log.debug("Find in table title " + title + ", using search form with locator " + SEARCH_INPUT);
        fillFormsField(SEARCH_INPUT, title);
        log.debug("Click on search submit button with locator " + SEARCH_SUBMIT);
        driver.findElement(SEARCH_SUBMIT).click();
        app.getWaitings().waitForElement(SEARCH_RESULTS_FOR);
    }

    protected String getElementTextByLocator(By locator) {
        log.debug("Get text by locator " + locator);
        return driver.findElement(locator).getText();
    }

    protected void switchToFrame(By locator) {
        log.debug("Switch to frame with locator " + locator);
        driver.switchTo().frame(driver.findElement(locator));
    }

    protected void switchToDefaultContent() {
        log.debug("Switch from frame to default content");
        driver.switchTo().defaultContent();
    }

    protected void saveAsDraft() {
        log.debug("Click on Save draft button with locator " + SAVE_DRAFT_BUTTON);
        driver.findElement(SAVE_DRAFT_BUTTON).click();
        app.getWaitings().waitForElementClickable(VIEW_PRIVIEW_SNACK_BAR);
    }

    protected String getEditedUrl() {
        log.debug("Getting edited page's Url");
        return "https://" + driver.findElement(EDITED_URL).getText().trim();
    }

    protected int countOfSubItems(String itemName) {
        log.debug(String.format("Return count of elements in sub item %s with locator %s", itemName, String.format(SUB_ITEM_PATTERN, itemName)));
        int count = Integer.parseInt(driver.findElement(By.xpath(String.format(SUB_ITEM_PATTERN + "/span", itemName)))
                .getText().replaceAll("[()]", ""));
        return count;
    }

    protected int countOfPostsOnMainPage() {
        log.debug("Get posts count on main page");
        return driver.findElements(POSTS_LIST_ON_MAIN_PAGE).size();
    }

    protected void scrollByLocator(By locator) {
        log.debug("Scroll to element with locator " + locator);
        WebElement webElement = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement);
        actions.perform();
    }

    protected void scrollScriptByXpath(String xpath) {
        log.debug("Scroll to element with XPath selector " + xpath);
        ((JavascriptExecutor) driver).executeScript("document.evaluate(\"" + xpath + "\", document, null, " +
                "XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView()");
    }

    protected void scrollScriptPx(int scrollPx) {
        log.debug("Scroll down on " + scrollPx + " pixels");
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + scrollPx + ")");
    }
}
