package com.emanueltuca.automation.ui.pages;

import com.emanueltuca.automation.utils.PageNavigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private final By inventoryContainer = By.id("inventory_container");
    private final By pageTitle = By.cssSelector(".title");

    public InventoryPage(WebDriver driver, PageNavigator navigator) {
        super(driver, navigator);
    }

    public boolean isPageOpened() {
        return isDisplayed(inventoryContainer);
    }

    protected By getDistinctiveElementToCheckIfPageIsLoaded() {
        return inventoryContainer;
    }

    public String getTitle() {
        return getText(pageTitle);
    }
}
