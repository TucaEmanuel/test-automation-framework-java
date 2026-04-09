package com.emanueltuca.automation.bdd.stepdefinitions;

import com.emanueltuca.automation.bdd.context.PageContext;
import com.emanueltuca.automation.bdd.context.TestContext;
import com.emanueltuca.automation.ui.pages.ProductCatalogPage;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductCatalogSteps {
    private static final Logger logger = LoggerFactory.getLogger(ProductCatalogSteps.class);

    private final PageContext pageContext;
    private final TestContext testContext;

    public ProductCatalogSteps(PageContext pageContext, TestContext testContext) {
        this.pageContext = pageContext;
        this.testContext = testContext;
        logger.debug("ProductCatalogSteps initialized");
    }

    @Then("user is on the product catalog page")
    public void verifyProductPage() {
        logger.info("Executing step: user is redirected to the Product Catalog Page");
        ProductCatalogPage productCatalogPage = pageContext.getCurrentPage(ProductCatalogPage.class);
        assertTrue(productCatalogPage.isOpen(), "Product Catalog Page should be opened after login");
        logger.info("Product Catalog Page verification passed");
    }
}
