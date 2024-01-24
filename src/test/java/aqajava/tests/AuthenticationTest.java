package aqajava.tests;

import aqajava.models.LoginData;
import aqajava.properties.ConfigProperties;
import io.qameta.allure.*;
import lombok.extern.log4j.Log4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Log4j
public class AuthenticationTest extends BaseTest {

    @DataProvider(name = "dataForLogin")
    public static Object[][] dataForLogin() {
        log.info("Load from properties file account's login and password data and return it " +
                "with titles of main menu items to the test method");
        return new Object[][] {
                {new LoginData(ConfigProperties.prop.getProperty("ADMIN_LOGIN"),
                        ConfigProperties.prop.getProperty("ADMIN_PASSWORD")), "Settings", null},
                {new LoginData(ConfigProperties.prop.getProperty("EDITOR_LOGIN"),
                        ConfigProperties.prop.getProperty("EDITOR_PASSWORD")), "Pages", "Settings"},
                {new LoginData(ConfigProperties.prop.getProperty("AUTHOR_LOGIN"),
                        ConfigProperties.prop.getProperty("AUTHOR_PASSWORD")), "Media", "Pages"},
                {new LoginData(ConfigProperties.prop.getProperty("CONTRIBUTOR_LOGIN"),
                        ConfigProperties.prop.getProperty("CONTRIBUTOR_PASSWORD")), "Posts", "Media"},
                {new LoginData(ConfigProperties.prop.getProperty("SUBSCRIBER_LOGIN"),
                        ConfigProperties.prop.getProperty("SUBSCRIBER_PASSWORD")), "Profile", "Posts"}
        };
    }

    @Epic(value = "Final project testing") @Feature(value = "Different users tests") @Story(value = "Authentication tests")
    @Issue(value = "BugId-11") @TmsLink(value = "TestId-21")
    @Test(dataProvider = "dataForLogin")
    public void testAuthenticationWithDifferentLoginData(LoginData loginData, String itemNamePresent,
                                                         String itemNameNotPresent) {

        Allure.description("Authentication on WordPress site, using  " + loginData.getUsername() + " account");
        log.info("Test of site authentication with " + loginData.getUsername() + " login data");

        app.getAuthentication().openLoginPage();
        app.getAuthentication().login(loginData);
        saveScreenShot();

        log.info("Assert, is item " + itemNamePresent + " present in Main menu");
        Assert.assertTrue(app.getMainMenu().isItemPresent(itemNamePresent),
                "Item " + itemNamePresent + " is not present in Main menu.");
        if (itemNameNotPresent != null) {
            log.info("Assert, is item " + itemNameNotPresent + " not present in Main menu");
            Assert.assertFalse(app.getMainMenu().isItemPresent(itemNameNotPresent),
                    "Item " + itemNameNotPresent + " is present in Main menu.");
        }
    }
}
