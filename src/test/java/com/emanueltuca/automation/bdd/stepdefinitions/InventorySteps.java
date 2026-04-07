package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.InventoryPage;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventorySteps {
    private static final Logger logger = LoggerFactory.getLogger(InventorySteps.class);

    private final TestContext testContext;

    public InventorySteps(TestContext testContext) {
        this.testContext = testContext;
        logger.debug("InventorySteps initialized");
    }

    @Then("user is redirected to the inventory page")
    public void verifyHomePage() {
        logger.info("Executing step: user is redirected to the inventory page");
        InventoryPage currentPage = testContext.getCurrentPage(InventoryPage.class);
        assertTrue(currentPage.isPageOpened(), "Inventory page should be opened after login");
        logger.info("Inventory page verification passed");
    }
}
