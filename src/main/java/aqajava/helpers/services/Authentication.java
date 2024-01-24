package aqajava.helpers.services;

import aqajava.helpers.pageobjects.BasePage;
import aqajava.models.LoginData;
import aqajava.properties.ConfigProperties;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

@Log4j
public class Authentication extends BasePage {

    private static final By USER_LOGIN_FIELD = By.cssSelector("#user_login");
    private static final By USER_PASSWORD_FIELD = By.cssSelector("#user_pass");
    private static final By REMEMBER_ME_CHECK_BOX = By.cssSelector("#rememberme");
    private static final By LOGIN_SUBMIT_BUTTON = By.cssSelector("#wp-submit");
    private static final By LOST_YOUR_PASSWORD = By.cssSelector("#nav a");
    private static final By ADMIN_BAR = By.cssSelector("#wp-admin-bar-my-account a");
    private static final By ADMIN_BAR_LOGOUT = By.cssSelector("#wp-admin-bar-logout a");

    public Authentication(AppManager app) {
        super(app);
    }

    public void openLoginPage() {
        log.debug("Open login page");
        if (isLoginPageOpened()) {
            return;
        }
        driver.get(ConfigProperties.prop.getProperty("LOGIN_URL"));
    }

    public void login(LoginData loginData) {
        log.debug("Login to " + loginData.getUsername() + " account");
        if (isLoggedIn()) {
            if (isLoggedIn(loginData)) {
                return;
            } else {
                logOut();
            }
        }
        openLoginPage();
        fillLoginFormsField(USER_LOGIN_FIELD, loginData.getUsername());
        fillLoginFormsField(USER_PASSWORD_FIELD, loginData.getPassword());
        log.debug("Click on 'Remember me' check box with locator " + REMEMBER_ME_CHECK_BOX);
        driver.findElement(REMEMBER_ME_CHECK_BOX).click();
        log.debug("Click on login submit button with locator " + LOGIN_SUBMIT_BUTTON);
        driver.findElement(LOGIN_SUBMIT_BUTTON).click();
    }

    public void logOut() {
        log.debug("Logout from account");
        if (isLoggedIn()) {
            scrollByLocator(ADMIN_BAR);
            app.getWaitings().waitForElementClickable(ADMIN_BAR_LOGOUT).click();
        }
    }

    //-------------------------------------

    private boolean isLoginPageOpened() {
        log.debug("Check, is login page already open");
        return driver.getCurrentUrl().equals(baseUrl + "aqajava")
                && driver.findElement(LOST_YOUR_PASSWORD).getText().equals("Lost your password?");
    }

    private boolean isLoggedIn() {
        log.debug("Check, is already login to account");
        return isElementPresent(ADMIN_BAR_LOGOUT);
    }

    private boolean isLoggedIn(LoginData loginData) {
        log.debug("Check, is already login to account with username " + loginData.getUsername());
        return isLoggedIn()
                && driver.findElement(ADMIN_BAR).getText().equals("Howdy, " + loginData.getUsername());
    }

    private void fillLoginFormsField(By locator, String text) {
        log.debug("Fill login form's field with locator " + locator);
        driver.findElement(locator).click();
        driver.findElement(locator).sendKeys(Keys.CONTROL, "a");
        driver.findElement(locator).sendKeys(Keys.DELETE);
        driver.findElement(locator).sendKeys(text);
    }
}
