package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.bdd.assertions.LoginAssertions;
import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.bdd.flows.LoginFlow;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);

    private final LoginFlow loginFlow;
    private final LoginAssertions loginAssertions;

    public LoginSteps(TestContext context) {
        this.loginFlow = new LoginFlow(context);
        this.loginAssertions = new LoginAssertions(context);
        logger.debug("LoginSteps initialized");
    }

    @Given("the login page is opened")
    public void openLoginPage() {
        logger.info("Executing step: the login page is opened");
        loginFlow.openLoginPage();
    }

    @When("^user logs in with (valid|invalid) credentials: \"(.*)\" username and \"(.*)\" password$")
    public void userLogsInWithSpecificCredentials(String option, String username, String password) {
        logger.info("Executing step: user logs in with {} credentials", option);
        if ("valid".equals(option)) {
            loginFlow.submitValidCredentials(username, password);
        } else {
            loginFlow.submitInvalidCredentials(username, password);
        }
    }

    @Then("the error message with {string} text is displayed")
    public void theErrorMessageWithTextIsDisplayed(String errorMessage) {
        logger.info("Executing step: the error message with '{}' text is displayed", errorMessage);
        loginAssertions.verifyErrorMessage(errorMessage);
    }

}