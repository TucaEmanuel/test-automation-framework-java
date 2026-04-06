package com.emanueltuca.automation.utils;

import com.emanueltuca.automation.ui.pages.BasePage;

public interface PageNavigator {

    <T extends BasePage> T transitionTo(Class<T> pageType);
    <T extends BasePage> T getCurrentPage(Class<T> pageType);
    <T extends BasePage> T getPage(Class<T> pageType);
}
