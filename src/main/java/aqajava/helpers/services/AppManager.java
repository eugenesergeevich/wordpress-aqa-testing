package aqajava.helpers.services;

import aqajava.helpers.pageobjects.*;
import aqajava.properties.ConfigProperties;
import aqajava.webdriver.Browser;
import lombok.extern.log4j.Log4j;

@Log4j
public class AppManager {

    private Authentication authentication;
    private MainPage mainPage;
    private MainMenu mainMenu;
    private PagesPage pagesPage;
    private PostsPage postsPage;
    private MediaPage mediaPage;
    private CommentsPage commentsPage;
    private Waitings waitings;

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

    //------------------------

    public Authentication getAuthentication() {
        return authentication;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public PagesPage getPagesPage() {
        return pagesPage;
    }

    public PostsPage getPostsPage() {
        return postsPage;
    }

    public MediaPage getMediaPage() {
        return mediaPage;
    }

    public CommentsPage getCommentsPage() {
        return commentsPage;
    }

    public Waitings getWaitings() {
        return waitings;
    }
}
