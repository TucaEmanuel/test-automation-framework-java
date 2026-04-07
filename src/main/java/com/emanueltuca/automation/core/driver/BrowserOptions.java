package com.emanueltuca.automation.core.driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.emanueltuca.automation.core.config.ConfigReader.isHeadless;

public final class BrowserOptions {
    private static final Logger logger = LoggerFactory.getLogger(BrowserOptions.class);

    private BrowserOptions() {
    }

    public static ChromeOptions getChromeOptions() {
        logger.debug("Configuring Chrome browser options");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        if(isHeadless()){
            logger.debug("Adding headless mode to Chrome options");
            options.addArguments("--headless");
        }

        logger.debug("Chrome options configured successfully");
        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {
        logger.debug("Configuring Firefox browser options");
        FirefoxProfile profile = new FirefoxProfile();

        // Disable password manager
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);

        // Disable notifications
        profile.setPreference("dom.webnotifications.enabled", false);

        // Disable geolocation popup
        profile.setPreference("geo.enabled", false);

        // Disable password breach alerts
        profile.setPreference("signon.management.page.breach-alerts.enabled", false);

        // Disable autofill
        profile.setPreference("extensions.formautofill.addresses.enabled", false);
        profile.setPreference("extensions.formautofill.creditCards.enabled", false);

        // Optional: disable extensions
        profile.setPreference("extensions.enabledScopes", 0);

        // Equivalent incognito
        profile.setPreference("browser.privatebrowsing.autostart", true);

        FirefoxOptions options = new FirefoxOptions();
        if(isHeadless()){
            logger.debug("Adding headless mode to Firefox options");
            options.addArguments("--headless");
        }
        options.setProfile(profile);

        logger.debug("Firefox options configured successfully");
        return options;
    }
}
