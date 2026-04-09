package com.emanueltuca.automation.bdd.assertions;

import com.emanueltuca.automation.bdd.context.PageContext;
import com.emanueltuca.automation.domain.LoginErrorMessage;
import com.emanueltuca.automation.ui.pages.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAssertions {
    private static final Logger logger = LoggerFactory.getLogger(LoginAssertions.class);

    private final PageContext pageContext;

    public LoginAssertions(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public void assertLoginPageIsDisplayed() {
        logger.debug("Verifying that login page is displayed");
        LoginPage loginPage = pageContext.getCurrentPage(LoginPage.class);
        assertTrue(loginPage.isOpen(), "Login page should be displayed");
    }

    public void assertErrorMessageIsDisplayed(LoginErrorMessage loginErrorMessage) {
        String expectedErrorText = loginErrorMessage.getErrorText();
        logger.debug("Verifying the displayed error message: {}", expectedErrorText);
        String actualText = pageContext.getCurrentPage(LoginPage.class).getErrorMessageText();
        assertEquals(expectedErrorText, actualText, "Expected error message to be: " + expectedErrorText + " but was: " + actualText);
    }
}
