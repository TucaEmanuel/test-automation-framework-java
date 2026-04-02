package com.emanueltuca.automation.core.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config/test.properties")) {
            if (input == null) {
                throw new RuntimeException("test.properties not found in classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout"));
    }
}
