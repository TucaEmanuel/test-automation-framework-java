package com.emanueltuca.automation.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtils {
    private WebDriverWait wait;

    public WebDriverUtils(WebDriverWait wait) {
        this.wait = wait;
    }

    public void waitForElementVisible(WebElement element) {
        wait.until(driver -> element.isDisplayed());
    }

    public void clickElement(WebElement element) {
        waitForElementVisible(element);
        element.click();
    }

    public void sendKeysToElement(WebElement element, String text) {
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }
}
