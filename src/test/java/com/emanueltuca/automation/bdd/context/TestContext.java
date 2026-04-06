package com.emanueltuca.automation.bdd.context;

import com.emanueltuca.automation.ui.pages.BasePage;
import com.emanueltuca.automation.utils.PageNavigator;
import com.emanueltuca.automation.utils.PageManager;

public class TestContext implements PageNavigator{

    private final PageManager pageManager;
    private BasePage currentPage;

    public TestContext() {
        this.pageManager = new PageManager(this);
    }

    @Override
    public <T extends BasePage> T getPage(Class<T> pageType) {
        return pageManager.getPage(pageType);
    }

    @Override
    public <T extends BasePage> T getCurrentPage(Class<T> pageType) {
        if (currentPage == null) {
            throw new IllegalStateException("No page is currently active. Perform navigation first.");
        }

        if (!pageType.isInstance(currentPage)) {
            throw new IllegalStateException(
                    "Expected page type " + pageType.getSimpleName() +
                            " but current page is " + currentPage.getClass().getSimpleName()
            );
        }

        return pageType.cast(currentPage);
    }

    @Override
    public <T extends BasePage> T transitionTo(Class<T> pageClass) {
        T page = pageManager.getPage(pageClass);
//        page.waitUntilLoaded();
        this.currentPage = page;
        return page;
    }

    public void cleanup() {
        currentPage = null;
        pageManager.pagesCacheCleanup();
    }
}

