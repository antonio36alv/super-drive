package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(linkText = "here")
    private WebElement homeAnchor;

    public ResultPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void clickHomeAnchor() throws InterruptedException {
        Thread.sleep(3000);
        this.homeAnchor.click();
    }

}
