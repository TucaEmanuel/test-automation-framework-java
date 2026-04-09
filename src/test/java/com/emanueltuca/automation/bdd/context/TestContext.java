package com.emanueltuca.automation.bdd.context;

import com.emanueltuca.automation.ui.pages.BasePage;
import com.emanueltuca.automation.utils.PageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContext {
    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);

    private final PageManager pageManager;
    private BasePage currentPage;

    public TestContext() {
        this.pageManager = new PageManager();
        currentPage = null;
    }

    public <T extends BasePage> T getPage(Class<T> pageType) {
        logger.debug("Getting page of type: {}", pageType.getSimpleName());
        return pageManager.getPage(pageType);
    }

    public <T extends BasePage> T getCurrentPage(Class<T> pageType) {
        logger.debug("Getting current page (expecting: {})", pageType.getSimpleName());

        if (currentPage == null) {
            logger.error("No page is currently active. Perform navigation first.");
            throw new IllegalStateException("No page is currently active. Perform navigation first.");
        }

        if (!pageType.isInstance(currentPage)) {
            logger.error("Expected page type {} but current page is {}",
                    pageType.getSimpleName(), currentPage.getClass().getSimpleName());
            throw new IllegalStateException(
                    "Expected page type " + pageType.getSimpleName() +
                            " but current page is " + currentPage.getClass().getSimpleName()
            );
        }

        logger.debug("Current page verified: {}", pageType.getSimpleName());
        return pageType.cast(currentPage.waitUntilLoaded());
    }

    public <T extends BasePage> void setCurrentPage(Class<T> pageClass) {
        logger.debug("Setting current page (expecting: {})", pageClass.getSimpleName());
        this.currentPage = pageManager.getPage(pageClass);
        logger.debug("Current Page was set to: {}", pageClass.getSimpleName());
    }

    public void cleanup() {
        logger.info("Cleaning up test context");
        currentPage = null;
        pageManager.pagesCacheCleanup();
        logger.info("Test context cleanup completed");
    }
}
