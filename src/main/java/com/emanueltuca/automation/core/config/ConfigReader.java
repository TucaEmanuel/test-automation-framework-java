package com.emanueltuca.automation.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties PROPERTIES = new Properties();

    private static final String PROPERTIES_FILE_NAME = "test.properties";

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config/" +  PROPERTIES_FILE_NAME)) {
            if (input == null) {
                logger.error(PROPERTIES_FILE_NAME + " not found in classpath");
                throw new RuntimeException(PROPERTIES_FILE_NAME + " not found in classpath");
            }
            PROPERTIES.load(input);
            logger.info("Configuration loaded successfully from " + PROPERTIES_FILE_NAME);
        } catch (Exception e) {
            logger.error("Failed to load configuration", e);
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static String getProperty(String key) {
        logger.debug("Retrieving property: {}", key);
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    public static String getBaseUrl() {
        logger.debug("Getting base URL");
        return getProperty("base.url");
    }

    public static String getBrowser() {
        logger.debug("Getting browser configuration");
        // Check for system property override first
        String systemBrowser = System.getProperty("driver");
        if (systemBrowser != null && !systemBrowser.trim().isEmpty()) {
            logger.info("Using browser from system property: {}", systemBrowser);
            return systemBrowser.trim().toLowerCase();
        }

        // Fall back to properties file
        String configBrowser = getProperty("browser");
        logger.debug("Using browser from configuration: {}", configBrowser);
        return configBrowser;
    }

    public static int getPageTimeout() {
        logger.debug("Getting page timeout");
        return Integer.parseInt(getProperty("page.timeout"));
    }

    public static int getElementTimeout() {
        logger.debug("Getting element timeout");
        return Integer.parseInt(getProperty("element.timeout"));
    }

    public static boolean isHeadless() {
        logger.debug("Getting headless mode configuration");

        // Check for system property override first
        String headlessOption = System.getProperty("headless");
        if (headlessOption != null && !headlessOption.trim().isEmpty()) {
            logger.info("Using headless option from system property: {}", headlessOption);
            return Boolean.parseBoolean(headlessOption.trim().toLowerCase());
        }

        // Fall back to properties file
        boolean configHeadless =  Boolean.parseBoolean(getProperty("headless"));
        logger.debug("Using headless option from configuration: {}", configHeadless);
        return configHeadless;
    }
}
