package com.emanueltuca.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

public final class ScreenshotUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        logger.debug("Taking screenshot as bytes");
        if (driver == null) {
            logger.error("WebDriver is null. Cannot take screenshot.");
            throw new IllegalStateException("WebDriver is null. Cannot take screenshot.");
        }

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        logger.debug("Screenshot captured successfully, size: {} bytes", screenshot.length);
        return screenshot;
    }

    public static Optional<String> saveScreenshot(byte[] screenshotBytes, String scenarioName, Path screenshotPath) {
        logger.debug("Saving screenshot for scenario: {}", scenarioName);
        try {
            String safeScenarioName = scenarioName
                    .replaceAll("[^a-zA-Z0-9-_]", "_")
                    .replaceAll("_+", "_");

            String fileName = safeScenarioName + "_T" +
                    Thread.currentThread().getId() + "_" +
                    LocalDateTime.now().format(FORMATTER) + ".png";

            Path destination = screenshotPath.resolve(fileName);
            logger.debug("Writing screenshot to: {}", destination.toAbsolutePath());

            Files.createDirectories(destination.getParent());
            Files.write(destination, screenshotBytes);

            String absolutePath = destination.toAbsolutePath().toString();
            logger.debug("Screenshot saved successfully at: {}", absolutePath);
            return Optional.of(absolutePath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot for scenario: {}", scenarioName, e);
            return Optional.empty();
        }
    }
}