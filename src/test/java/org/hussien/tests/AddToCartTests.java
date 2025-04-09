package org.hussien.tests;


import com.fasterxml.jackson.databind.JsonNode;
import org.hussien.core.utils.ConfigReader;
import org.hussien.core.utils.JSONParser;
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

            JSONParser parser = new JSONParser();
            JsonNode addressData = parser.parseJSON("/testData/addressDetails.json");

            
            String fullName = JSONParser.getValue(addressData, "Full name (First and Last name)");
            String streetName = JSONParser.getValue(addressData, "Street name");
            String city = JSONParser.getValue(addressData, "City/Area");
            String mobileNumber = JSONParser.getValue(addressData, "Mobile number");
            String buildingNo = JSONParser.getValue(addressData, "Building name/no");
            String district = JSONParser.getValue(addressData, "District");
            String nearestLandmark = JSONParser.getValue(addressData, "Nearest landmark");

            AddressForm addressForm = new AddressForm();
            addressForm.fillAddressForm("Full name (First and Last name)", fullName);
            addressForm.fillAddressForm("Street name", streetName);
            addressForm.fillAddressForm("City/Area", city);
            addressForm.fillMobileNoInAddressForm("Mobile number", mobileNumber);
            addressForm.fillAddressForm("Building name/no", buildingNo);
            addressForm.fillAddressForm("District", district);
            addressForm.fillAddressForm("Nearest landmark", nearestLandmark);

            addressForm.clickOnAddAddress();
            ReportManager.logStepWithScreenshot("Address is added successfully");

        } catch (Exception e) {
            ReportManager.logFailWithScreenshot("Test failed: " + e.getMessage());
            throw e;
        }
    }
}