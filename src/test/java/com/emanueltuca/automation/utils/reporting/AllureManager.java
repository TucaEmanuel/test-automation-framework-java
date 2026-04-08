package com.emanueltuca.automation.utils.reporting;

import io.cucumber.java.Scenario;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Allure Manager - Centralized management for Allure Reports setup and configuration
 * Handles environment setup, categories, feature extraction, and test metadata
 * Uses AllureReportUtils for consistent Allure API interactions
 */
public class AllureManager {

    private static final Logger logger = LoggerFactory.getLogger(AllureManager.class);
    //TODO: read the allure dir from junit-platform.properties
    private static final String ALLURE_RESULTS_DIR = "target/allure-results";
    private static final String ENVIRONMENT_FILE = "environment.properties";
    private static final String CATEGORIES_FILE = "categories.json";

    /**
     * -- GETTER --
     *  Check if Allure Manager is initialized
     */
    @Getter
    private static boolean initialized = false;

    /**
     * Initialize Allure reporting setup
     * Call this method in @Before hook to setup environment and categories
     */
    public static void initialize() {
        if (!initialized) {
            logger.info("Initializing Allure Manager");
            setupEnvironment();
            writeCategories();
            initialized = true;
            logger.debug("Allure Manager initialized successfully");
        }
    }

    /**
     * Setup test metadata for a scenario
     * Call this method in @Before hook with the scenario parameter
     */
    public static void setupTestMetadata(Scenario scenario) {
        logger.debug("Setting up test metadata for scenario: {}", scenario.getName());

        // Set feature name using AllureReportUtils
        String featureName = extractFeatureName(scenario);
        AllureReportUtils.addFeature(featureName);

        // Set tags as labels using AllureReportUtils
        scenario.getSourceTagNames().forEach(tag -> {
            AllureReportUtils.addLabel("tag", tag);
        });
    }

    /**
     * Attach screenshot to Allure report
     * Convenience method that delegates to AllureReportUtils
     */
    public static void attachScreenshot(String name, byte[] screenshot) {
        AllureReportUtils.attachScreenshot(name, screenshot);
    }

    public static void attachText(String name, byte[] textContent) {
        AllureReportUtils.attachText(name, textContent);
    }

    /**
     * Attach log to Allure report
     * Convenience method that delegates to AllureReportUtils
     */
    public static void attachLog(String name, String content) {
        AllureReportUtils.attachLog(name, content);
    }

    /**
     * Attach JSON data to Allure report
     * Convenience method that delegates to AllureReportUtils
     */
    public static void attachJson(String name, String json) {
        AllureReportUtils.attachJson(name, json);
    }

    /**
     * Add custom label to test
     * Convenience method that delegates to AllureReportUtils
     */
    public static void addLabel(String name, String value) {
        AllureReportUtils.addLabel(name, value);
    }

    /**
     * Set test severity
     * Convenience method that delegates to AllureReportUtils
     */
    public static void setSeverity(String severity) {
        AllureReportUtils.setSeverity(severity);
    }

    /**
     * Add story to test
     * Convenience method that delegates to AllureReportUtils
     */
    public static void addStory(String story) {
        AllureReportUtils.addStory(story);
    }

    /**
     * Setup environment information for Allure Report
     * Creates environment.properties file with test configuration
     */
    private static void setupEnvironment() {
        try {
            // Create directory if it doesn't exist
            Files.createDirectories(Paths.get(ALLURE_RESULTS_DIR));

            Map<String, String> environmentInfo = buildEnvironmentInfo();
            File envFile = new File(ALLURE_RESULTS_DIR, ENVIRONMENT_FILE);

            try (FileWriter writer = new FileWriter(envFile)) {
                for (Map.Entry<String, String> entry : environmentInfo.entrySet()) {
                    writer.append(entry.getKey())
                           .append("=")
                           .append(entry.getValue())
                           .append("\n");
                }
            }
            logger.debug("Environment properties written to: {}", envFile.getAbsolutePath());

        } catch (IOException e) {
            logger.error("Failed to setup Allure environment", e);
        }
    }

    /**
     * Write categories.json file for custom test categorization in Allure
     * Note: Static version at src/test/resources/categories.json is automatically picked up by Allure
     * This method writes a backup copy to target/allure-results as fallback
     */
    private static void writeCategories() {
        // This backup ensures categories exist at runtime even if needed
        // Primary source: src/test/resources/categories.json (auto-detected by Allure)
        String json = """
        [
          {
            "name": "Assertion Failures",
            "matchedStatuses": ["failed"],
            "messageRegex": ".*Assertion.*"
          },
          {
            "name": "Element Not Found",
            "matchedStatuses": ["failed"],
            "traceRegex": ".*NoSuchElementException.*"
          },
          {
            "name": "Timeout Issues",
            "matchedStatuses": ["failed"],
            "traceRegex": ".*TimeoutException.*"
          },
          {
            "name": "Login Failures",
            "matchedStatuses": ["failed"],
            "messageRegex": ".*login.*"
          },
          {
            "name": "Network Issues",
            "matchedStatuses": ["failed"],
            "traceRegex": ".*ConnectException.*"
          }
        ]
        """;

        try {
            File file = new File(ALLURE_RESULTS_DIR, CATEGORIES_FILE);
            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            }
            logger.debug("Categories written to: {}", file.getAbsolutePath());

        } catch (IOException e) {
            logger.error("Failed to write categories.json", e);
        }
    }

    /**
     * Extract feature name from scenario ID
     * Feature ID format: path/to/Feature.feature:line_number
     */
    private static String extractFeatureName(Scenario scenario) {
        String id = scenario.getId();
        if (id != null && id.contains("/")) {
            return id.substring(id.lastIndexOf('/') + 1).split(":")[0].replace(".feature", "");
        }
        return "Test Feature";
    }

    /**
     * Build environment information map
     * Collects system and framework information
     */
    private static Map<String, String> buildEnvironmentInfo() {
        Map<String, String> env = new HashMap<>();

        // Application Information
        env.put("Application URL", getPropertyOrDefault("base.url", "https://www.saucedemo.com/"));
        env.put("Application Name", "Sauce Labs Demo App");

        // Browser Configuration
        env.put("Browser", getPropertyOrDefault("browser", "Chrome"));
        env.put("Headless Mode", getPropertyOrDefault("headless", "true"));

        // Framework Versions
        env.put("Test Framework", "JUnit 5.11.0");
        env.put("Cucumber Version", "7.18.0");
        env.put("Selenium Version", "4.41.0");

        // Java Information
        env.put("Java Version", System.getProperty("java.version", "unknown"));
        env.put("Java Vendor", System.getProperty("java.vendor", "unknown"));

        // System Information
        env.put("OS Name", System.getProperty("os.name", "unknown"));
        env.put("OS Version", System.getProperty("os.version", "unknown"));
        env.put("OS Architecture", System.getProperty("os.arch", "unknown"));

        // Test Configuration
        env.put("Page Timeout (seconds)", getPropertyOrDefault("page.timeout", "10"));
        env.put("Element Timeout (seconds)", getPropertyOrDefault("element.timeout", "10"));

        // Report Tool
        env.put("Report Tool", "Allure Reports 2.25.0");
        env.put("Test Execution Date", java.time.LocalDateTime.now().toString());

        return env;
    }

    /**
     * Get property from system or config with fallback default value
     */
    private static String getPropertyOrDefault(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value != null && !value.trim().isEmpty()) {
            return value;
        }
        return defaultValue;
    }

    /**
     * Reset initialization state (useful for testing)
     */
    public static void reset() {
        initialized = false;
        logger.info("Allure Manager reset");
    }
}
