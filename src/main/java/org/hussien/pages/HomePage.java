package org.hussien.pages;


import org.hussien.pages.base.BasePage;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By loginAccountList = By.id("nav-link-accountList");
    public static String currentCategory ;


    public void clickSignIn() {
        hoverOverElement(loginAccountList);
        clickOnButtonWithText("Sign in");
    }

    public void selectCategory(String category) {
        click(By.xpath("//li[@class='nav-li']//a[normalize-space(text())='" + category + "']"));
        currentCategory = category;
    }

    public boolean isCategorySelected(){
        return isDisplayed(By.xpath("//li[@class='nav-li']//a[normalize-space(text())='" + currentCategory + "']"));
    }


}