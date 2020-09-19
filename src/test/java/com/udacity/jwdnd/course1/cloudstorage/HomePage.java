package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "new-note-btn")
    private WebElement newNoteBtn;

    @FindBy(id = "note-title-input")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "note-submit")
    protected WebElement noteSubmitBtn;

    @FindBy(className = "note-title-th")
    protected WebElement noteTitleTh;

    @FindBy(className = "note-description-td")
    protected WebElement noteDescriptionTd;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutBtn.click();
    }

    public void addNote(String noteTitle, String noteDescription) throws InterruptedException {
        this.noteTab.click();
        this.newNoteBtn.click();
        this.noteTitleInput.sendKeys(noteTitle);
        this.noteDescriptionInput.sendKeys(noteDescription);
        Thread.sleep(4000);
        this.noteSubmitBtn.click();
    }

    public Note getFirstNote() {
        return new Note(null, noteTitleTh.getText(), noteDescriptionTd.getText(), null);
    }

}
