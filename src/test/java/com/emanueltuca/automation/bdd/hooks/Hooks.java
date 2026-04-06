package com.emanueltuca.automation.bdd.hooks;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.core.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

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
    public void tearDown() {
        // Reset navigation state to prevent stale references
        testContext.cleanup();
        DriverFactory.quitDriver();
    }
}