package com.emanueltuca.automation.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.emanueltuca.automation.core.config.Config.getTimeout;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(getTimeout()));
    }

    public void openUrl(String url) {
        driver.get(url);
    }
}
