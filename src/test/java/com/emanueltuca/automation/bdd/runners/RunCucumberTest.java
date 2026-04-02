package com.emanueltuca.automation.bdd.runners;

import static io.cucumber.junit.platform.engine.Constants.*;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.emanueltuca.automation.bdd.stepdefinitions,com.emanueltuca.automation.bdd.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class RunCucumberTest {
}