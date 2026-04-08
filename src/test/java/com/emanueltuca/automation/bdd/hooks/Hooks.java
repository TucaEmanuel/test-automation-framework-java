package com.emanueltuca.automation.bdd.hooks;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.core.driver.DriverFactory;
import com.emanueltuca.automation.utils.ScreenshotUtils;
import com.emanueltuca.automation.utils.reporting.AllureManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Collections.replaceAll;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========== START SCENARIO: {} ==========", scenario.getName());

        //Add scenario name to ThreadContext for better logging correlation
        String scenarioKey = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
        ThreadContext.put("scenario", scenarioKey);

        // Initialize Allure Manager (environment, categories)
        AllureManager.initialize();

        // Setup test metadata (feature, tags)
        AllureManager.setupTestMetadata(scenario);

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
            testContext.cleanup();
            DriverFactory.quitDriver();
        }

        logger.info("========== END SCENARIO: {} ==========", scenario.getName());
    }

    private void handleFailureArtifacts(Scenario scenario, WebDriver driver) {
        if (scenario.isFailed() && driver != null) {
            logger.error("Scenario failed: {}", scenario.getName());

            // Capture screenshot
            byte[] screenshot = ScreenshotUtils.takeScreenshotAsBytes(driver);

            // Attach screenshot to Allure report
            AllureManager.attachScreenshot("Failure Screenshot", screenshot);
            AllureManager.attachText("Failure Context",
                    ("Scenario: " + scenario.getName() + "\n" +
                            "URL: " + driver.getCurrentUrl() + "\n" +
                            "Title: " + driver.getTitle() + "\n" +
                            "Thread: " + Thread.currentThread().getId()
                    ).getBytes());

            // Save to file system
            String savedPath = ScreenshotUtils.saveScreenshot(screenshot, scenario.getName());
            logger.error("Screenshot saved at: {}", savedPath);
        }
    }
}