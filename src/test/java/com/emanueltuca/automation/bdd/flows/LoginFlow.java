package com.emanueltuca.automation.bdd.flows;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.InventoryPage;
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
        LoginPage loginPage = testContext.getPage(LoginPage.class);
        loginPage.open()
                .waitUntilLoaded();

        testContext.setCurrentPage(LoginPage.class);
        logger.info("Login page opened successfully");
    }

    @Step("Login with username '{username}'")
    public InventoryPage submitValidCredentials(String username, String password) {
        logger.info("Starting valid login flow with username: {}", username);
        LoginPage loginPage = testContext.getCurrentPage(LoginPage.class);

        loginPage.enterUsername(username)
                .enterPassword(password)
                .submitLoginForm();

        InventoryPage inventoryPage = testContext.getPage(InventoryPage.class);
        inventoryPage.waitUntilLoaded();
        testContext.setCurrentPage(InventoryPage.class);
        logger.info("Login flow executed successfully");

        return inventoryPage;
    }

    @Step("Login with invalid credentials username '{username}'")
    public LoginPage submitInvalidCredentials(String username, String password) {
        logger.info("Starting invalid login flow with username: {}", username);
        LoginPage loginPage = testContext.getCurrentPage(LoginPage.class);
        loginPage.enterUsername(username)
                .enterPassword(password)
                .submitLoginForm();

        loginPage.waitUntilLoaded();
        testContext.setCurrentPage(LoginPage.class);

        logger.info("Invalid login flow executed successfully");
        return loginPage;
    }

}
