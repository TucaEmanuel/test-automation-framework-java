package com.emanueltuca.automation.bdd.assertions;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginAssertions {
    private static final Logger logger = LoggerFactory.getLogger(LoginAssertions.class);

    private final TestContext testContext;

    public LoginAssertions(TestContext testContext) {
        this.testContext = testContext;
    }

    public void verifyErrorMessage(String expectedText) {
        logger.debug("Verifying error message: {}", expectedText);
        String actualText = testContext.getCurrentPage(LoginPage.class).getErrorMessageText();
        assertEquals(expectedText, actualText, "Expected error message to be: " + expectedText + " but was: " + actualText);
        logger.info("Error message verified successfully");
    }
}
