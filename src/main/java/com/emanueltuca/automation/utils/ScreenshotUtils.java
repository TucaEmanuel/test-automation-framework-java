package com.emanueltuca.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class ScreenshotUtils {

    private static final String SCREENSHOTS_DIR = "target/screenshots";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
    }

    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is null. Cannot take screenshot.");
        }

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static String saveScreenshot(byte[] screenshotBytes, String scenarioName) {
        try {
            Path screenshotsPath = Paths.get(SCREENSHOTS_DIR);
            Files.createDirectories(screenshotsPath);

            String safeScenarioName = scenarioName
                    .replaceAll("[^a-zA-Z0-9-_]", "_")
                    .replaceAll("_+", "_");

            String fileName = safeScenarioName + "_T" +
                    Thread.currentThread().getId() + "_" +
                    LocalDateTime.now().format(FORMATTER) + "_" +
                    UUID.randomUUID() + ".png";

            Path destination = screenshotsPath.resolve(fileName);
            Files.write(destination, screenshotBytes);

            return destination.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot for scenario: " + scenarioName, e);
        }
    }
}