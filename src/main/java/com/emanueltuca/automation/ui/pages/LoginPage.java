package com.emanueltuca.automation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.emanueltuca.automation.core.config.Config.getBaseUrl;

public class LoginPage extends BasePage {

    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openUrl(getBaseUrl());
    }

    public void loginAs(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }
}