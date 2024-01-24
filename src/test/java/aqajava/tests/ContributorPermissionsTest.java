package aqajava.tests;

import aqajava.models.LoginData;
import aqajava.models.PostData;
import aqajava.properties.ConfigProperties;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j
public class ContributorPermissionsTest extends BaseTest {

    {
        log.info("Load from properties file CONTRIBUTOR_LOGIN " + ConfigProperties.prop.getProperty("CONTRIBUTOR_LOGIN") +
                " and CONTRIBUTOR_PASSWORD");
    }
    private LoginData loginData = new LoginData(ConfigProperties.prop.getProperty("CONTRIBUTOR_LOGIN"),
            ConfigProperties.prop.getProperty("CONTRIBUTOR_PASSWORD"));

    @Epic(value = "Final project testing") @Feature(value = "Contributor permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-701") @TmsLink(value = "TestId-801")
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

    @Epic(value = "Final project testing") @Feature(value = "Contributor permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-702") @TmsLink(value = "TestId-802")
    @Test(priority=2)
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

    @Epic(value = "Final project testing") @Feature(value = "Contributor permissions tests") @Story(value = "Posts tests")
    @Issue(value = "BugId-703") @TmsLink(value = "TestId-803")
    @Test(priority=3)
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
}
