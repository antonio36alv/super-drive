package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.service.AuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final StorageService storageService;
    private final AuthenticationFacade authenticationFacade;


    @Autowired
    public HomeController(StorageService storageService, AuthenticationFacade authenticationFacade) {
        this.storageService = storageService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(path ->
                MvcUriComponentsBuilder.fromMethodName(HomeController.class, "serveFile",
                        path.getFileName().toString()).build().toUri().toString()).
                                                                        collect(Collectors.toList()));
        return "uploadForm";
    }

//    @GetMapping("/files")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @PostMapping("/fileUpload")
    public String saveFile(@RequestParam("fileUpload") MultipartFile fileUpload,
                           RedirectAttributes redirectAttributes, Authentication authentication) {

//    Authentication authentication = authenticationFacade.getAuthentication();
    String message = storageService.store(fileUpload, authentication.getName());
    redirectAttributes.addFlashAttribute("message",
            String.format("%s", message));

    return "redirect:/home";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}

