package aqajava.helpers.pageobjects;

import aqajava.helpers.services.AppManager;
import lombok.extern.log4j.Log4j;

@Log4j
public class MainPage extends BasePage {

    public MainPage(AppManager app) {
        super(app);
    }

    public void openMainPage() {
        log.debug("Open main page");
        if (isMainPageOpened()) {
            return;
        }
        driver.get(baseUrl);
    }

    private boolean isMainPageOpened() {
        log.debug("Check, is main page already open");
        return driver.getCurrentUrl().equals(baseUrl);
    }
}
