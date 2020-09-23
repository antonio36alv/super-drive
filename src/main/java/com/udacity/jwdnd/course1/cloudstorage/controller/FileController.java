package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.SizeLimitExceededException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final StorageService storageService;
    private final UserService userService;

    @Autowired
    public FileController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    // Used spring boot docs for refrence
    // https://spring.io/guides/gs/uploading-files/
    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> accessFile(@PathVariable Integer fileId) {
        // Load file from database
        File file = storageService.viewFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header("attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @RequestMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, RedirectAttributes redirectAttributes) {
        if(storageService.deleteFile(fileId) != 0){
            redirectAttributes.addFlashAttribute("successMessage", "success");
        } else{
            redirectAttributes.addFlashAttribute("errorMessage", "error");
        }
        return "redirect:/home";
    }

    @PostMapping
    public String saveFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model,
                           RedirectAttributes redirectAttributes, Authentication authentication) {
        try {
            String message = storageService.store(fileUpload, userService.getUserId(authentication.getName()));
        if(message.contains("You successfully uploaded ")) {
            redirectAttributes.addFlashAttribute("successMessage", "success");
            return "redirect:/result";
        } else {
            redirectAttributes.addFlashAttribute("alertMessage", message);
            return "redirect:/home";
        }
        } catch(MultipartException e) {
            System.out.println("no bueno");
            return null;
        }
    }

}
