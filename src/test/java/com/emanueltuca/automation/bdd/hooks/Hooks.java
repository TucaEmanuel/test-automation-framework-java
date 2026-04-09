package com.emanueltuca.automation.bdd.hooks;

import com.emanueltuca.automation.bdd.context.ExecutionContext;
import com.emanueltuca.automation.bdd.context.PageContext;
import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.core.driver.DriverFactory;
import com.emanueltuca.automation.utils.ScreenshotUtils;
import com.emanueltuca.automation.utils.reporting.AllureManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;


public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private final PageContext pageContext;
    private final TestContext testContext;

    public Hooks(PageContext pageContext, TestContext testContext) {

        this.pageContext = pageContext;
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        //Initialize the Execution Context for the current scenario (thread-local variables, output directories)
        ExecutionContext.initialize(scenario.getName());

        // Initialize Allure Manager (environment, categories)
        AllureManager.initialize();

        // Setup test metadata (feature, tags)
        AllureManager.setupTestMetadata(scenario);

        logger.info("========== START SCENARIO: {} ==========", scenario.getName());

        DriverFactory.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Scenario finished: {}", scenario.getName());
        logger.info("Scenario status: {}", scenario.getStatus());

        WebDriver driver = DriverFactory.getDriver();
        try {
            handleFailureArtifacts(scenario, driver);
        } finally {
            // Reset navigation state to prevent stale references
            pageContext.cleanup();
            testContext.cleanup();
            DriverFactory.quitDriver();

            logger.info("========== END SCENARIO: {} ==========", scenario.getName());

            // Cleanup the current scenario execution context
            ExecutionContext.cleanup();
        }

    }

    private void handleFailureArtifacts(Scenario scenario, WebDriver driver) {
        if (scenario.isFailed() && driver != null) {
            logger.error("Scenario failed: {}", scenario.getName());

            Path scenarioDir = Path.of(ThreadContext.get("scenarioDir"));

            captureScreenshot(scenario, driver, scenarioDir);
            attachFailureContext(scenario, driver, scenarioDir);
            attachPageSource(driver, scenarioDir);
            Path scenarioLogPath = scenarioDir.resolve("scenario.log");
            AllureManager.attachLogFile("Scenario Log", scenarioLogPath);
        }
    }

    private void captureScreenshot(Scenario scenario, WebDriver driver, Path scenarioDir) {
        // Capture screenshot
        byte[] screenshot = ScreenshotUtils.takeScreenshotAsBytes(driver);

        // Save screenshot to file system
        Optional<String> screenshotPath = ScreenshotUtils.saveScreenshot(screenshot, scenario.getName(), scenarioDir);
        screenshotPath.ifPresent(path -> logger.info("Screenshot saved at: {}", path));

        // Attach screenshot to Allure report
        AllureManager.attachScreenshot("Failure Screenshot", screenshot);
        logger.info("Screenshot attached to allure report");
    }

    private void attachPageSource(WebDriver driver, Path scenarioDir) {
        String pageSource = getPageSource(driver);
        Path htmlFile = scenarioDir.resolve("page-source.html");
        try {
            Files.writeString(htmlFile, pageSource);
            logger.info("Page source saved at: {}", htmlFile.toAbsolutePath());

            boolean attached = AllureManager.attachHtmlFile("Page Source", htmlFile);
            if (!attached) {
                AllureManager.attachText("Page Source", pageSource);
            }
        } catch (IOException e) {
            logger.error("Error writing Page Source to the following path: {}", htmlFile.toAbsolutePath(), e);
            AllureManager.attachText("Page Source", pageSource);
        }
    }

    private @NonNull String getPageSource(WebDriver driver) {
        try {
            return Objects.requireNonNull(driver.getPageSource());
        } catch (Exception e) {
            return "Unavailable: " + e.getClass().getSimpleName();
        }
    }

    private void attachFailureContext(Scenario scenario, WebDriver driver, Path scenarioDir) {
        String failureContext = getFailureContext(scenario, driver);

        Path contextFile = scenarioDir.resolve("context.txt");
        try {
            Files.writeString(contextFile, failureContext);
            logger.info("Failure Context saved at: {}", contextFile);

            boolean attached = AllureManager.attachTextFile("Failure Context", contextFile);
            if (!attached) {
                AllureManager.attachText("Failure Context", failureContext);
            }
        } catch (IOException e) {
            logger.error("Error writing failure context to the following path: {}", contextFile.toAbsolutePath(), e);
            AllureManager.attachText("Failure Context", failureContext);
        }
    }

    private static @NonNull String getFailureContext(Scenario scenario, WebDriver driver) {
        String currentUrl;
        String title;
        try {
            currentUrl = driver.getCurrentUrl();
        } catch (Exception e) {
            currentUrl = "Unavailable: " + e.getClass().getSimpleName();
        }

        try {
            title = driver.getTitle();
        } catch (Exception e) {
            title = "Unavailable: " + e.getClass().getSimpleName();
        }

        // Build a comprehensive failure context string
        return "Scenario: " + scenario.getName() + System.lineSeparator() +
                "Status: " + scenario.getStatus() + System.lineSeparator() +
                "URL: " + currentUrl + System.lineSeparator() +
                "Title: " + title + System.lineSeparator() +
                "Thread: " + Thread.currentThread().getId();
    }
}