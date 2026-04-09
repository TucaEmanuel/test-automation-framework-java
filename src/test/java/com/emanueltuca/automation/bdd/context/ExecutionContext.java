package com.emanueltuca.automation.bdd.context;

import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExecutionContext {
    private static final Logger logger = LoggerFactory.getLogger(ExecutionContext.class);

    private static final String RUN_ID = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    private static final Path RUN_OUTPUT_DIR = Path.of("target", "execution-output", RUN_ID);

    private ExecutionContext() {
    }

    public static void initialize(String scenarioName) {
        String scenarioFolderUniqueName = ExecutionContext.buildUniqueScenarioFolderName(scenarioName);
        Path scenarioDir = ExecutionContext.getScenarioOutputDir(scenarioFolderUniqueName);

        try {
            Files.createDirectories(scenarioDir);
            ThreadContext.put("runId", ExecutionContext.getRunId());
            ThreadContext.put("scenarioDir", scenarioDir.toString());
            ThreadContext.put("scenario", scenarioName);
            logger.debug("ExecutionContext initialized: runId={}, scenarioDir={}", ExecutionContext.getRunId(), scenarioDir);
        } catch (IOException e) {
            logger.error("Failed to create scenario output directory: {}", scenarioDir, e);
            throw new RuntimeException("Failed to initialize execution context for scenario: " + scenarioName, e);
        } catch (Exception e) {
            logger.error("Unexpected error while initializing execution context for scenario: {}", scenarioName, e);
            throw new RuntimeException(e);
        }
    }

    public static void cleanup() {
        ThreadContext.clearAll();
    }

    public static String getRunId() {
        return RUN_ID;
    }

    public static Path getRunOutputDir() {
        return RUN_OUTPUT_DIR;
    }

    private static String buildUniqueScenarioFolderName(String scenarioName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        long threadId = Thread.currentThread().getId();

        return scenarioName + "_" + timestamp + "_" + threadId;
    }

    private static Path getScenarioOutputDir(String scenarioName) {
        return RUN_OUTPUT_DIR.resolve(sanitizeName(scenarioName));
    }

    private static String sanitizeName(String input) {
        return input.replaceAll("[^a-zA-Z0-9-_]", "_").replaceAll("[\\\\/:*?\"<>|\\s]+", "_");
    }
}
