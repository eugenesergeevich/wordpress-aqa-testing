package aqajava.tests;

import aqajava.models.LoginData;
import aqajava.models.PostData;
import aqajava.properties.ConfigProperties;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j
public class AuthorPermissionsTest extends BaseTest {

    {
        log.info("Load from properties file AUTHOR_LOGIN " + ConfigProperties.prop.getProperty("AUTHOR_LOGIN") +
                " and AUTHOR_PASSWORD");
    }
    private LoginData loginData = new LoginData(ConfigProperties.prop.getProperty("AUTHOR_LOGIN"),
            ConfigProperties.prop.getProperty("AUTHOR_PASSWORD"));

    @Epic(value = "Final project testing") @Feature(value = "Author permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-501") @TmsLink(value = "TestId-601")
    @Test(priority=1)
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

    @Epic(value = "Final project testing") @Feature(value = "Author permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-502") @TmsLink(value = "TestId-602")
    @Test(priority=2)
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

    @Epic(value = "Final project testing") @Feature(value = "Author permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-503") @TmsLink(value = "TestId-603")
    @Test(priority=3)
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

    @Epic(value = "Final project testing") @Feature(value = "Author permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-504") @TmsLink(value = "TestId-604")
    @Test(priority=4)
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

    @Epic(value = "Final project testing") @Feature(value = "Author permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-505") @TmsLink(value = "TestId-605")
    @Test(priority=5)
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

    @Epic(value = "Final project testing") @Feature(value = "Author permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-506") @TmsLink(value = "TestId-606")
    @Test(priority=6)
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
}
