package com.emanueltuca.automation.bdd.hooks;

import com.emanueltuca.automation.core.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    private DriverFactory driverFactory;

    @Before
    public void setUp() {
        driverFactory = new DriverFactory();
        driverFactory.initializeDriver();
    }

    @After
    public void tearDown() {
        if (driverFactory != null) {
            driverFactory.quitDriver();
        }
    }
}