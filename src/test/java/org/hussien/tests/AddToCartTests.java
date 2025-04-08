package org.hussien.tests;


import org.hussien.core.utils.ConfigReader;
import org.hussien.core.utils.ReportManager;
import org.hussien.pages.*;
import org.hussien.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTests extends BaseTest {

    @Test
    public void testSuccessfulAddToCart() {
        try {

            HomePage homepage = new HomePage();
            homepage.clickSignIn();
            LoginPage loginPage = new LoginPage();

            loginPage.enterUsername(ConfigReader.get("username"));
            ReportManager.logStepWithScreenshot("Username entered");

            loginPage.clickContinue();
            ReportManager.logStepWithScreenshot("Continue clicked");

            loginPage.enterPassword(ConfigReader.get("pwd"));
            ReportManager.logStepWithScreenshot("Password entered");

            loginPage.clickSignIn();
            ReportManager.logStepWithScreenshot("Sign in clicked");

            homepage.selectCategory("Video Games");
            ReportManager.logStepWithScreenshot("Category selected");
            Assert.assertTrue(homepage.isCategorySelected());
            ReportManager.logStepWithScreenshot("Category selected successfully");

            SearchResultsPage searchResultsPage = new SearchResultsPage();
            searchResultsPage.applyFilter("Free Shipping");
            searchResultsPage.applyFilter("New");
            searchResultsPage.applySorting("Price: High to Low");
            searchResultsPage.addProductsToCart(15000, "below");

            searchResultsPage.clickOnCartIcon();

            ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
            shoppingCartPage.assertTotalPriceInCart();
            ReportManager.logStepWithScreenshot("Correct total price of matching products");
            shoppingCartPage.assertAllProductsInCart();
            ReportManager.logStepWithScreenshot("All products are added successfully");

            shoppingCartPage.clickOnProceedToBuy();

            CheckoutPage checkoutPage = new CheckoutPage();
            checkoutPage.clickOnChangeAddress();
            checkoutPage.clickOnAddNewAddressLink();
            checkoutPage.fillAddressForm("Full name (First and Last name)", "Hussien Adel");
            checkoutPage.fillAddressForm("Street name", "New fostat city");
            checkoutPage.fillAddressForm("City/Area", "Misr Al Qadimah");
            checkoutPage.fillMobileNoInAddressForm("Mobile number", "1122104398");
            checkoutPage.fillAddressForm("Building name/no", "53");
            checkoutPage.fillAddressForm("District", "Al-Fostat");
            checkoutPage.fillAddressForm("Nearest landmark", "National museum of civilization");

            checkoutPage.clickOnAddAddress();
            ReportManager.logStepWithScreenshot("Address is added successfully");

        } catch (Exception e) {
            ReportManager.logFailWithScreenshot("Test failed: " + e.getMessage());
            throw e;
        }
    }
}