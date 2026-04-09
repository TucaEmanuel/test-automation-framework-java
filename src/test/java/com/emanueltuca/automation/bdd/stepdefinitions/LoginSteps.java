package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.bdd.assertions.CatalogAssertions;
import com.emanueltuca.automation.bdd.assertions.LoginAssertions;
import com.emanueltuca.automation.bdd.context.PageContext;
import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.bdd.flows.LoginFlow;
import com.emanueltuca.automation.domain.DemoUser;
import com.emanueltuca.automation.domain.LoginErrorMessage;
import io.cucumber.java.en.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);

    private final PageContext pageContext;
    private final TestContext context;

    private final LoginFlow loginFlow;
    private final LoginAssertions loginAssertions;
    private final CatalogAssertions catalogAssertions;

    public LoginSteps(PageContext pageContext, TestContext testContext) {
        this.pageContext = pageContext;
        this.context = testContext;
        this.loginFlow = new LoginFlow(pageContext);
        this.loginAssertions = new LoginAssertions(pageContext);
        this.catalogAssertions = new CatalogAssertions(pageContext);

        logger.debug("LoginSteps initialized");
    }

    @Given("the authentication page is displayed")
    public void userIsOnAuthenticationPage() {
        loginFlow.openLoginPage();
    }

    @Given("a {demoUser} user")
    public void theCurrentUserIs(DemoUser user) {
        context.setCurrentUser(user);
    }

    @Given("the user provides username {string}")
    public void userProvidesUsername(String username) {
        context.setUsername(username);
    }

    @Given("the user provides password {string}")
    public void userProvidesPassword(String password) {
        context.setPassword(password);
    }

    @When("he authenticates")
    public void heAuthenticates() {
        if (context.hasCurrentUser()) {
            loginFlow.login(context.getCurrentUser());
        } else {
            loginFlow.login(context.getUsername(), context.getPassword());
        }
    }

    @Then("he gains access to the product catalog")
    public void heGainsAccessToTheProductCatalog() {
        catalogAssertions.assertCatalogIsDisplayed();
    }

    @Then("he remains unauthenticated")
    public void heRemainsUnauthenticated() {
        loginAssertions.assertLoginPageIsDisplayed();
    }

    @Then("he is informed that the {loginError}")
    public void heIsInformedOfTheError(LoginErrorMessage error) {
        loginAssertions.assertErrorMessageIsDisplayed(error);
    }


//    Then he is informed that the account is locked
//    Then he is informed that the credentials are invalid
//    Then he is informed that {string} is required


}