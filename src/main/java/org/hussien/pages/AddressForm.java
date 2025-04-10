package org.hussien.pages;


import org.hussien.core.utils.InteractionUtils;
import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;


public class AddressForm extends BasePage {
    // Locators
    private static final By ADDRESS_SUBMIT_BUTTON = By.id("address-ui-widgets-form-submit-button");
    private static final By FULLNAME_AFTER_ADD = By.xpath("//li[contains(@class,'FullName')]");

    // Dynamic locator builders
    private static By getFieldByAriaLabel(String label) {
        return By.xpath(String.format("//input[@aria-label='%s']", label));
    }

    private static By getPhoneFieldByLabel(String label) {
        return By.xpath(String.format(
                "//span[normalize-space()='%s']/../../following-sibling::div//input",
                label
        ));
    }

    public void fillAddressForm(String labelName, String value) {
        InteractionUtils.type(getFieldByAriaLabel(labelName), value);
    }

    public void fillMobileNumber(String labelName, String value) {
        InteractionUtils.type(getPhoneFieldByLabel(labelName), value);
    }

    public void submitAddress() {
        InteractionUtils.click(ADDRESS_SUBMIT_BUTTON);
        WaitUtils.waitForVisibility(FULLNAME_AFTER_ADD);

    }
}