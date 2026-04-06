package com.emanueltuca.automation.ui.pages;

import com.emanueltuca.automation.utils.PageNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.emanueltuca.automation.core.config.ConfigReader.getElementTimeout;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected PageNavigator navigator;

    public BasePage(WebDriver driver, PageNavigator navigator) {
        this.driver = driver;
        this.navigator = navigator;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(getElementTimeout()));
    }

    public abstract void waitUntilLoaded();

    protected WebElement find(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected void openUrl(String url) {
        driver.get(url);
    }

    protected BasePage click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        return this;
    }

    protected BasePage type(By locator, String text) {
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
        return this;
    }

    protected String getText(By locator) {
        return find(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
