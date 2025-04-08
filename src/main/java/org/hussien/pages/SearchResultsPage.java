package org.hussien.pages;

import org.hussien.core.driver.WebDriverFactory;

import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage {
    By dropdownElement = By.id("s-result-sort-select");
    static double expectedTotalPrice;
    static List<String> addedProductNames = new ArrayList<>();

    public void applyFilter(String filterName) {
        By filterLocator = By.xpath("//div[@id='s-refinements']//span[normalize-space(text())='" + filterName + "']/ancestor::a");
        click(filterLocator);
        waitForFilterApplied(filterName);
    }

    public void applySorting(String sortType) {

        Select dropdown = new Select(WaitUtils.waitForVisibility(dropdownElement));
        dropdown.selectByVisibleText(sortType);
    }


    private void waitForFilterApplied(String filterName) {
        By activeFilterLocator = By.xpath(
                "//div[@id='s-refinements']//span[normalize-space(text())='" + filterName + "' and contains(@class,'bold')]");
        WaitUtils.waitForVisibility(activeFilterLocator);
    }

     // Declare this at class level for later use

    public void addProductsToCart(double targetPrice, String condition) {
        boolean shouldStopSearching = false;
        int totalAdded = 0;
        expectedTotalPrice = 0.0;
        addedProductNames.clear(); // Reset the list before use

        while (!shouldStopSearching) {

            WaitUtils.waitForPageLoad();
            try {
                Thread.sleep(3000); // Give Amazon time to fully render results
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> products = WaitUtils.waitForPresenceOfElements(
                    By.xpath("//div[@data-component-type='s-search-result']")
            );

            boolean foundMatchOnPage = false;

            for (WebElement product : products) {
                try {
                    // Check if Add to Cart button is present
                    List<WebElement> addToCartButtons = product.findElements(
                            By.xpath(".//button[text()='Add to cart']")
                    );

                    if (addToCartButtons.isEmpty()) {
                        continue; // Skip if Add to Cart not present
                    }

                    // Extract price
                    List<WebElement> priceWholeElements = product.findElements(By.xpath(".//span[@class='a-price-whole']"));
                    List<WebElement> priceFractionElements = product.findElements(By.xpath(".//span[@class='a-price-fraction']"));

                    if (priceWholeElements.isEmpty() || priceFractionElements.isEmpty()) {
                        continue;
                    }

                    String wholePart = priceWholeElements.getFirst().getText().replace(",", "").trim();
                    String fractionPart = priceFractionElements.getFirst().getText().trim();

                    if (wholePart.isEmpty() || fractionPart.isEmpty()) {
                        continue;
                    }

                    double price = Double.parseDouble(wholePart + "." + fractionPart);

                    // Check price condition
                    boolean matchesCondition = condition.equalsIgnoreCase("below") ? price < targetPrice : price > targetPrice;

                    if (matchesCondition) {
                        foundMatchOnPage = true;

                        try {
                            WebElement productNameElement = product.findElement(By.xpath(".//h2//span"));
                            String productName = productNameElement.getText().trim();

                            WebElement addToCartBtn = addToCartButtons.getFirst();
                            WaitUtils.waitForClickableElement(addToCartBtn).click();

                            // Wait for cart update, such as button disappearing or visual feedback
                            WaitUtils.waitForAddToCartComplete(addToCartBtn);  // Ensure the cart button is updated or gone
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            totalAdded++;
                            expectedTotalPrice += price;
                            addedProductNames.add(productName);

                        } catch (Exception e) {
                            System.out.println("Failed to add a matching product.");
                        }
                    }
                } catch (Exception ignored) {
                }
            }

            if (foundMatchOnPage) {
                System.out.println("Processed all matching products on this page. Stopping.");
                shouldStopSearching = true;
            } else {
                System.out.println("No matches on this page - Checking next page...");
                try {
                    WebElement nextPage = WaitUtils.waitForClickable(
                            By.xpath("//a[contains(@class, 's-pagination-next') and not(contains(@class, 's-pagination-disabled'))]")
                    );
                    nextPage.click();
                    WaitUtils.waitForPageLoad();
                    WaitUtils.waitForPresenceOfElements(
                            By.xpath("//div[@data-component-type='s-search-result']")
                    );
                } catch (Exception e) {
                    System.out.println("No more pages available");
                    shouldStopSearching = true;
                }
            }
            System.out.println(addedProductNames);
            System.out.println(totalAdded > 0
                    ? "Successfully added " + totalAdded + " products"
                    : "No matching products found");
        }
    }





    public void clickOnCartIcon(){
        // Navigate to cart page
        WebElement cartIcon = WaitUtils.waitForClickable(By.id("nav-cart"));

        // Use JavaScript to click (bypasses overlays)
        JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getDriver();
        js.executeScript("arguments[0].click();", cartIcon);
        WaitUtils.waitForPageLoad();
    }

}