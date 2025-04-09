package org.hussien.pages;

import org.hussien.core.utils.WaitUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;

public class AddressForm extends BasePage {
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
}
