package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import aqajava.models.PostData;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;

@Log4j
public class PostsPage extends BasePage {

    private static final By ADD_NEW_POST_BUTTON = By.xpath("//div[@class='wrap']/a[contains(text(),'Add New Post')]");
    private static final By POST_UPDATED_SNACK_BAR = By.xpath("//div//div[contains(text(),'Post updated')]");
    private static final By VIEW_POST_BUTTON_AFTER_CREATE = By.xpath("//div[@class='post-publish-panel__postpublish-buttons']/a[text()='View Post']");
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

    public PostsPage(AppManager app) {
        super(app);
    }

    public void openPosts() {
        log.debug("Open Posts");
        app.getMainMenu().clickOnItem("Posts");
    }

    public void createPostAndSaveItAsDraft(PostData postData) {
        log.debug("Create post and save it as draft");
        openPosts();
        initPostCreation();
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(TITLE_FIELD, postData.getPostTitle());
        fillFormsField(CONTENT_FIELD, postData.getPostContent());
        switchToDefaultContent();
        saveAsDraft();
    }

    public PostData getCreatedSavingAsDraftPostData(PostData postData) {
        log.debug("Get title and content of created saving as draft post, based on " + postData);
        PostData actualPostData = new PostData();
        openPosts();
        clickOnSubItem("Draft");
        searchTitleInTableByTitle(postData.getPostTitle());
        hoverRowInTableByTitle(postData.getPostTitle());
        clickOnItemInRowActionsMenu("Preview", 0);
        app.getWaitings().waitForElement(HR_TO_WAIT);
        actualPostData.setPostTitle(getElementTextByLocator(MAIN_H1));
        actualPostData.setPostContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPostData;
    }

    public void createPostAndPublishIt(PostData postData) {
        log.debug("Create post and publish it");
        openPosts();
        initPostCreation();
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(TITLE_FIELD, postData.getPostTitle());
        fillFormsField(CONTENT_FIELD, postData.getPostContent());
        switchToDefaultContent();
        publishPost();
    }

    public PostData getCreatedPublishedPostData(PostData postData) {
        log.debug("Get title and content of created published post, based on " + postData);
        PostData actualPostData = new PostData();
        app.getAuthentication().logOut();
        openCreatedPost(postData);
        actualPostData.setPostTitle(getElementTextByLocator(MAIN_H1));
        actualPostData.setPostContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPostData;
    }

    public void editSavingAsDraftPost(int index, PostData postData) {
        log.debug("Edit saving as draft post with table's list index " + index + " and new data " + postData);
        openPosts();
        if (!isSubItemPresent("Draft")) {
            log.debug("Create post and save it as draft");
            createPostAndSaveItAsDraft(new PostData());
            openPosts();
        }
        clickOnSubItem("Draft");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Edit", index);
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(CONTENT_FIELD, postData.getPostContent());
        fillFormsField(TITLE_FIELD, postData.getPostTitle());
        switchToDefaultContent();
        saveAsDraft();
    }

    public PostData getEditedSavingAsDraftPostData(PostData postData) {
        log.debug("Get title and content of edited saving as draft post");
        PostData actualPostData = new PostData();
        openPosts();
        clickOnSubItem("Draft");
        searchTitleInTableByTitle(postData.getPostTitle());
        hoverRowInTableByTitle(postData.getPostTitle());
        clickOnItemInRowActionsMenu("Preview", 0);
        app.getWaitings().waitForElement(HR_TO_WAIT);
        actualPostData.setPostTitle(getElementTextByLocator(MAIN_H1));
        actualPostData.setPostContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPostData;
    }

    public String editPublishedPost(int index, PostData postData) {
        log.debug("Edit published post with table's list index " + index + " and new data " + postData +
                " and also return edited post's Url");
        openPosts();
        if (!isSubItemPresent("Published")) {
            log.debug("Create post and publish it");
            createPostAndPublishIt(new PostData());
            openPosts();
        }
        clickOnSubItem("Published");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Edit", index);
        switchToFrame(FRAME_CREATE_OR_EDIT);
        fillFormsField(CONTENT_FIELD, postData.getPostContent());
        fillFormsField(TITLE_FIELD, postData.getPostTitle());
        switchToDefaultContent();
        String editedPostUrl = getEditedUrl();
        updatePost();
        return editedPostUrl;
    }

    public PostData getEditedPublishedPostData(String editedPostUrl) {
        log.debug("Get title and content of edited published post");
        PostData actualPostData = new PostData();
        app.getAuthentication().logOut();
        driver.get(editedPostUrl);
        actualPostData.setPostTitle(getElementTextByLocator(MAIN_H1));
        actualPostData.setPostContent(getElementTextByLocator(ENTRY_CONTENT));
        return actualPostData;
    }

    public void removeSavingAsDraftPost(int index) {
        log.debug("Remove saving as draft post with table's list index " + index);
        openPosts();
        if (!isSubItemPresent("Draft")) {
            log.debug("Create post and save it as draft");
            createPostAndSaveItAsDraft(new PostData());
            openPosts();
        }
        clickOnSubItem("Draft");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Trash", index);
        app.getWaitings().waitForElement(MOVED_TO_TRASH_MESSAGE);
    }

    public int getSavingAsDraftPostsCount() {
        log.debug("Getting count of saving in draft posts");
        openPosts();
        if (!isSubItemPresent("Draft")) {
            return 0;
        } else {
            return countOfSubItems("Draft");
        }
    }

    public void removePublishedPost(int index) {
        log.debug("Remove published post with table's list index " + index);
        openPosts();
        if (!isSubItemPresent("Published")) {
            log.debug("Create post and publish it");
            createPostAndPublishIt(new PostData());
            openPosts();
        }
        clickOnSubItem("Published");
        hoverRowInTableByIndex(index);
        clickOnItemInRowActionsMenu("Trash", index);
        app.getWaitings().waitForElement(MOVED_TO_TRASH_MESSAGE);
    }

    public int getPublishedPostsCount() {
        log.debug("Getting count of published posts");
        openPosts();
        if (!isSubItemPresent("Published")) {
            return 0;
        } else {
            return countOfSubItems("Published");
        }
    }

    //------------------------------------------------

    private void initPostCreation() {
        log.debug("Init post creation by clicking on locator " + ADD_NEW_POST_BUTTON);
        driver.findElement(ADD_NEW_POST_BUTTON).click();
        app.getWaitings().waitForElement(FRAME_CREATE_OR_EDIT);
    }

    private void publishPost() {
        log.debug("Publish post by clicking on buttons with locator " + PUBLISH_BUTTON + " and locator " + LAST_STEP_PUBLISH_BUTTON);
        driver.findElement(PUBLISH_BUTTON).click();
        app.getWaitings().waitForElementClickable(LAST_STEP_PUBLISH_BUTTON).click();
        app.getWaitings().waitForElementClickable(VIEW_POST_BUTTON_AFTER_CREATE);
    }

    private void openCreatedPost(PostData postData) {
        log.debug("Opening created post with postdata " + postData);
        String subUrl = postData.getPostTitle().replaceAll(" ", "-").toLowerCase();
        driver.get(baseUrl + subUrl);
        app.getWaitings().waitForElement(HR_TO_WAIT);
    }

    private void updatePost() {
        log.debug("Click on Update button with locator " + UPDATE_BUTTON);
        driver.findElement(UPDATE_BUTTON).click();
        app.getWaitings().waitForElement(POST_UPDATED_SNACK_BAR);
    }
}
