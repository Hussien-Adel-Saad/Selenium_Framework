package org.hussien.pages;

import org.hussien.core.utils.BrowserUtils;
import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.hussien.pages.SearchResultsPage.*;

public class ShoppingCartPage extends BasePage {
    // Locators
    private static final By PROCEED_TO_BUY_BTN = By.xpath("//input[@value='Proceed to checkout']");
    private static final By TOTAL_PRICE = By.xpath("//span[contains(@id, 'sc-subtotal-amount-activecart')]/span");
    private static final By CART_ITEMS = By.xpath("//div[@data-name='Active Items']//div[@class='sc-list-item-content']");
    private final By cartItemDelete = By.xpath("//input[@value='Delete']");

    // Custom wait for cart to stabilize
    private void waitForCartToUpdate() {
        WaitUtils.waitFor(driver -> {
            try {
                return !driver.findElements(TOTAL_PRICE).isEmpty() &&
                        !driver.findElements(CART_ITEMS).isEmpty();
            } catch (Exception e) {
                return false;
            }
        }, 8);
    }

    public void assertTotalPriceInCart() {
        waitForCartToUpdate();

        double actualTotalPrice = extractNumericPrice();
        System.out.printf("Price Verification - Expected: %.2f, Actual: %.2f%n",
                expectedTotalPrice, actualTotalPrice);

        Assert.assertEquals(actualTotalPrice, expectedTotalPrice, 0.01, "Total price mismatch");
    }

    public void assertItemCountInCart() {
        waitForCartToUpdate();

        int actualItemCount = WaitUtils.waitForPresenceOfElements(CART_ITEMS).size();
        System.out.printf("Item Count Verification - Expected: %d, Actual: %d%n",
                addedProductNames.size(), actualItemCount);

        Assert.assertEquals(actualItemCount, addedProductNames.size(),
                "Item count in cart doesn't match expected");
    }

    private double extractNumericPrice() {
        String priceText = WaitUtils.waitForVisibility(ShoppingCartPage.TOTAL_PRICE)
                .getText()
                .replaceAll("[^0-9.]", "");
        return Double.parseDouble(priceText);
    }

    public void proceedToCheckout() {
        WaitUtils.waitForClickable(PROCEED_TO_BUY_BTN).click();
        WaitUtils.waitForVisibility(By.id("addressChangeLinkId"));
    }


    public void clearCart() {
        BrowserUtils.navigateTo("https://www.amazon.eg/-/en/gp/cart/view.html?ref_=nav_cart");

        try {

            while (true) {
                List<WebElement> deleteButtons = WaitUtils.waitForPresenceOfElements(cartItemDelete);
                if (deleteButtons.isEmpty()) break;

                try {
                    deleteButtons.getFirst().click();

                } catch (Exception e) {
                    System.out.println("Delete attempt failed: " + e.getMessage());
                }
            }

            // Assert that the cart is empty by checking subtotal label
            WebElement subtotalLabel = WaitUtils.waitForVisibility(By.id("sc-subtotal-label-activecart"));
            String actualText = subtotalLabel.getText().trim();
            Assert.assertEquals(actualText, "Subtotal (0 items):", "Cart is not empty as expected.");

            //reset your state variables
            expectedTotalPrice = 0.0;
            addedProductNames.clear();

        } catch (TimeoutException e) {
            System.out.println("Cart was already empty");
        }
    }



}