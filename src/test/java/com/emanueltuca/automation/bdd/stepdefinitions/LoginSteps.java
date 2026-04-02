package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.core.driver.DriverFactory;
import io.cucumber.java.en.*;
import com.emanueltuca.automation.ui.pages.LoginPage;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("I open the login page")
    public void openLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(DriverFactory.getDriver());
        }
        loginPage.open();
    }

    @When("I login with valid credentials")
    public void login() {
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @Then("I should see the home page")
    public void verifyHomePage() {
        System.out.println("Add assertion here");
    }
}