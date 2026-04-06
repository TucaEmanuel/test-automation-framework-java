package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.bdd.assertions.LoginAssertions;
import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.bdd.flows.LoginFlow;
import io.cucumber.java.en.*;

public class LoginSteps {

    private final LoginFlow loginFlow;
    private final LoginAssertions loginAssertions;

    public LoginSteps(TestContext context) {
        this.loginFlow = new LoginFlow(context);
        this.loginAssertions = new LoginAssertions(context);
    }

    @Given("the login page is opened")
    public void openLoginPage() {
        loginFlow.openLoginPage();
    }

    @When("^user logs in with (valid|invalid) credentials: \"(.*)\" username and \"(.*)\" password$")
    public void userLogsInWithSpecificCredentials(String option, String username, String password) {
        if ("valid".equals(option)) {
            loginFlow.loginAs(username, password);
        } else {
            loginFlow.loginAsInvalid(username, password);
        }
    }

    @Then("the error message with {string} text is displayed")
    public void theErrorMessageWithTextIsDisplayed(String errorMessage) {
        loginAssertions.verifyErrorMessage(errorMessage);
    }

}