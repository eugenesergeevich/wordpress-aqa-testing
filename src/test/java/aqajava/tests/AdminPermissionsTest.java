package aqajava.tests;

import aqajava.models.CommentData;
import aqajava.models.LoginData;
import aqajava.models.PageData;
import aqajava.models.PostData;
import aqajava.properties.ConfigProperties;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.nio.file.Paths;

@Log4j
public class AdminPermissionsTest extends BaseTest {

    {
        log.info("Load from properties file ADMIN_LOGIN " + ConfigProperties.prop.getProperty("ADMIN_LOGIN") +
                " and ADMIN_PASSWORD");
    }
    private LoginData loginData = new LoginData(ConfigProperties.prop.getProperty("ADMIN_LOGIN"),
            ConfigProperties.prop.getProperty("ADMIN_PASSWORD"));

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Pages tests")
    @Issue(value = "BugId-101") @TmsLink(value = "TestId-201")
    @Test(priority=1)
    public void testCreatePageAndSaveItAsDraft() {

        Allure.description("testCreatePageAndSaveItAsDraft, using  " + loginData.getUsername() + " account");
        log.info("testCreatePageAndSaveItAsDraft, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        PageData pageData = new PageData();
        app.getPagesPage().createPageAndSaveItAsDraft(pageData);
        PageData actualPageData = app.getPagesPage().getCreatedSavingAsDraftPageData(pageData);
        saveScreenShot();

        log.info("Assert, is actual page title " + actualPageData.getPageTitle() + " equals " +
                "expected page title " + pageData.getPageTitle());
        Assert.assertEquals(actualPageData.getPageTitle(), pageData.getPageTitle(),
                "Actual page title " + actualPageData.getPageTitle() + " is not equals " +
                        "expected page title " + pageData.getPageTitle());
        log.info("Assert, is actual page content " + actualPageData.getPageContent() + " equals " +
                "expected page content " + pageData.getPageContent());
        Assert.assertEquals(actualPageData.getPageContent(), pageData.getPageContent(),
                "Actual page content " + actualPageData.getPageContent() + " is not equals " +
                        "expected page content " + pageData.getPageContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Pages tests")
    @Issue(value = "BugId-102") @TmsLink(value = "TestId-202")
    @Test(priority=2)
    public void testCreatePageAndPublishIt() {

        Allure.description("testCreatePageAndPublishIt, using  " + loginData.getUsername() + " account");
        log.info("testCreatePageAndPublishIt, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        PageData pageData = new PageData();
        app.getPagesPage().createPageAndPublishIt(pageData);
        PageData actualPageData = app.getPagesPage().getCreatedPublishedPageData(pageData);
        saveScreenShot();

        log.info("Assert, is actual page title " + actualPageData.getPageTitle() + " equals " +
                "expected page title " + pageData.getPageTitle());
        Assert.assertEquals(actualPageData.getPageTitle(), pageData.getPageTitle(),
                "Actual page title " + actualPageData.getPageTitle() + " is not equals " +
                        "expected page title " + pageData.getPageTitle());
        log.info("Assert, is actual page content " + actualPageData.getPageContent() + " equals " +
                "expected page content " + pageData.getPageContent());
        Assert.assertEquals(actualPageData.getPageContent(), pageData.getPageContent(),
                "Actual page content " + actualPageData.getPageContent() + " is not equals " +
                        "expected page content " + pageData.getPageContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Pages tests")
    @Issue(value = "BugId-103") @TmsLink(value = "TestId-203")
    @Test(priority=3)
    public void testEditSavingAsDraftPage() {

        Allure.description("testEditSavingAsDraftPage, using  " + loginData.getUsername() + " account");
        log.info("testEditSavingAsDraftPage, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        PageData pageData = new PageData();
        app.getPagesPage().editSavingAsDraftPage(index, pageData);
        PageData actualPageData = app.getPagesPage().getEditedSavingAsDraftPageData(pageData);
        saveScreenShot();

        log.info("Assert, is actual page title " + actualPageData.getPageTitle() + " equals " +
                "expected page title " + pageData.getPageTitle());
        Assert.assertEquals(actualPageData.getPageTitle(), pageData.getPageTitle(),
                "Actual page title " + actualPageData.getPageTitle() + " is not equals " +
                        "expected page title " + pageData.getPageTitle());
        log.info("Assert, is actual page content " + actualPageData.getPageContent() + " equals " +
                "expected page content " + pageData.getPageContent());
        Assert.assertEquals(actualPageData.getPageContent(), pageData.getPageContent(),
                "Actual page content " + actualPageData.getPageContent() + " is not equals " +
                        "expected page content " + pageData.getPageContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Pages tests")
    @Issue(value = "BugId-104") @TmsLink(value = "TestId-204")
    @Test(priority=4)
    public void testEditPublishedPage() {

        Allure.description("testEditPublishedPage, using  " + loginData.getUsername() + " account");
        log.info("testEditPublishedPage, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        PageData pageData = new PageData();
        PageData actualPageData = app.getPagesPage().getEditedPublishedPageData(app.getPagesPage().editPublishedPage(index, pageData));
        saveScreenShot();

        log.info("Assert, is actual page title " + actualPageData.getPageTitle() + " equals " +
                "expected page title " + pageData.getPageTitle());
        Assert.assertEquals(actualPageData.getPageTitle(), pageData.getPageTitle(),
                "Actual page title " + actualPageData.getPageTitle() + " is not equals " +
                        "expected page title " + pageData.getPageTitle());
        log.info("Assert, is actual page content " + actualPageData.getPageContent() + " equals " +
                "expected page content " + pageData.getPageContent());
        Assert.assertEquals(actualPageData.getPageContent(), pageData.getPageContent(),
                "Actual page content " + actualPageData.getPageContent() + " is not equals " +
                        "expected page content " + pageData.getPageContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Pages tests")
    @Issue(value = "BugId-105") @TmsLink(value = "TestId-205")
    @Test(priority=5)
    public void testRemoveSavingAsDraftPage() {

        Allure.description("testRemoveSavingAsDraftPage, using  " + loginData.getUsername() + " account");
        log.info("testRemoveSavingAsDraftPage, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int pagesCount = app.getPagesPage().getSavingAsDraftPagesCount();
        if (pagesCount == 0) {
            pagesCount++;
        }
        app.getPagesPage().removeSavingAsDraftPage(index);
        int actualPagesCount = app.getPagesPage().getSavingAsDraftPagesCount();
        saveScreenShot();

        log.info("Assert, is actual saving as draft pages count " + actualPagesCount + " equals " +
                "expected saving as draft pages count " + pagesCount);
        Assert.assertEquals(actualPagesCount, --pagesCount,
                "Actual saving as draft pages count " + actualPagesCount + " is not equals " +
                        "expected saving as draft pages count " + pagesCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Pages tests")
    @Issue(value = "BugId-106") @TmsLink(value = "TestId-206")
    @Test(priority=6)
    public void testRemovePublishedPage() {

        Allure.description("testRemovePublishedPage, using  " + loginData.getUsername() + " account");
        log.info("testRemovePublishedPage, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int pagesCount = app.getPagesPage().getPublishedPagesCount();
        if (pagesCount == 0) {
            pagesCount++;
        }
        app.getPagesPage().removePublishedPage(index);
        int actualPagesCount = app.getPagesPage().getPublishedPagesCount();
        saveScreenShot();

        log.info("Assert, is actual published pages count " + actualPagesCount + " equals " +
                "expected published pages count " + pagesCount);
        Assert.assertEquals(actualPagesCount, --pagesCount,
                "Actual published pages count " + actualPagesCount + " is not equals " +
                        "expected published pages count " + pagesCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-107") @TmsLink(value = "TestId-207")
    @Test(priority=7)
    public void testCreatePostAndSaveItAsDraft() {

        Allure.description("testCreatePostAndSaveItAsDraft, using  " + loginData.getUsername() + " account");
        log.info("testCreatePostAndSaveItAsDraft, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        PostData postData = new PostData();
        app.getPostsPage().createPostAndSaveItAsDraft(postData);
        PostData actualPostData = app.getPostsPage().getCreatedSavingAsDraftPostData(postData);
        saveScreenShot();

        log.info("Assert, is actual post title " + actualPostData.getPostTitle() + " equals " +
                "expected post title " + postData.getPostTitle());
        Assert.assertEquals(actualPostData.getPostTitle(), postData.getPostTitle(),
                "Actual post title " + actualPostData.getPostTitle() + " is not equals " +
                        "expected post title " + postData.getPostTitle());
        log.info("Assert, is actual post content " + actualPostData.getPostContent() + " equals " +
                "expected post content " + postData.getPostContent());
        Assert.assertEquals(actualPostData.getPostContent(), postData.getPostContent(),
                "Actual post content " + actualPostData.getPostContent() + " is not equals " +
                        "expected post content " + postData.getPostContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-108") @TmsLink(value = "TestId-208")
    @Test(priority=8)
    public void testCreatePostAndPublishIt() {

        Allure.description("testCreatePostAndPublishIt, using  " + loginData.getUsername() + " account");
        log.info("testCreatePostAndPublishIt, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        PostData postData = new PostData();
        app.getPostsPage().createPostAndPublishIt(postData);
        PostData actualPostData = app.getPostsPage().getCreatedPublishedPostData(postData);
        saveScreenShot();

        log.info("Actual post title " + actualPostData.getPostTitle() + " equals " +
                "expected post title " + postData.getPostTitle());
        Assert.assertEquals(actualPostData.getPostTitle(), postData.getPostTitle(),
                "Actual post title " + actualPostData.getPostTitle() + " is not equals " +
                        "expected post title " + postData.getPostTitle());
        log.info("Assert, is actual post content " + actualPostData.getPostContent() + " equals " +
                "expected post content " + postData.getPostContent());
        Assert.assertEquals(actualPostData.getPostContent(), postData.getPostContent(),
                "Actual post content " + actualPostData.getPostContent() + " is not equals " +
                        "expected post content " + postData.getPostContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-109") @TmsLink(value = "TestId-209")
    @Test(priority=9)
    public void testEditSavingAsDraftPost() {

        Allure.description("testEditSavingAsDraftPost, using  " + loginData.getUsername() + " account");
        log.info("testEditSavingAsDraftPost, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        PostData postData = new PostData();
        app.getPostsPage().editSavingAsDraftPost(index, postData);
        PostData actualPostData = app.getPostsPage().getEditedSavingAsDraftPostData(postData);
        saveScreenShot();

        log.info("Assert, is actual post title " + actualPostData.getPostTitle() + " equals " +
                "expected post title " + postData.getPostTitle());
        Assert.assertEquals(actualPostData.getPostTitle(), postData.getPostTitle(),
                "Actual post title " + actualPostData.getPostTitle() + " is not equals " +
                        "expected post title " + postData.getPostTitle());
        log.info("Assert, is actual post content " + actualPostData.getPostContent() + " equals " +
                "expected post content " + postData.getPostContent());
        Assert.assertEquals(actualPostData.getPostContent(), postData.getPostContent(),
                "Actual post content " + actualPostData.getPostContent() + " is not equals " +
                        "expected post content " + postData.getPostContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-110") @TmsLink(value = "TestId-210")
    @Test(priority=10)
    public void testEditPublishedPost() {

        Allure.description("testEditPublishedPost, using  " + loginData.getUsername() + " account");
        log.info("testEditPublishedPost, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        PostData postData = new PostData();
        PostData actualPostData = app.getPostsPage().getEditedPublishedPostData(app.getPostsPage().editPublishedPost(index, postData));
        saveScreenShot();

        log.info("Assert, is actual post title " + actualPostData.getPostTitle() + " equals " +
                "expected post title " + postData.getPostTitle());
        Assert.assertEquals(actualPostData.getPostTitle(), postData.getPostTitle(),
                "Actual post title " + actualPostData.getPostTitle() + " is not equals " +
                        "expected post title " + postData.getPostTitle());
        log.info("Assert, is actual post content " + actualPostData.getPostContent() + " equals " +
                "expected post content " + postData.getPostContent());
        Assert.assertEquals(actualPostData.getPostContent(), postData.getPostContent(),
                "Actual post content " + actualPostData.getPostContent() + " is not equals " +
                        "expected post content " + postData.getPostContent());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-111") @TmsLink(value = "TestId-211")
    @Test(priority=11)
    public void testRemoveSavingAsDraftPost() {

        Allure.description("testRemoveSavingAsDraftPost, using  " + loginData.getUsername() + " account");
        log.info("testRemoveSavingAsDraftPost, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int postsCount = app.getPostsPage().getSavingAsDraftPostsCount();
        if (postsCount == 0) {
            postsCount++;
        }
        app.getPostsPage().removeSavingAsDraftPost(index);
        int actualPostsCount = app.getPostsPage().getSavingAsDraftPostsCount();
        saveScreenShot();

        log.info("Assert, is actual saving as draft posts count " + actualPostsCount + " equals " +
                "expected saving as draft posts count " + postsCount);
        Assert.assertEquals(actualPostsCount, --postsCount,
                "Actual saving as draft posts count " + actualPostsCount + " is not equals " +
                        "expected saving as draft posts count " + postsCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-112") @TmsLink(value = "TestId-212")
    @Test(priority=12)
    public void testRemovePublishedPost() {

        Allure.description("testRemovePublishedPost, using  " + loginData.getUsername() + " account");
        log.info("testRemovePublishedPost, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int postsCount = app.getPostsPage().getPublishedPostsCount();
        if (postsCount == 0) {
            postsCount++;
        }
        app.getPostsPage().removePublishedPost(index);
        int actualPostsCount = app.getPostsPage().getPublishedPostsCount();
        saveScreenShot();

        log.info("Assert, is actual published posts count " + actualPostsCount + " equals " +
                "expected published posts count " + postsCount);
        Assert.assertEquals(actualPostsCount, --postsCount,
                "Actual published posts count " + actualPostsCount + " is not equals " +
                        "expected published posts count " + postsCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Comments tests")
    @Issue(value = "BugId-113") @TmsLink(value = "TestId-213")
    @Test(priority=13)
    public void testMarkUnapprovedCommentAsApproved() {

        Allure.description("testMarkUnapprovedCommentAsApproved, using  " + loginData.getUsername() + " account");
        log.info("testMarkUnapprovedCommentAsApproved, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int approvedCommentsCount = app.getCommentsPage().getApprovedCommentsCount();
        app.getCommentsPage().markUnapprovedCommentAsApproved(index, loginData);
        int actualApprovedCommentsCount = app.getCommentsPage().getApprovedCommentsCount();
        saveScreenShot();

        log.info("Assert, is actual approved сomments сount " + actualApprovedCommentsCount + " equals " +
                "expected approved сomments сount " + approvedCommentsCount);
        Assert.assertEquals(actualApprovedCommentsCount, ++approvedCommentsCount,
                "Actual approved сomments сount " + actualApprovedCommentsCount + " is not equals " +
                        "expected approved сomments сount " + approvedCommentsCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Comments tests")
    @Issue(value = "BugId-114") @TmsLink(value = "TestId-214")
    @Test(priority=14)
    public void testMarkCommentAsSpam() {

        Allure.description("testMarkCommentAsSpam, using  " + loginData.getUsername() + " account");
        log.info("testMarkCommentAsSpam, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int spamCommentsCount = app.getCommentsPage().getSpamCommentsCount();
        app.getCommentsPage().markCommentAsSpam(index, loginData);
        int actualSpamCommentsCount = app.getCommentsPage().getSpamCommentsCount();
        saveScreenShot();

        log.info("Assert, is actual spam сomments сount " + actualSpamCommentsCount + " equals " +
                "expected spam сomments сount " + spamCommentsCount);
        Assert.assertEquals(actualSpamCommentsCount, ++spamCommentsCount,
                "Actual spam сomments сount " + actualSpamCommentsCount + " is not equals " +
                        "expected spam сomments сount " + spamCommentsCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Comments tests")
    @Issue(value = "BugId-115") @TmsLink(value = "TestId-215")
    @Test(priority=15)
    public void testMarkCommentAsTrash() {

        Allure.description("testMarkCommentAsTrash, using  " + loginData.getUsername() + " account");
        log.info("testMarkCommentAsTrash, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        int trashCommentsCount = app.getCommentsPage().getTrashCommentsCount();
        app.getCommentsPage().markCommentAsTrash(index, loginData);
        int actualTrashCommentsCount = app.getCommentsPage().getTrashCommentsCount();
        saveScreenShot();

        log.info("Assert, is actual trash сomments сount " + actualTrashCommentsCount + " equals " +
                "expected trash сomments сount " + trashCommentsCount);
        Assert.assertEquals(actualTrashCommentsCount, ++trashCommentsCount,
                "Actual trash сomments сount " + actualTrashCommentsCount + " is not equals " +
                        "expected trash сomments сount " + trashCommentsCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Comments tests")
    @Issue(value = "BugId-116") @TmsLink(value = "TestId-216")
    @Test(priority=16)
    public void testEditComment() {

        Allure.description("testEditComment, using  " + loginData.getUsername() + " account");
        log.info("testEditComment, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        CommentData commentData = new CommentData();
        app.getCommentsPage().editComment(index, commentData, loginData);
        CommentData actualCommentData = app.getCommentsPage().getEditedCommentData(commentData);
        saveScreenShot();

        log.info("Assert, is actual comment's text " + actualCommentData.getComment() + " equals " +
                "expected сomment's text " + commentData.getComment());
        Assert.assertEquals(actualCommentData.getComment(), commentData.getComment(),
                "Actual comment's text " + actualCommentData.getComment() + " is not equals " +
                        "expected сomment's text " + commentData.getComment());
        log.info("Assert, is actual comment's name " + actualCommentData.getName() + " equals " +
                "expected сomment's name " + commentData.getName());
        Assert.assertEquals(actualCommentData.getName(), commentData.getName(),
                "Actual comment's name " + actualCommentData.getName() + " is not equals " +
                        "expected сomment's name " + commentData.getName());
        log.info("Assert, is actual comment's email " + actualCommentData.getEmail() + " equals " +
                "expected сomment's email " + commentData.getEmail());
        Assert.assertEquals(actualCommentData.getEmail(), commentData.getEmail(),
                "Actual comment's email " + actualCommentData.getEmail() + " is not equals " +
                        "expected сomment's email " + commentData.getEmail());
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Media tests")
    @Issue(value = "BugId-117") @TmsLink(value = "TestId-217")
    @Test(priority=17)
    public void testAddMediaFileToLibrary() {

        Allure.description("testAddMediaFileToLibrary, using  " + loginData.getUsername() + " account");
        log.info("testAddMediaFileToLibrary, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        String mediaFilePath = Paths.get("src/image.jpg").toAbsolutePath().toString();
        int mediaFilesCount = app.getMediaPage().getMediaFilesCount();
        app.getMediaPage().addMediaFileToLibrary(mediaFilePath);
        int actualMediaFilesCount = app.getMediaPage().getMediaFilesCount();
        saveScreenShot();

        log.info("Assert, is actual media files count " + actualMediaFilesCount + " equals " +
                "expected media files count " + mediaFilesCount);
        Assert.assertEquals(actualMediaFilesCount, ++mediaFilesCount,
                "Actual media files count " + actualMediaFilesCount + " is not equals " +
                        "expected media files count " + mediaFilesCount);
    }

    @Epic(value = "Final project testing") @Feature(value = "Administrator permissions tests") @Story(value = "Media tests")
    @Issue(value = "BugId-118") @TmsLink(value = "TestId-218")
    @Test(priority=18)
    public void testRemoveMediaFileFromLibrary() {

        Allure.description("testRemoveMediaFileFromLibrary, using  " + loginData.getUsername() + " account");
        log.info("testRemoveMediaFileFromLibrary, using " + loginData.getUsername() + " account");

        app.getAuthentication().login(loginData);

        int index = 0;
        String mediaFilePath = Paths.get("src/image.jpg").toAbsolutePath().toString();
        int mediaFilesCount = app.getMediaPage().getMediaFilesCount();
        if (mediaFilesCount == 0) {
            mediaFilesCount++;
        }
        app.getMediaPage().removeMediaFileFromLibrary(index, mediaFilePath);
        int actualMediaFilesCount = app.getMediaPage().getMediaFilesCount();
        saveScreenShot();

        log.info("Assert, is actual media files count " + actualMediaFilesCount + " equals " +
                "expected media files count " + mediaFilesCount);
        Assert.assertEquals(actualMediaFilesCount, --mediaFilesCount,
                "Actual media files count " + actualMediaFilesCount + " is not equals " +
                        "expected media files count " + mediaFilesCount);
    }
}
