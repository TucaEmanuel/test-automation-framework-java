package com.emanueltuca.automation.ui.pages;

import com.emanueltuca.automation.utils.PageNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.emanueltuca.automation.core.config.ConfigReader.getElementTimeout;

public abstract class BasePage {
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected PageNavigator navigator;

    public BasePage(WebDriver driver, PageNavigator navigator) {
        this.driver = driver;
        this.navigator = navigator;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(getElementTimeout()));
    }

    protected abstract By getDistinctiveElementToCheckIfPageIsLoaded();

    public BasePage waitUntilLoaded() {
        By distinctiveElement = getDistinctiveElementToCheckIfPageIsLoaded();
        try {
            find(distinctiveElement);
        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    String.format("Expected page {%s} to be loaded, but is the distinctive element with {%s} selector is not visible.",
                            this.getClass().getSimpleName(), distinctiveElement)
            );
        }
        return this;
    }


    protected WebElement find(By locator) {
        logger.debug("Finding element: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForUrlContains(String partialUrl) {
        logger.debug("Waiting for URL to contain: {}", partialUrl);
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected BasePage openUrl(String url) {
        logger.debug("Opening URL: {}", url);
        driver.get(url);
        return this;
    }

    protected BasePage click(By locator) {
        logger.debug("Clicking on element: {}", locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        return this;
    }

    protected BasePage type(By locator, String text) {
        logger.debug("Typing '{}' into element: {}", text, locator);
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
        return this;
    }

    protected String getText(By locator) {
        logger.debug("Getting text from element: {}", locator);
        return find(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = find(locator).isDisplayed();
            logger.debug("Element {} is displayed: {}", locator, displayed);
            return displayed;
        } catch (TimeoutException e) {
            logger.debug("Element {} is not displayed", locator);
            return false;
        }
    }
}
