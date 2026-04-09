package com.emanueltuca.automation.bdd.flows;

import com.emanueltuca.automation.bdd.context.PageContext;
import com.emanueltuca.automation.domain.DemoUser;
import com.emanueltuca.automation.ui.pages.ProductCatalogPage;
import com.emanueltuca.automation.ui.pages.LoginPage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginFlow {
    private static final Logger logger = LoggerFactory.getLogger(LoginFlow.class);

    private final PageContext pageContext;

    public LoginFlow(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    @Step("Open Login Page")
    public void openLoginPage() {
        logger.info("Opening login page");
        LoginPage loginPage = pageContext.getPage(LoginPage.class);
        loginPage.open()
                .waitUntilLoaded();

        pageContext.setCurrentPage(LoginPage.class);
        logger.info("Login page opened successfully");
    }

    @Step("Login with valid credentials for user '{user.username}'")
    public void login(DemoUser user) {
        logger.info("Starting valid login flow with username: {}", user.getUsername());
        LoginPage loginPage = pageContext.getCurrentPage(LoginPage.class);

        loginPage.enterUsername(user.getUsername())
                .enterPassword(user.getPassword())
                .submitLoginForm();

        if(pageContext.getPage(ProductCatalogPage.class).isOpen()){
            pageContext.setCurrentPage(ProductCatalogPage.class);
            logger.info("Login flow executed successfully");
            return;
        }

        if(pageContext.getPage(LoginPage.class).isOpen()){
            pageContext.setCurrentPage(LoginPage.class);
            logger.info("Login flow executed but login page is still open, likely due to invalid credentials or locked out account");
        }
    }

    @Step("Login with invalid credentials username '{username}'")
    public void login(String username, String password) {
        logger.info("Starting invalid login flow with username: {}", username);
        LoginPage loginPage = pageContext.getCurrentPage(LoginPage.class);
        loginPage.enterUsername(username)
                .enterPassword(password)
                .submitLoginForm();

        loginPage.waitUntilLoaded();
        pageContext.setCurrentPage(LoginPage.class);

        logger.info("Invalid login flow executed successfully");
    }

}
