package com.emanueltuca.automation.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class WindowManager {
    private static final Logger logger = LoggerFactory.getLogger(WindowManager.class);

    private final WebDriver driver;
    private final WebDriver.Navigation navigate;

    public WindowManager(WebDriver driver) {
        this.driver = driver;
        this.navigate = driver.navigate();
        logger.debug("WindowManager initialized");
    }

    public void goBack() {
        logger.debug("Navigating back");
        navigate.back();
    }

    public void goForward() {
        logger.debug("Navigating forward");
        navigate.forward();
    }

    public void refreshPage() {
        logger.debug("Refreshing current page");
        navigate.refresh();
    }

    public void goTo(String url) {
        logger.debug("Navigating to URL: {}", url);
        navigate.to(url);
    }

    public void switchToTab(String tabTitle) {
        logger.debug("Switching to tab with title: {}", tabTitle);
        Set<String> windows = driver.getWindowHandles();
        logger.debug("Number of windows/tabs: {}", windows.size());

        for(String window: windows){
            driver.switchTo().window(window);
            String currentTitle = driver.getTitle();
            logger.debug("Checking window with title: {}", currentTitle);

            if(tabTitle.equals(currentTitle)){
                logger.info("Found and switched to tab: {}", tabTitle);
                break;
            }
        }
    }
}
