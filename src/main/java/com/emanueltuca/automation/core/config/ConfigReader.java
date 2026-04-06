package com.emanueltuca.automation.core.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config/test.properties")) {
            if (input == null) {
                throw new RuntimeException("test.properties not found in classpath");
            }
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static int getPageTimeout() {
        return Integer.parseInt(getProperty("page.timeout"));
    }

    public static int getElementTimeout() {
        return Integer.parseInt(getProperty("element.timeout"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
}
