package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;

@Log4j
public class MediaPage extends BasePage {

    private static final By MEDIA_SEARCH_INPUT = By.cssSelector("#media-search-input");
    private static final By FOUND_MEDIA_FILE = By.cssSelector("div.thumbnail");
    private static final By MEDIA_FILES_LIST = By.cssSelector("div.attachments-wrapper ul li");
    private static final By CLOSE_ATTACHMENT_DETAILS = By.cssSelector("button.media-modal-close");
    private static final By ADD_NEW_MEDIA_FILE_BUTTON = By.xpath("//div[@id='wp-media-grid']//a[contains(text(), 'Add New Media File')]");
    private static final By UPLOAD_FILES_INPUT = By.cssSelector("input[type='file']");
    private static final By FIRST_MEDIA_FILE_ITEM = By.xpath("//div[@class='attachments-wrapper']//ul/li[1]");
    private static final By DELETE_MEDIA_FILE_BUTTON = By.cssSelector("button.button-link.delete-attachment");
    private static final By MEDIA_FILES_COUNT = By.cssSelector("div.load-more-wrapper p.load-more-count");

    public MediaPage(AppManager app) {
        super(app);
    }

    public void openMedia() {
        log.debug("Open Media");
        app.getMainMenu().clickOnItem("Media");
    }

    public void addMediaFileToLibrary(String mediaFilePath) {
        log.debug("Add to library media file with path " + mediaFilePath);
        openMedia();
        initAddMediaFile();
        uploadMediaFile(mediaFilePath);
    }

    public void removeMediaFileFromLibrary(int index, String mediaFilePath) {
        log.debug("Remove media file from library");
        if (getMediaFilesCount() == 0) {
            log.debug("Add to library media file with path " + mediaFilePath);
            addMediaFileToLibrary(mediaFilePath);
        }
        openMedia();
        clickOnMediaFileInListByIndex(index);
        deleteMediaFile();
    }

    public int getMediaFilesCount() {
        log.debug("Getting count of media files in library, using locators " + MEDIA_FILES_LIST +
                " and " + MEDIA_FILES_COUNT);
        openMedia();
        try {
            driver.findElements(MEDIA_FILES_LIST);
            return Integer.parseInt(driver.findElement(MEDIA_FILES_COUNT).getText().split(" ")[3]);
        } catch (Exception ex) {
            log.warn("List of media files have no elements");
            return 0;
        }
    }

    //-----------------------------------

    private void clickOnMediaFileInListByIndex(int index) {
        log.debug("Click on file with index " + index + " in Media's list with locator " + MEDIA_FILES_LIST);
        driver.findElements(MEDIA_FILES_LIST).get(index).click();
    }

    private void initAddMediaFile() {
        log.debug("Initialization adding new media file to library by clicking on button " + ADD_NEW_MEDIA_FILE_BUTTON);
        driver.findElement(ADD_NEW_MEDIA_FILE_BUTTON).click();
    }

    private void uploadMediaFile(String mediaFilePath) {
        log.debug("Upload to library media file with path " + mediaFilePath +
                ", using input with locator " + UPLOAD_FILES_INPUT);
        driver.findElement(UPLOAD_FILES_INPUT).sendKeys(mediaFilePath);
        driver.findElement(FIRST_MEDIA_FILE_ITEM).click();
        app.getWaitings().waitForElementClickable(ADD_NEW_MEDIA_FILE_BUTTON);
    }

    private void deleteMediaFile() {
        log.debug("Click on button with locator " + DELETE_MEDIA_FILE_BUTTON);
        driver.findElement(DELETE_MEDIA_FILE_BUTTON).click();
        driver.switchTo().alert().accept();
    }

    private void findMediaFile(String mediaFileName) {
        log.debug("Find in Media file with name " + mediaFileName + ", using search form with locator " + MEDIA_SEARCH_INPUT);
        fillFormsField(MEDIA_SEARCH_INPUT, mediaFileName);
    }

    private void clickOnFoundMediaFile() {
        log.debug("Click on file, found in Media's list with locator " + FOUND_MEDIA_FILE);
        driver.findElement(FOUND_MEDIA_FILE).click();
    }

    private void closeAttachmentDetailsWindow() {
        log.debug("Click on button with locator " + CLOSE_ATTACHMENT_DETAILS + " to close media file's details window");
        driver.findElement(CLOSE_ATTACHMENT_DETAILS).click();
    }
}
