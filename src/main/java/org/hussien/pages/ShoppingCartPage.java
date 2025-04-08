package org.hussien.pages;

import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static org.hussien.pages.SearchResultsPage.addedProductNames;
import static org.hussien.pages.SearchResultsPage.expectedTotalPrice;

public class ShoppingCartPage extends BasePage {

    SoftAssert softAssert = new SoftAssert();

    By proceedToBuyBtn = By.xpath("//input[@value='Proceed to checkout']");
    By checkoutTitle = By.xpath("//h1[normalize-space(text())='Checkout']");

    public void assertTotalPriceInCart() {
        try {
            WaitUtils.waitForPageLoad();

            // Extract actual total price
            WebElement totalPriceElement = WaitUtils.waitForVisibility(
                    By.xpath("//span[contains(@id, 'sc-subtotal-amount-activecart')]/span")
            );

            String totalText = totalPriceElement.getText()
                    .replaceAll("[^0-9.]", "") // Remove non-numeric characters
                    .trim();

            double actualTotalPrice = Double.parseDouble(totalText);

            System.out.println("Actual total price in cart: " + actualTotalPrice);
            System.out.println("Expected total price in cart: " + expectedTotalPrice);

            // Assertion
            Assert.assertEquals(actualTotalPrice, expectedTotalPrice);
        } catch (Exception e) {
            System.out.println("Error verifying total price in cart: " + e.getMessage());
        }
    }


    public void assertAllProductsInCart() {

        By cartItemLocator = By.xpath("//div[@data-name='Active Items']//div[@class='sc-list-item-content']");
        List<WebElement> cartItems = WaitUtils.waitForPresenceOfElements(cartItemLocator);

        List<String> actualCartProductNames = new ArrayList<>();
        for (WebElement cartItem : cartItems) {
            try {
                WebElement nameElement = cartItem.findElement(By.xpath(".//span[contains(@class,'a-truncate-cut')]"));
                actualCartProductNames.add(nameElement.getText().trim());
            } catch (Exception e) {
                System.out.println("Could not extract name from cart item: " + e.getMessage());
            }
        }
        System.out.println("Actual Product names: " + actualCartProductNames);
        System.out.println("Expected Product Count: " + addedProductNames.size());
        System.out.println("🛒 Actual Cart Product Count: " + actualCartProductNames.size());

        // Count assertion
        softAssert.assertEquals(
                actualCartProductNames.size(),
                addedProductNames.size(),
                "Mismatch in product count"
        );

        // Smart match on product names
        for (String expected : addedProductNames) {
            String expectedStart = expected.length() > 50 ? expected.substring(0, 50).toLowerCase() : expected.toLowerCase();

            boolean found = actualCartProductNames.stream()
                    .anyMatch(actual -> expected.toLowerCase().startsWith(actual.toLowerCase()) ||
                            actual.toLowerCase().startsWith(expectedStart));

            softAssert.assertTrue(found, "Product not matched in cart: " + expected);
        }

        softAssert.assertAll();
    }



    public void clickOnProceedToBuy() {
        WaitUtils.waitForClickable(proceedToBuyBtn).click();
        Assert.assertTrue(isDisplayed(checkoutTitle));
    }
}
