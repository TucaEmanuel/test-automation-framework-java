package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.InventoryPage;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventorySteps {
    private final TestContext testContext;

    public InventorySteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("user is redirected to the inventory page")
    public void verifyHomePage() {
        InventoryPage currentPage = testContext.getCurrentPage(InventoryPage.class);
        assertTrue(currentPage.isPageOpened(), "Inventory page should be opened after login");
    }
}
