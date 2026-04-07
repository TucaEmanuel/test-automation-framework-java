package com.emanueltuca.automation.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.emanueltuca.automation.core.config.ConfigReader.*;

public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void initializeDriver() {
        logger.info("Initializing WebDriver");
        String browser = getBrowser();
        switch (browser.toLowerCase()) {
            case "chrome":
                logger.debug("Setting up ChromeDriver");
                WebDriverManager.chromedriver().setup();
                DRIVER.set(new ChromeDriver(BrowserOptions.getChromeOptions()));
                logger.info("ChromeDriver initialized successfully");
                break;
            case "firefox":
                logger.debug("Setting up FirefoxDriver");
                WebDriverManager.firefoxdriver().setup();
                DRIVER.set(new FirefoxDriver(BrowserOptions.getFirefoxOptions()));
                logger.info("FirefoxDriver initialized successfully");
                break;
            default:
                logger.error("Unsupported browser: {}", browser);
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        logger.debug("Configuring driver timeouts");
        getDriver().manage().timeouts().implicitlyWait(Duration.ZERO);
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(getPageTimeout()));

        if (!isHeadless()) {
            logger.debug("Maximizing browser window");
            getDriver().manage().window().maximize();
        }

        logger.info("WebDriver initialization completed");
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();

        if (driver == null) {
            logger.error("WebDriver is not initialized. Call initDriver() first.");
            throw new IllegalStateException("WebDriver is not initialized. Call initDriver() first.");
        }
        logger.debug("Retrieving WebDriver instance");
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            DRIVER.remove();
            logger.info("WebDriver quit successfully");
        } else {
            logger.debug("WebDriver is already null, nothing to quit");
        }
    }
}
