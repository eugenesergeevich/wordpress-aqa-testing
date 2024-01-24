package aqajava.tests;

import aqajava.models.CommentData;
import aqajava.models.LoginData;
import aqajava.properties.ConfigProperties;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j
public class VisitorCreateCommentTest extends BaseTest {

    {
        log.info("Load from properties file ADMIN_LOGIN " + ConfigProperties.prop.getProperty("ADMIN_LOGIN") +
                " and ADMIN_PASSWORD");
    }
    private LoginData loginData = new LoginData(ConfigProperties.prop.getProperty("ADMIN_LOGIN"),
            ConfigProperties.prop.getProperty("ADMIN_PASSWORD"));

    @Epic(value = "Final project testing") @Feature(value = "Visitor permissions tests") @Story(value = "Comments tests")
    @Issue(value = "BugId-31") @TmsLink(value = "TestId-41")
    @Test(priority=1)
    public void testCreateByAnyVisitorComment() {

        Allure.description("testCreateByAnyVisitorComment, not using account");
        log.info("testCreateByAnyVisitorComment, not using account");

        int index = 0;
        CommentData commentData = new CommentData();
        app.getCommentsPage().createByAnyVisitorComment(index, commentData, loginData);
        CommentData actualCommentData = app.getCommentsPage().getCreatedByAnyVisitorCommentData(index, commentData, loginData);
        saveScreenShot();

        log.info("Assert, is actual comment text " + actualCommentData.getComment() + " equals " +
                "expected comment text " + commentData.getComment());
        Assert.assertEquals(actualCommentData.getComment(), commentData.getComment(),
                "Actual comment text " + actualCommentData.getComment() + " is not equals " +
                        "expected comment text " + commentData.getComment());
        log.info("Assert, is actual comment name " + actualCommentData.getName() + " equals " +
                "expected comment name" + commentData.getName());
        Assert.assertEquals(actualCommentData.getName(), commentData.getName(),
                "Actual comment name " + actualCommentData.getName() + " is not equals " +
                        "expected comment name" + commentData.getName());
    }
}
