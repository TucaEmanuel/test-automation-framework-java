package com.emanueltuca.automation.utils;

import com.emanueltuca.automation.core.driver.DriverFactory;
import com.emanueltuca.automation.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Page Object Manager - Centralizes page object instantiation and management
 * Provides lazy initialization and type-safe access to page objects
 */
public class PageManager {

    private final PageNavigator navigator;
    private final Map<Class<? extends BasePage>, BasePage> pageCache = new HashMap<>();

    public PageManager(PageNavigator navigator) {
        this.navigator = navigator;
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        WebDriver driver =  DriverFactory.getDriver();

        if (!pageCache.containsKey(pageClass)) {
            try {

                T page = pageClass.getDeclaredConstructor(WebDriver.class, PageNavigator.class).newInstance(driver, navigator);
                pageCache.put(pageClass, page);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return pageClass.cast(pageCache.get(pageClass));
    }

    public void pagesCacheCleanup() {
        pageCache.clear();
    }
}
