package com.emanueltuca.automation.bdd.flows;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.LoginPage;
import io.qameta.allure.Step;
            import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFlow {
    private static final Logger logger = LoggerFactory.getLogger(LoginFlow.class);

    private final TestContext testContext;

    public LoginFlow(TestContext testContext) {
        this.testContext = testContext;
    }

    @Step("Open Login Page")
    public void openLoginPage() {
        logger.info("Opening login page");
        testContext.transitionTo(LoginPage.class).open();
        logger.info("Login page opened successfully");
    }

    @Step("Login with username '{username}'")
    public void loginAs(String username, String password) {
        logger.info("Starting valid login flow with username: {}", username);
        testContext.getCurrentPage(LoginPage.class)
                .enterUsername(username)
                .enterPassword(password)
                .submitLoginExpectingSuccess();
        logger.info("Login flow executed successfully");
    }

    @Step("Login with invalid credentials username '{username}'")
    public void loginAsInvalid(String username, String password) {
        logger.info("Starting invalid login flow with username: {}", username);
        testContext.getCurrentPage(LoginPage.class)
                .enterUsername(username)
                .enterPassword(password)
                .submitLoginExpectingFailure();
        logger.info("Invalid login flow executed successfully");
    }

}
