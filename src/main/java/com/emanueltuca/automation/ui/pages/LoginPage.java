package com.emanueltuca.automation.ui.pages;

import com.emanueltuca.automation.utils.PageNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.emanueltuca.automation.core.config.ConfigReader.getBaseUrl;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver, PageNavigator navigator) {
        super(driver, navigator);
    }

    protected By getDistinctiveElementToCheckIfPageIsLoaded() {
        return loginButton;
    }

    public LoginPage open() {
        openUrl(getBaseUrl());
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public InventoryPage submitLoginExpectingSuccess() {
        click(loginButton);
        return navigator.transitionTo(InventoryPage.class);
    }

    public LoginPage submitLoginExpectingFailure() {
        click(loginButton);
        return this;
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }
}