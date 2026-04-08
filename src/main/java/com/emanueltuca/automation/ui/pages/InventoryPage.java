package com.emanueltuca.automation.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(InventoryPage.class);

    private final By inventoryContainer = By.id("inventory_container");
    private final By pageTitle = By.cssSelector(".title");

    public InventoryPage(WebDriver driver) {
        super(driver);
        logger.debug("InventoryPage initialized");
    }

    public boolean isPageOpened() {
        logger.debug("Checking if inventory page is opened");
        boolean opened = isDisplayed(inventoryContainer);
        logger.debug("Inventory page opened: {}", opened);
        return opened;
    }

    protected By getDistinctiveElementToCheckIfPageIsLoaded() {
        return inventoryContainer;
    }

    public String getTitle() {
        logger.debug("Retrieving inventory page title");
        String title = getText(pageTitle);
        logger.debug("Inventory page title: {}", title);
        return title;
    }
}
