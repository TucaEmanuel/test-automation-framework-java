package com.emanueltuca.automation.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static com.emanueltuca.automation.core.config.ConfigReader.*;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void initializeDriver() {
        String browser = getBrowser();
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                DRIVER.set(new ChromeDriver(BrowserOptions.getChromeOptions()));
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                DRIVER.set(new FirefoxDriver(BrowserOptions.getFirefoxOptions()));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(getPageTimeout()));

        if (!isHeadless()) {
            getDriver().manage().window().maximize();
        }

    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
