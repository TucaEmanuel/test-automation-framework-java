package com.emanueltuca.automation.bdd.context;

import com.emanueltuca.automation.ui.pages.BasePage;
import com.emanueltuca.automation.utils.PageNavigator;
import com.emanueltuca.automation.utils.PageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContext implements PageNavigator{
    private static final Logger logger = LoggerFactory.getLogger(TestContext.class);

    private final PageManager pageManager;
    private BasePage currentPage;

    public TestContext() {
        this.pageManager = new PageManager(this);
        logger.debug("TestContext initialized");
    }

    @Override
    public <T extends BasePage> T getPage(Class<T> pageType) {
        logger.debug("Getting page of type: {}", pageType.getSimpleName());
        return pageManager.getPage(pageType);
    }

    @Override
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

    @Override
    public <T extends BasePage> T transitionTo(Class<T> pageClass) {
        logger.info("Transitioning to page: {}", pageClass.getSimpleName());
        T page = pageManager.getPage(pageClass);
        this.currentPage = page;
        logger.info("Page transition completed: {}", pageClass.getSimpleName());
        return page;
    }

    public void cleanup() {
        logger.info("Cleaning up test context");
        currentPage = null;
        pageManager.pagesCacheCleanup();
        logger.info("Test context cleanup completed");
    }
}
