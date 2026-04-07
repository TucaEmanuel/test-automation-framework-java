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
        return getProperty("browser");
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
        return Boolean.parseBoolean(getProperty("headless"));
    }
}
