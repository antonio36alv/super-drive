package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;
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

    private StorageService storageService;
    @Autowired
    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @ModelAttribute("files")
    public List<File> listUploadedFiles(Model model, Authentication authentication) throws IOException {

//        model.addAttribute("files", storageService.loadAll().map(path ->
//                MvcUriComponentsBuilder.fromMethodName(HomeController.class, "serveFile",
//                        path.getFileName().toString()).build().toUri().toString()).
//                                                                        collect(Collectors.toList()));
//        return model.addAttribute("files", storageService.getUserFiles(authentication.getName()));
        return storageService.getUserFiles(authentication.getName());
//        for(String fileName : storageService.getUserFiles(authentication.getName()))
//        model.addAttribute("files", storageService.getUserFiles(authentication.getName()).stream().map(file -> file));
    }
}

