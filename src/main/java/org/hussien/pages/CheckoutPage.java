package org.hussien.pages;

import org.hussien.core.utils.InteractionUtils;
import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;


public class CheckoutPage extends BasePage {
    // Locators
    private static final By CHANGE_ADDRESS_LINK = By.id("addressChangeLinkId");
    private static final By DELIVERY_INSTRUCTIONS = By.xpath("//a[normalize-space()='Add delivery instructions']");
    private static final By TOTAL_PRICE_ROW = By.xpath("(//tr[contains(@class, 'order-summary-unidenfitied-style')])[2]/td[2]");
    private static final By SHIPPING_FEE_ROW = By.xpath("(//tr[contains(@class, 'order-summary-unidenfitied-style')])[1]/td[2]");
    private static final By FULLNAME_AFTER_ADD = By.xpath("//li[contains(@class,'FullName')]");

    public void clickOnChangeAddress() {

        try {
            InteractionUtils.click(CHANGE_ADDRESS_LINK);
        } catch (Exception e) {

            InteractionUtils.clickWithJS(CHANGE_ADDRESS_LINK);
        }
    }

    public void clickOnAddNewAddress() {
        InteractionUtils.clickOnButtonWithText("Add a new address");
    }

    public static void waitForAddressToLoad() {
        WaitUtils.waitForVisibility(FULLNAME_AFTER_ADD);
    }

//    public void assertPriceExcludingShipping() {
//        waitForCheckoutToLoad();
//
//        double totalPrice = extractPrice(TOTAL_PRICE_ROW);
//        double shippingFee = extractPrice(SHIPPING_FEE_ROW);
//        double calculatedPrice = totalPrice - shippingFee;
//
//        System.out.printf("Price Verification - Expected: %.2f, Calculated: %.2f%n",
//                SearchResultsPage.expectedTotalPrice, calculatedPrice);
//
//        Assert.assertEquals(calculatedPrice, SearchResultsPage.expectedTotalPrice, 0.01,
//                "Price excluding shipping mismatch");
//    }
//
//    private void waitForCheckoutToLoad() {
//        WaitUtils.waitForVisibility(DELIVERY_INSTRUCTIONS);
//        WaitUtils.waitFor(driver -> {
//            try {
//                return !WebDriverFactory.getDriver().findElements(TOTAL_PRICE_ROW).isEmpty() &&
//                        !WebDriverFactory.getDriver().findElements(SHIPPING_FEE_ROW).isEmpty();
//            } catch (Exception e) {
//                return false;
//            }
//        }, Duration.ofSeconds(5));
//    }
//
//    private double extractPrice(By locator) {
//        String priceText = WaitUtils.waitForVisibility(locator)
//                .getText()
//                .replaceAll("[^0-9.]", "");
//        return Double.parseDouble(priceText);
//    }
}