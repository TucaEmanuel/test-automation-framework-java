package com.emanueltuca.automation.ui.pages;

import com.emanueltuca.automation.utils.PageNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.emanueltuca.automation.core.config.ConfigReader.getBaseUrl;

public class LoginPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private final String URL = getBaseUrl();

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver, PageNavigator navigator) {
        super(driver, navigator);
        logger.debug("LoginPage initialized");
    }

    protected By getDistinctiveElementToCheckIfPageIsLoaded() {
        return loginButton;
    }

    public LoginPage open() {
        logger.debug("Opening Login Page URL: {}", URL);
        openUrl(URL);
        return this;
    }

    public LoginPage enterUsername(String username) {
        logger.debug("Entering username: {}", username);
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        logger.debug("Entering password");
        type(passwordInput, password);
        return this;
    }

    public InventoryPage submitLoginExpectingSuccess() {
        logger.info("Submitting login form expecting success");
        click(loginButton);
        logger.info("Login submitted, transitioning to inventory page");
        return navigator.transitionTo(InventoryPage.class);
    }

    public LoginPage submitLoginExpectingFailure() {
        logger.info("Submitting login form expecting failure");
        click(loginButton);
        logger.info("Login submitted, error expected");
        return this;
    }

    public String getErrorMessageText() {
        logger.debug("Getting error message text");
        String errorText = getText(errorMessage);
        logger.debug("Error message retrieved: {}", errorText);
        return errorText;
    }

    public boolean isErrorMessageDisplayed() {
        logger.debug("Checking if error message is displayed");
        boolean displayed = isDisplayed(errorMessage);
        logger.debug("Error message displayed: {}", displayed);
        return displayed;
    }
}