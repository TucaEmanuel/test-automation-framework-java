package com.emanueltuca.automation.bdd.hooks;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.core.driver.DriverFactory;
import com.emanueltuca.automation.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp(Scenario scenario) {
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
            testContext.cleanup();
            DriverFactory.quitDriver();
        }

        logger.info("========== END SCENARIO: {} ==========", scenario.getName());
    }

    private void handleFailureArtifacts(Scenario scenario, WebDriver driver) {
        if (scenario.isFailed() && driver != null) {
            logger.error("Scenario failed: {}", scenario.getName());
            //capture screenshot
            byte[] screenshot = ScreenshotUtils.takeScreenshotAsBytes(driver);

            //attach it to the report
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // save it in the directory
            String savedPath = ScreenshotUtils.saveScreenshot(screenshot, scenario.getName());
            logger.error("Screenshot saved at: {}", savedPath);
        }
    }
}