package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-btn")
    private WebElement logoutBtn;
    // for notes
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "new-note-btn")
    private WebElement newNoteBtn;

    @FindBy(className = "edit-note-btn")
    private WebElement editNoteBtn;

    @FindBy(className = "delete-note-btn")
    private WebElement deleteNoteBtn;

    @FindBy(id = "note-title-input")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "save-note-changes")
    protected WebElement noteSubmitBtn;

    @FindBy(className = "note-title-th")
    protected WebElement noteTitleTh;

    @FindBy(className = "note-description-td")
    protected WebElement noteDescriptionTd;
    // for credentials
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "new-credential-btn")
    private WebElement newCredentialBtn;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(id = "save-credential-changes")
    private WebElement credentialSubmitBtn;

    @FindBy(className = "edit-credential-btn")
    private WebElement editCredentailBtn;

    @FindBy(id = "close-credential-btn")
    private WebElement closeCredentialBtn;

    @FindBy(className = "delete-credential-btn")
    private WebElement deleteCredentialBtn;

    @FindBy(className = "credential-url-th")
    private WebElement credentialUrl;

    @FindBy(className = "credential-password-enc-td")
    private WebElement credentialPasswordEncryp;

    @FindBy(className = "credential-username-td")
    private WebElement credentialUsername;

    @FindBy(className = "password-view")
    private WebElement passwordView;

    @FindBy(className = "show-password-btn")
    private WebElement showPasswordBtn;

//    @FindBy(className = "")

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        this.logoutBtn.click();
    }

    public void addNote(boolean edit, String noteTitle, String noteDescription) {
        this.noteTab.click();
        if (!edit) {
            this.newNoteBtn.click();
        } else {
            this.editNoteBtn.click();
            this.noteTitleInput.clear();
            this.noteDescriptionInput.clear();
        }
        this.noteTitleInput.sendKeys(noteTitle);
        this.noteDescriptionInput.sendKeys(noteDescription);
        this.noteSubmitBtn.click();
    }

    public void deleteNote() {
        this.deleteNoteBtn.click();
    }

    public Note getFirstNote() {
        this.noteTab.click();
        return new Note(null, noteTitleTh.getText(), noteDescriptionTd.getText(), null);
    }

    public void addCredential(boolean edit, String url, String username, String password) {
        if (!edit) {
            this.credentialsTab.click();
            this.newCredentialBtn.click();
        } else {
            this.editCredentailBtn.click();
            this.credentialUrlInput.clear();
            this.credentialUsernameInput.clear();
            this.credentialPasswordInput.clear();
        }
        this.credentialUrlInput.sendKeys(url);
        this.credentialUsernameInput.sendKeys(username);
        this.credentialPasswordInput.sendKeys(password);
        this.credentialSubmitBtn.click();
    }

    public String getPasswordEnc() {
        return credentialPasswordEncryp.getAttribute("value");
    }

    public Credential getFirstCredential() {
        this.credentialsTab.click();
        return new Credential(null, credentialUrl.getText(), credentialUsername.getText(),
                                null, passwordView.getText(), null);
    }

    public void deleteCredential() {
        this.deleteCredentialBtn.click();
    }

    public String getPasswordDecrypted() {
        this.editCredentailBtn.click();
        String decryptedPassword = this.credentialPasswordInput.getAttribute("value");
        this.closeCredentialBtn.click();
        this.closeCredentialBtn.click();
        try {
            Thread.sleep(3000);
        } catch(InterruptedException e) {
            System.out.println("fuck");
        }
        return decryptedPassword;
    }

    public void clickShowPassword() {
        this.showPasswordBtn.click();
    }
}
