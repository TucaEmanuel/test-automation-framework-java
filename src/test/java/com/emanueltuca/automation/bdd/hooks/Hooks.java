package com.emanueltuca.automation.bdd.hooks;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.core.driver.DriverFactory;
import com.emanueltuca.automation.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() {
        DriverFactory.initializeDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            handleFailureArtifacts(scenario, driver);
        } finally {
            // Reset navigation state to prevent stale references
            testContext.cleanup();
            DriverFactory.quitDriver();
        }
    }

    private void handleFailureArtifacts(Scenario scenario, WebDriver driver) {
        if (scenario.isFailed() && driver != null) {
            //capture screenshot
            byte[] screenshot = ScreenshotUtils.takeScreenshotAsBytes(driver);

            //attach it to the report
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

            // save it in the directory
            String savedPath = ScreenshotUtils.saveScreenshot(screenshot, scenario.getName());
            System.out.println("Screenshot saved at: " + savedPath);
        }
    }
}