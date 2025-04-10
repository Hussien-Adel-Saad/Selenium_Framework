package org.hussien.pages;

import org.hussien.core.utils.InteractionUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    // Locators as constants
    private static final By ACCOUNT_LIST = By.id("nav-link-accountList");

    // Instance-specific state
    private String currentCategory;

    public void clickSignIn() {
        InteractionUtils.hover(ACCOUNT_LIST);
        InteractionUtils.clickOnButtonWithText("Sign in");
    }

    public void selectCategory(String category) {
        By categoryLocator = By.xpath(String.format(
                "//li[@class='nav-li']//a[normalize-space()='%s']",
                category
        ));
        InteractionUtils.click(categoryLocator);
        this.currentCategory = category;
    }

    public boolean isCategorySelected() {
        return InteractionUtils.isDisplayed(By.xpath(String.format(
                "//li[@class='nav-li']//a[normalize-space()='%s']",
                currentCategory
        )));
    }
}