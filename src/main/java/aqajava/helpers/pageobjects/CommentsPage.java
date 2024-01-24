package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import aqajava.models.CommentData;
import aqajava.models.LoginData;
import aqajava.models.PostData;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

@Log4j
public class CommentsPage extends BasePage {

    private static final String SUB_ITEM_PATTERN = "//ul[@class='subsubsub']//a[contains(text(), '%s')]";
    private static final String COMMENT_IN_TABLE_PATTERN = "//table[@class='wp-list-table widefat fixed striped table-view-list comments']" +
            "//p[contains(text(), '%s')]";
    private static final String COMMENT_TEXT_IN_POST_BY_COMMENT_PATTERN = "//ol[@class='commentlist']/li//" +
            "p[text()='%s']/parent::div/parent::article/div/p";
    private static final String COMMENT_NAME_IN_POST_BY_COMMENT_PATTERN = "//ol[@class='commentlist']/li//" +
            "p[text()='%s']/parent::div/parent::article/footer/div/b";

    private static final By COMMENT_SEARCH_INPUT = By.cssSelector("#comment-search-input");
    private static final By TABLE_COMMENTS_LIST = By.cssSelector("#the-comment-list tr");
    private static final By COMMENT_FORM = By.cssSelector("#comment");
    private static final By COMMENT_NAME = By.cssSelector("#author");
    private static final By COMMENT_EMAIL = By.cssSelector("#email");
    private static final By SUBMIT_CREATE_COMMENT_BUTTON = By.cssSelector("#submit");
    private static final By COMMENT_WAIT_MODERATION = By.cssSelector("em.comment-awaiting-moderation");
    private static final By COMMENTS_TABLE_LIST = By.cssSelector("tbody#the-comment-list tr");
    private static final By EDIT_COMMENT_CONTENT = By.cssSelector("textarea[name='content']");
    private static final By EDIT_COMMENT_NAME = By.cssSelector("input[name='newcomment_author']");
    private static final By EDIT_COMMENT_EMAIL = By.cssSelector("input[name='newcomment_author_email']");
    private static final By SUBMIT_UPDATE_COMMENT_BUTTON = By.cssSelector("input[name='save']");
    private static final By H1_COMMENTS = By.xpath("//h1[@class='wp-heading-inline'][contains(text(), 'Comments')]");
    private static final By FOUND_COMMENT_TEXT_IN_TABLE = By.cssSelector("tbody#the-comment-list p");
    private static final By FOUND_COMMENT_NAME_IN_TABLE = By.cssSelector("tbody#the-comment-list strong");
    private static final By FOUND_COMMENT_EMAIL_IN_TABLE = By.partialLinkText("@");
    private static final By HR_TO_WAIT = By.cssSelector("hr.wp-block-separator");
    private static final By POSTS_LIST_ON_MAIN_PAGE = By.cssSelector("main ul li");
    private static final By POST_TITLE_LINK_ON_MAIN_PAGE = By.cssSelector("a");
    private static final By SEARCH_SUBMIT = By.cssSelector("#search-submit");

    public CommentsPage(AppManager app) {
        super(app);
    }

    public void createByAnyVisitorComment(int index, CommentData commentData, LoginData loginData) {
        log.debug("Create comment by any visitor, based on " + commentData);
        app.getAuthentication().logOut();
        app.getMainPage().openMainPage();
        if (countOfPostsOnMainPage() == 0) {
            app.getAuthentication().openLoginPage();
            app.getAuthentication().login(loginData);
            log.debug("Create post and publish it");
            app.getPostsPage().createPostAndPublishIt(new PostData());
            app.getAuthentication().logOut();
            app.getMainPage().openMainPage();
        }
        openPostOnMainPage(index);
        fillFormsField(COMMENT_FORM, commentData.getComment());
        fillFormsField(COMMENT_NAME, commentData.getName());
        fillFormsField(COMMENT_EMAIL, commentData.getEmail());
        submitCommentCreation();
    }

    public CommentData getCreatedByAnyVisitorCommentData(int index, CommentData commentData, LoginData loginData) {
        log.debug("Get comment text and comment name of created by any visitor comment to post with index " + index);
        CommentData actualCommentData = new CommentData();
        app.getAuthentication().openLoginPage();
        app.getAuthentication().login(loginData);
        openComments();
        clickOnCommentsSubItem("Pending");
        findCommentInTableByCommentText(commentData.getComment());
        hoverCommentInTableByIndex(0);
        clickOnItemInRowActionsMenu("Approve", 0);
        app.getAuthentication().logOut();
        app.getMainPage().openMainPage();
        openPostOnMainPage(index);
        actualCommentData.setComment(getCommentTextInPost(commentData.getComment()));
        actualCommentData.setName(getCommentNameInPost(commentData.getComment()));
        return actualCommentData;
    }

    public void editComment(int index, CommentData commentData, LoginData loginData) {
        log.debug("Edit comment by index " + index);
        openComments();
        if (getSubItemCommentsCount("All") == 0) {
            log.debug("Create comment");
            createByAnyVisitorComment(0, new CommentData(), loginData);
            app.getAuthentication().openLoginPage();
            app.getAuthentication().login(loginData);
            openComments();
        }
        hoverCommentInTableByIndex(index);
        clickOnItemInRowActionsMenu("Edit", index);
        fillFormsField(EDIT_COMMENT_CONTENT, commentData.getComment());
        fillFormsField(EDIT_COMMENT_NAME, commentData.getName());
        fillFormsField(EDIT_COMMENT_EMAIL, commentData.getEmail());
        submitCommentEditing();
    }

    public CommentData getEditedCommentData(CommentData commentData) {
        log.debug("Getting edited comment data");
        CommentData actualCommentData = new CommentData();
        openComments();
        findCommentInTableByCommentText(commentData.getComment());
        actualCommentData.setComment(getFoundCommentTextInTable());
        actualCommentData.setName(getFoundCommentNameInTable());
        actualCommentData.setEmail(getFoundCommentEmailInTable());
        return actualCommentData;
    }

    public void markUnapprovedCommentAsApproved(int index, LoginData loginData) {
        log.debug("Mark unapproved comment with index " + index + " as approved");
        openComments();
        if (getSubItemCommentsCount("Pending") == 0) {
            log.debug("Create comment");
            createByAnyVisitorComment(0, new CommentData(), loginData);
            app.getAuthentication().openLoginPage();
            app.getAuthentication().login(loginData);
            openComments();
        }
        clickOnSubItem("Pending");
        hoverRowInCommentsTableByIndex(index);
        clickOnItemInRowActionsMenu("Approve", index);
    }

    public int getApprovedCommentsCount() {
        log.debug("Getting count of approved comments");
        openComments();
        return getSubItemCommentsCount("Approved");
    }

    public void markCommentAsSpam(int index, LoginData loginData) {
        log.debug("Mark comment with index " + index + " as spam");
        openComments();
        if (getSubItemCommentsCount("All") == 0) {
            log.debug("Create comment");
            createByAnyVisitorComment(0, new CommentData(), loginData);
            app.getAuthentication().openLoginPage();
            app.getAuthentication().login(loginData);
            openComments();
        }
        hoverRowInCommentsTableByIndex(index);
        clickOnItemInRowActionsMenu("Spam", index);
    }

    public int getSpamCommentsCount() {
        log.debug("Getting count of spam comments");
        openComments();
        return getSubItemCommentsCount("Spam");
    }

    public void markCommentAsTrash(int index, LoginData loginData) {
        log.debug("Mark comment with index " + index + " as trash");
        openComments();
        if (getSubItemCommentsCount("All") == 0) {
            log.debug("Create comment");
            createByAnyVisitorComment(0, new CommentData(), loginData);
            app.getAuthentication().openLoginPage();
            app.getAuthentication().login(loginData);
            openComments();
        }
        hoverRowInCommentsTableByIndex(index);
        clickOnItemInRowActionsMenu("Trash", index);
    }

    public int getTrashCommentsCount() {
        log.debug("Getting count of trash comments");
        openComments();
        return getSubItemCommentsCount("Trash");
    }

    //-----------------------------------

    private void openComments() {
        log.debug("Open Comments");
        app.getMainMenu().clickOnItem("Comments");
    }

    private void clickOnCommentsSubItem(String itemName) {
        log.debug(String.format("Click on Comments sub item %s with locator %s", itemName, String.format(SUB_ITEM_PATTERN, itemName)));
        driver.findElement(By.xpath(String.format(SUB_ITEM_PATTERN, itemName))).click();
    }

    private void findCommentInTableByCommentText(String comment) {
        log.debug("Find in Comment's table comment with comment's text " + comment + ", using search form with locator " + COMMENT_SEARCH_INPUT);
        fillFormsField(COMMENT_SEARCH_INPUT, comment);
        log.debug("Click on comment search submit button with locator " + SEARCH_SUBMIT);
        driver.findElement(SEARCH_SUBMIT).click();
    }

    private void hoverCommentInTableByIndex(int index) {
        log.debug("Hover in Comment's table with locator " + TABLE_COMMENTS_LIST +
                " over the comment with index " + index);
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(TABLE_COMMENTS_LIST).get(index))
                .build().perform();
    }

    private void hoverCommentInTableByCommentText(String comment) {
        log.debug(String.format("Hover in Comments table over the comment with comment's text %s and locator %s", comment, String.format(COMMENT_IN_TABLE_PATTERN, comment)));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(String.format(COMMENT_IN_TABLE_PATTERN, comment))))
                .build().perform();
    }

    private void openPostOnMainPage(int index) {
        log.debug("Open on main page post by index " + index);
        driver.findElements(POSTS_LIST_ON_MAIN_PAGE).get(index).findElement(POST_TITLE_LINK_ON_MAIN_PAGE).click();
        app.getWaitings().waitForElement(HR_TO_WAIT);
    }

    private void submitCommentCreation() {
        log.debug("Submit comment creation by clicking on button with locator " + SUBMIT_CREATE_COMMENT_BUTTON);
        driver.findElement(SUBMIT_CREATE_COMMENT_BUTTON).click();
        app.getWaitings().waitForElement(COMMENT_WAIT_MODERATION);
    }

    private void submitCommentEditing() {
        log.debug("Submit comment editing by clicking on button with locator " + SUBMIT_UPDATE_COMMENT_BUTTON);
        driver.findElement(SUBMIT_UPDATE_COMMENT_BUTTON).click();
        app.getWaitings().waitForElement(H1_COMMENTS);
    }

    private String getCommentTextInPost(String comment) {
        log.debug(String.format("Get in post comment text with locator %s", String.format(COMMENT_TEXT_IN_POST_BY_COMMENT_PATTERN, comment)));
        return driver.findElement(By.xpath(String.format(COMMENT_TEXT_IN_POST_BY_COMMENT_PATTERN, comment))).getText();
    }

    private String getCommentNameInPost(String comment) {
        log.debug(String.format("Get in post comment name with locator %s", String.format(COMMENT_NAME_IN_POST_BY_COMMENT_PATTERN, comment)));
        return driver.findElement(By.xpath(String.format(COMMENT_NAME_IN_POST_BY_COMMENT_PATTERN, comment))).getText();
    }

    private int getSubItemCommentsCount(String itemName) {
        log.debug("Getting count of sub item " + itemName + " comments");
        openComments();
        return countOfSubItems(itemName);
    }

    private void hoverRowInCommentsTableByIndex(int index) {
        log.debug("Hover in comments table with locator " + COMMENTS_TABLE_LIST +
                " over the row with index " + index);
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElements(COMMENTS_TABLE_LIST).get(index))
                .build().perform();
    }

    private String getFoundCommentTextInTable() {
        log.debug("Getting comment text of found comment in table");
        return driver.findElement(FOUND_COMMENT_TEXT_IN_TABLE).getText();
    }

    private String getFoundCommentNameInTable() {
        log.debug("Getting comment name of found comment in table");
        return driver.findElement(FOUND_COMMENT_NAME_IN_TABLE).getText();
    }

    private String getFoundCommentEmailInTable() {
        log.debug("Getting comment email of found comment in table");
        return driver.findElement(FOUND_COMMENT_EMAIL_IN_TABLE).getText();
    }
}
