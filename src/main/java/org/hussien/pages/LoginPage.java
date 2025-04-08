package org.hussien.pages;

import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;


public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = By.xpath("//input[@type='email']");
    private final By passwordField = By.xpath("//input[@type='password']");
    private final By continueBtn = By.id("continue");
    private final By signInBtn = By.id("auth-signin-button");



    //Page Actions
    public void enterUsername(String username) {
        type(usernameField, username);
    }


    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickContinue() {
        click(continueBtn);
    }


    public void clickSignIn() {
        click(signInBtn);
    }
}