package com.emanueltuca.automation.bdd.assertions;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginAssertions {
    private final TestContext testContext;

    public LoginAssertions(TestContext testContext) {
        this.testContext = testContext;
    }

    public void verifyErrorMessage(String expectedText) {
        String actualText = testContext.getCurrentPage(LoginPage.class).getErrorMessageText();
        assertEquals(expectedText, actualText, "Expected error message to be: " + expectedText + " but was: " + actualText);
    }
}
