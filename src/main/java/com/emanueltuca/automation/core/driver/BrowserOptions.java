package com.emanueltuca.automation.core.driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.Map;

public final class BrowserOptions {

    private BrowserOptions() {
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=PasswordLeakDetection");

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        return options;
    }

    public static FirefoxOptions getFirefoxOptions() {

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

        // echivalent incognito
        //TODO add -private preference
//        profile.setPreference("-private");

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        return options;
    }
}
