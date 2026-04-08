package com.emanueltuca.automation.utils;

import com.emanueltuca.automation.core.driver.DriverFactory;
import com.emanueltuca.automation.ui.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Page Object Manager - Centralizes page object instantiation and management
 * Provides lazy initialization and type-safe access to page objects
 */
public class PageManager {
    private static final Logger logger = LoggerFactory.getLogger(PageManager.class);

    private final Map<Class<? extends BasePage>, BasePage> pageCache = new HashMap<>();

    public PageManager() {
    }

    public <T extends BasePage> T getPage(Class<T> pageClass) {
        logger.debug("Getting page: {}", pageClass.getSimpleName());

        WebDriver driver = DriverFactory.getDriver();

        if (!pageCache.containsKey(pageClass)) {
            try {
                logger.debug("Page not in cache, instantiating: {}", pageClass.getSimpleName());
                T page = pageClass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
                pageCache.put(pageClass, page);
                logger.debug("Page instantiated and cached: {}", pageClass.getSimpleName());
            } catch (Exception e) {
                logger.error("Failed to instantiate page: {}", pageClass.getSimpleName(), e);
                throw new RuntimeException(e);
            }
        } else {
            logger.debug("Page found in cache: {}", pageClass.getSimpleName());
        }
        return pageClass.cast(pageCache.get(pageClass));
    }

    public void pagesCacheCleanup() {
        logger.debug("Clearing page cache ({} pages cached)", pageCache.size());
        pageCache.clear();
        logger.debug("Page cache cleared");
    }
}
