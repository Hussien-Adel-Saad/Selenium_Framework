package org.hussien.pages;

import org.hussien.core.utils.InteractionUtils;
import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    // Locators as constants
    private static final By USERNAME_FIELD = By.xpath("//input[@type='email']");
    private static final By PASSWORD_FIELD = By.xpath("//input[@type='password']");
    private static final By CONTINUE_BTN = By.id("continue");
    private static final By SIGN_IN_BTN = By.id("auth-signin-button");

    // Fluent API for better test readability
    public void enterUsername(String username) {
        InteractionUtils.type(USERNAME_FIELD, username);
    }

    public void enterPassword(String password) {
        InteractionUtils.type(PASSWORD_FIELD, password);
    }

    public void clickContinue() {
        InteractionUtils.click(CONTINUE_BTN);
    }

    public void clickSignIn() {
        InteractionUtils.click(SIGN_IN_BTN);
    }

}