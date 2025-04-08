package org.hussien.pages;

import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CheckoutPage extends BasePage {
    By changeAddressLnk = By.id("addressChangeLinkId");

    public void clickOnChangeAddress() {
        WaitUtils.waitForClickable(changeAddressLnk).click();
    }

    public void clickOnAddNewAddressLink() {
        clickOnButtonWithText("Add a new address");
    }

    public void fillAddressForm(String labelName, String value) {
        WaitUtils.waitForPageLoad();
        type(By.xpath("//input[@aria-label='" + labelName + "']"), value);

    }

    public void fillMobileNoInAddressForm(String labelName, String value) {
        type(By.xpath("//span[normalize-space(text())='" + labelName + "']/../../following-sibling::div//input"), value);

    }

    public void clickOnAddAddress() {
        click(By.id("address-ui-widgets-form-submit-button"));
        WaitUtils.waitForVisibility(By.xpath("//h3[contains(., 'Shipping address') and @class]"));
    }

//    public void assertPriceExcludingShipping() {
//        WaitUtils.waitForVisibility(By.xpath("//a[normalize-space(text())='Add delivery instructions']"));
//        // Locate the total price (including shipping) and the shipping fee
//        WebElement totalPriceElement = WaitUtils.waitForVisibility(By.xpath("(//tr[contains(@class, 'order-summary-unidenfitied-style')])[2]/td[2]"));
//        WebElement shippingFeeElement = WaitUtils.waitForVisibility(By.xpath("(//tr[contains(@class, 'order-summary-unidenfitied-style')])[1]/td[2]"));
//
//        // Extract the text values
//        String totalPriceText = totalPriceElement.getText().replaceAll("[^0-9.]", "");
//        String shippingFeeText = shippingFeeElement.getText().replaceAll("[^0-9.]", "");
//
//        // Parse the values into double
//        double totalPrice = Double.parseDouble(totalPriceText);
//        double shippingFee = Double.parseDouble(shippingFeeText);
//
//        // Calculate the expected price without shipping
//        double priceExcludingShipping = totalPrice - shippingFee;
//
//        System.out.println("Final price: " + priceExcludingShipping);
//
//        // Assert the price excluding shipping
//        Assert.assertEquals(SearchResultsPage.expectedTotalPrice, priceExcludingShipping, "Price excluding shipping does not match expected value");
//    }

}
