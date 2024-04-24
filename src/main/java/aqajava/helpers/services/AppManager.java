package aqajava.helpers.services;

import aqajava.helpers.pageobjects.*;
import aqajava.properties.ConfigProperties;
import aqajava.webdriver.Browser;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
public class AppManager {

    @Getter private Authentication authentication;
    @Getter private MainPage mainPage;
    @Getter private MainMenu mainMenu;
    @Getter private PagesPage pagesPage;
    @Getter private PostsPage postsPage;
    @Getter private MediaPage mediaPage;
    @Getter private CommentsPage commentsPage;
    @Getter private Waitings waitings;

    public String baseUrl;
    private static AppManager appInstance;

    private AppManager() {

        Browser.initDriver();
        log.info("Load from properties file BASE_URL " + ConfigProperties.prop.getProperty("BASE_URL"));
        baseUrl = ConfigProperties.prop.getProperty("BASE_URL");

        authentication = new Authentication(this);
        mainPage = new MainPage(this);
        mainMenu = new MainMenu(this);
        pagesPage = new PagesPage(this);
        postsPage = new PostsPage(this);
        mediaPage = new MediaPage(this);
        commentsPage = new CommentsPage(this);
        waitings = new Waitings();
    }

    public static AppManager getAppInstance() {
        if (appInstance == null) {
            log.info("Create AppManager instance");
            appInstance = new AppManager();
        }
        log.info("Return AppManager instance");
        return appInstance;
    }

    public void stop() {
        log.info("Stop process of Webdriver and AppManager instance");
        Browser.quitDriver();
        if (appInstance != null) {
            appInstance = null;
        }
    }
}
