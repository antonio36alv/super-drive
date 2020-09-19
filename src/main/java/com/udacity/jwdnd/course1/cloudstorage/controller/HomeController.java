package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final StorageService storageService;
    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;

    @Autowired
    public HomeController(StorageService storageService, NoteService noteService,
                          UserService userService, CredentialService credentialService) {
        this.storageService = storageService;
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @ModelAttribute("files")
    public List<File> listUploadedFiles(Authentication authentication) throws IOException {
        return storageService.getUserFiles(authentication.getName());
    }

    @ModelAttribute("notes")
    public List<Note> listNotes(Authentication authentication) throws Exception {
        return noteService.getNotes(userService.getUserId(authentication.getName()));
    }

    @ModelAttribute("credentials")
    public List<Credential> listCredentails(Authentication authentication) {
        return credentialService.getCredentials(userService.getUserId(authentication.getName()));
    }

    @ModelAttribute("encryptionService")
    public EncryptionService encryptionService() {
        return new EncryptionService();
    }

}

