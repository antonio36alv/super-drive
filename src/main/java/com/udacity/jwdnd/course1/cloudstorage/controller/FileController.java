package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;

    }

//    @GetMapping

    @PostMapping
    public String saveFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model,
                           RedirectAttributes redirectAttributes, Authentication authentication) {

        String message = storageService.store(fileUpload, authentication.getName());
        redirectAttributes.addFlashAttribute("message",
                String.format("%s", message));

//        model.addAttribute()

        return "redirect:/home";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
