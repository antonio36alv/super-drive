package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    @GetMapping
    public List<Credential> getCredentials(Authentication authentication) {
        return null;
    }

    @PostMapping
    public Integer getCredentails(Authentication authentication) {
        return null;
    }
}
