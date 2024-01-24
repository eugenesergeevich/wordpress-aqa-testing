package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import aqajava.models.PageData;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;

@Log4j
public class PagesPage extends BasePage {

    private static final By ADD_NEW_PAGE_BUTTON = By.xpath("//div[@class='wrap']/a[contains(text(),'Add New Page')]");
    private static final By PAGE_UPDATED_SNACK_BAR = By.xpath("//div//div[contains(text(),'Page updated')]");
    private static final By VIEW_PAGE_BUTTON_AFTER_CREATE = By.xpath("//div[@class='post-publish-panel__postpublish-buttons']/a[text()='View Page']");
    private static final By TITLE_FIELD = By.cssSelector("h1.wp-block");
    private static final By CONTENT_FIELD = By.cssSelector("div.is-root-container p");
    private static final By FRAME_CREATE_OR_EDIT = By.cssSelector("iframe[name='editor-canvas']");
    private static final By PUBLISH_BUTTON = By.xpath("//button[text()='Publish']");
    private static final By UPDATE_BUTTON = By.xpath("//button[text()='Update']");
    private static final By LAST_STEP_PUBLISH_BUTTON = By.xpath("//div[@class='editor-post-publish-panel__header-publish-button']/button[text()='Publish']");
    private static final By MOVED_TO_TRASH_MESSAGE = By.xpath("//div[@id='message']/p[contains(text(), 'moved to the Trash')]");
    private static final By MAIN_H1 = By.cssSelector("main h1");
    private static final By ENTRY_CONTENT = By.cssSelector("div.entry-content");
    private static final By HR_TO_WAIT = By.cssSelector("hr.wp-block-separator");

    public PagesPage(AppManager app) {
        super(app);
    }

    public void openPages() {
        log.debug("Open Pages");
        app.getMainMenu().clickOnItem("Pages");
    }

    public void createPageAndSaveItAsDraft(PageData pageData) {
        log.debug("Create page and save it as draft");
        openPages();
        initPageCreation();
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(TITLE_FIELD, pageData.getPageTitle());
        fillFormsField(CONTENT_FIELD, pageData.getPageContent());
        switchToDefaultContent();
        saveAsDraft();
    }

    public PageData getCreatedSavingAsDraftPageData(PageData pageData) {
        log.debug("Get title and content of created saving as draft page, based on " + pageData);
        PageData actualPageData = new PageData();
        openPages();
        clickOnSubItem("Draft");
        searchTitleInTableByTitle(pageData.getPageTitle());
        hoverRowInTableByTitle(pageData.getPageTitle());
        clickOnItemInRowActionsMenu("Preview", 0);
        app.getWaitings().waitForElement(HR_TO_WAIT);
        actualPageData.setPageTitle(getElementTextByLocator(MAIN_H1));
        actualPageData.setPageContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPageData;
    }

    public void createPageAndPublishIt(PageData pageData) {
        log.debug("Create page and publish it");
        openPages();
        initPageCreation();
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(TITLE_FIELD, pageData.getPageTitle());
        fillFormsField(CONTENT_FIELD, pageData.getPageContent());
        switchToDefaultContent();
        publishPage();
    }

    public PageData getCreatedPublishedPageData(PageData pageData) {
        log.debug("Get title and content of created published page, based on " + pageData);
        PageData actualPageData = new PageData();
        app.getAuthentication().logOut();
        openCreatedPage(pageData);
        actualPageData.setPageTitle(getElementTextByLocator(MAIN_H1));
        actualPageData.setPageContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPageData;
    }

    public void editSavingAsDraftPage(int index, PageData pageData) {
        log.debug("Edit saving as draft page with table's list index " + index + " and new data " + pageData);
        openPages();
        if (!isSubItemPresent("Draft")) {
            log.debug("Create page and save it as draft");
            createPageAndSaveItAsDraft(new PageData());
            openPages();
        }
        clickOnSubItem("Draft");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Edit", index);
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(CONTENT_FIELD, pageData.getPageContent());
        fillFormsField(TITLE_FIELD, pageData.getPageTitle());
        switchToDefaultContent();
        saveAsDraft();
    }

    public PageData getEditedSavingAsDraftPageData(PageData pageData) {
        log.debug("Get title and content of edited saving as draft page");
        PageData actualPageData = new PageData();
        openPages();
        clickOnSubItem("Draft");
        searchTitleInTableByTitle(pageData.getPageTitle());
        hoverRowInTableByTitle(pageData.getPageTitle());
        clickOnItemInRowActionsMenu("Preview", 0);
        app.getWaitings().waitForElement(HR_TO_WAIT);
        actualPageData.setPageTitle(getElementTextByLocator(MAIN_H1));
        actualPageData.setPageContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPageData;
    }

    public String editPublishedPage(int index, PageData pageData) {
        log.debug("Edit published page with table's list index " + index + " and new data " + pageData +
                " and also return edited page's Url");
        openPages();
        if (!isSubItemPresent("Published")) {
            log.debug("Create page and publish it");
            createPageAndPublishIt(new PageData());
            openPages();
        }
        clickOnSubItem("Published");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Edit", index);
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(CONTENT_FIELD, pageData.getPageContent());
        fillFormsField(TITLE_FIELD, pageData.getPageTitle());
        switchToDefaultContent();
        String editedPageUrl = getEditedUrl();
        updatePage();
        return editedPageUrl;
    }

    public PageData getEditedPublishedPageData(String editedPageUrl) {
        log.debug("Get title and content of edited published page");
        PageData actualPageData = new PageData();
        app.getAuthentication().logOut();
        driver.get(editedPageUrl);
        actualPageData.setPageTitle(getElementTextByLocator(MAIN_H1));
        actualPageData.setPageContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPageData;
    }

    public void removeSavingAsDraftPage(int index) {
        log.debug("Remove saving as draft page with table's list index " + index);
        openPages();
        if (!isSubItemPresent("Draft")) {
            log.debug("Create page and save it as draft");
            createPageAndSaveItAsDraft(new PageData());
            openPages();
        }
        clickOnSubItem("Draft");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Trash", index);
        app.getWaitings().waitForElement(MOVED_TO_TRASH_MESSAGE);
    }

    public int getSavingAsDraftPagesCount() {
        log.debug("Getting count of saving in draft pages");
        openPages();
        if (!isSubItemPresent("Draft")) {
            return 0;
        } else {
            return countOfSubItems("Draft");
        }
    }

    public void removePublishedPage(int index) {
        log.debug("Remove published page with table's list index " + index);
        openPages();
        if (!isSubItemPresent("Published")) {
            log.debug("Create page and publish it");
            createPageAndPublishIt(new PageData());
            openPages();
        }
        clickOnSubItem("Published");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Trash", index);
        app.getWaitings().waitForElement(MOVED_TO_TRASH_MESSAGE);
    }

    public int getPublishedPagesCount() {
        log.debug("Getting count of published pages");
        openPages();
        if (!isSubItemPresent("Published")) {
            return 0;
        } else {
            return countOfSubItems("Published");
        }
    }

    //------------------------------------------------

    private void initPageCreation() {
        log.debug("Init page creation by clicking on locator " + ADD_NEW_PAGE_BUTTON);
        driver.findElement(ADD_NEW_PAGE_BUTTON).click();
        app.getWaitings().waitForElement(FRAME_CREATE_OR_EDIT);
    }

    private void publishPage() {
        log.debug("Publish page by clicking on buttons with locator " + PUBLISH_BUTTON + " and locator " + LAST_STEP_PUBLISH_BUTTON);
        driver.findElement(PUBLISH_BUTTON).click();
        app.getWaitings().waitForElementClickable(LAST_STEP_PUBLISH_BUTTON).click();
        app.getWaitings().waitForElementClickable(VIEW_PAGE_BUTTON_AFTER_CREATE);
    }

    private void updatePage() {
        log.debug("Click on Update button with locator " + UPDATE_BUTTON);
        driver.findElement(UPDATE_BUTTON).click();
        app.getWaitings().waitForElement(PAGE_UPDATED_SNACK_BAR);
    }

    private void openCreatedPage(PageData pageData) {
        log.debug("Opening created page with pagedata " + pageData);
        String subUrl = pageData.getPageTitle().replaceAll(" ", "-").toLowerCase();
        driver.get(baseUrl + subUrl);
        app.getWaitings().waitForElement(HR_TO_WAIT);
    }
}
