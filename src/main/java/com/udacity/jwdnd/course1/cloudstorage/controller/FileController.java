package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/files")
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;

    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
        // Load file from database
        File file = storageService.viewFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header("attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @RequestMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId) {
        storageService.deleteFile(fileId);
        return "redirect:/home";
    }

    @PostMapping
    public String saveFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model,
                           RedirectAttributes redirectAttributes, Authentication authentication) {
    // TODO model can probably be removed from parameters
        String message = storageService.store(fileUpload, authentication.getName());
        redirectAttributes.addFlashAttribute("message",
                String.format("%s", message));

        model.addAttribute("successMessage", "yay");

        return "redirect:/result";
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }

}
