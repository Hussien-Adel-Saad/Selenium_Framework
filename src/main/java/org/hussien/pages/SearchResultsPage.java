package org.hussien.pages;

import org.hussien.core.driver.WebDriverFactory;
import org.hussien.core.utils.InteractionUtils;
import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage {
    // Locators
    private final By dropdownElement = By.id("s-result-sort-select");
    private final By productCards = By.xpath("//div[@data-component-type='s-search-result']");
    private final By addToCartBtn = By.xpath(".//button[text()='Add to cart']");
    private final By priceWhole = By.xpath(".//span[@class='a-price-whole']");
    private final By priceFraction = By.xpath(".//span[@class='a-price-fraction']");
    private final By productName = By.xpath(".//h2//span");
    private final By nextPageBtn = By.xpath("//a[contains(@class, 's-pagination-next') and not(contains(@class, 's-pagination-disabled'))]");
    private final By cartIcon = By.id("nav-cart");

    // State
    public static double expectedTotalPrice;
    public static List<String> addedProductNames = new ArrayList<>();

    public void applyFilter(String filterName) {
        By locator = By.xpath(String.format(
                "//div[@id='s-refinements']//span[normalize-space(text())='%s']/ancestor::a",
                filterName
        ));
        InteractionUtils.click(locator);
        waitForFilterApplied(filterName);
    }

    public void applySorting(String sortType) {
        Select dropdown = new Select(WaitUtils.waitForClickable(dropdownElement));
        dropdown.selectByVisibleText(sortType);
        WaitUtils.waitForPageLoad();
    }

    private void waitForFilterApplied(String filterName) {
        By locator = By.xpath(String.format(
                "//div[@id='s-refinements']//span[normalize-space(text())='%s' and contains(@class,'bold')]",
                filterName
        ));
        WaitUtils.waitForVisibility(locator);
    }

    public void addProductsToCart(double targetPrice, String condition) {
        boolean shouldStopSearching = false;
        expectedTotalPrice = 0.0;
        addedProductNames.clear();

        while (!shouldStopSearching) {
            WaitUtils.waitForPageLoad();
            WaitUtils.waitForMillis(3000);

            List<WebElement> products = WaitUtils.waitForPresenceOfElements(productCards);
            boolean foundMatchOnPage = false;

            for (WebElement product : products) {
                try {
                    // Use InteractionUtils for child elements
                    List<WebElement> addButtons = InteractionUtils.findChildElements(product, addToCartBtn);
                    if (addButtons.isEmpty()) continue;

                    List<WebElement> wholeParts = InteractionUtils.findChildElements(product, priceWhole);
                    List<WebElement> fractionParts = InteractionUtils.findChildElements(product, priceFraction);
                    if (wholeParts.isEmpty() || fractionParts.isEmpty()) continue;

                    String whole = wholeParts.getFirst().getText().replace(",", "").trim();
                    String fraction = fractionParts.getFirst().getText().trim();
                    double price = Double.parseDouble(whole + "." + fraction);

                    boolean matchesCondition = condition.equalsIgnoreCase("below") ?
                            price < targetPrice : price > targetPrice;

                    if (matchesCondition) {
                        foundMatchOnPage = true;
                        WebElement nameElement = InteractionUtils.findChildElement(product, productName);
                        String productNameText = nameElement.getText().trim();


                        WebElement addButton = addButtons.getFirst();
                        addButton.click();
                        WaitUtils.waitForMillis(500);

                        addedProductNames.add(productNameText);
                        expectedTotalPrice += price;
                    }
                } catch (Exception ignored) {

                }
            }

            if (foundMatchOnPage) {
                shouldStopSearching = true;
            } else {
                try {
                    WebElement nextPage = WaitUtils.waitForClickable(nextPageBtn);
                    nextPage.click();
                } catch (Exception e) {
                    shouldStopSearching = true;
                }
            }
        }
    }

    public void clickOnCartIcon() {

        WebElement cart = WaitUtils.waitForClickable(cartIcon);
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("arguments[0].click();", cart);
        WaitUtils.waitForPageLoad();
    }

}