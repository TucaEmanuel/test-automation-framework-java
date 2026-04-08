package com.emanueltuca.automation.utils.reporting;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Allure Reporting Utilities - Helper methods for Allure integration
 */
public class AllureReportUtils {

    private static final Logger logger = LoggerFactory.getLogger(AllureReportUtils.class);


    public static void attachScreenshot(String screenshotName, byte[] screenshot) {
        try {
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshot), ".png");
            logger.debug("Screenshot attached to Allure: {}", screenshotName);
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Allure", e);
        }
    }

    public static void attachText(String textTitle, byte[] textContentAsByteArray) {
        try {
            Allure.addAttachment(textTitle, "text/plain", new ByteArrayInputStream(textContentAsByteArray), ".txt");
            logger.debug("Text attached to Allure: {}", textTitle);
        } catch (Exception e) {
            logger.error("Failed to attach text to Allure", e);
        }
    }

    public static void attachLog(String logName, String content) {
        try {
            Allure.addAttachment(logName, "text/plain", content);
            logger.debug("Log attached to Allure: {}", logName);
        } catch (Exception e) {
            logger.error("Failed to attach log to Allure", e);
        }
    }

    public static void attachJson(String jsonName, String json) {
        try {
            Allure.addAttachment(jsonName, "application/json", json);
            logger.debug("JSON data attached to Allure: {}", jsonName);
        } catch (Exception e) {
            logger.error("Failed to attach JSON to Allure", e);
        }
    }

    public static void addLabel(String labelName, String value) {
        try {
            Allure.label(labelName, value);
            logger.debug("Label added to Allure: {} = {}", labelName, value);
        } catch (Exception e) {
            logger.error("Failed to add label to Allure", e);
        }
    }

    public static void setSeverity(String severity) {
        try {
            Allure.label("severity", severity);
            logger.debug("Severity set in Allure: {}", severity);
        } catch (Exception e) {
            logger.error("Failed to set severity in Allure", e);
        }
    }

    public static void addFeature(String feature) {
        try {
            Allure.feature(feature);
            logger.debug("Feature added to Allure: {}", feature);
        } catch (Exception e) {
            logger.error("Failed to add feature to Allure", e);
        }
    }

    public static void addStory(String story) {
        try {
            Allure.story(story);
            logger.debug("Story added to Allure: {}", story);
        } catch (Exception e) {
            logger.error("Failed to add story to Allure", e);
        }
    }
}
