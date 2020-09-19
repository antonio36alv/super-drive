package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(className = "alert")
    private WebElement signupAlert;

    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement userNameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "submit-btn")
    private WebElement submitButton;

    public SignupPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstName, String lastName, String username, String password) {

        this.firstNameInput.sendKeys(firstName);
        this.lastNameInput.sendKeys(lastName);
        this.userNameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);

        this.submitButton.click();
    }
}
