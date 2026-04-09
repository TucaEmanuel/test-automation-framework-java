package com.emanueltuca.automation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCatalogPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ProductCatalogPage.class);

    private final By inventoryContainer = By.id("inventory_container");

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
        logger.debug("InventoryPage initialized");
    }

    protected By getDistinctiveElementToCheckIfPageIsLoaded() {
        return inventoryContainer;
    }
}
