package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @PostMapping
    public Integer saveFile(MultipartFile fileUpload) {
        return null;
    }
}

