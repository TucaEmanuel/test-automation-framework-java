package com.emanueltuca.automation.bdd.assertions;

import com.emanueltuca.automation.bdd.context.PageContext;
import com.emanueltuca.automation.ui.pages.LoginPage;
import com.emanueltuca.automation.ui.pages.ProductCatalogPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatalogAssertions {
    private static final Logger logger = LoggerFactory.getLogger(CatalogAssertions.class);

    private final PageContext pageContext;

    public CatalogAssertions(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public void assertCatalogIsDisplayed() {
        logger.debug("Verifying that catalog page is displayed");
        ProductCatalogPage catalogPage = pageContext.getCurrentPage(ProductCatalogPage.class);
        assertTrue(catalogPage.isOpen(), "Catalog page should be displayed");
    }
}
