package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    @Autowired
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public List<Credential> getCredentials(Authentication authentication) {
        return credentialService.getCredentials(userService.getUserId(authentication.getName()));
    }

    @PostMapping
    public String insertCredential(CredentialForm credentialForm, Authentication authentication, RedirectAttributes redirectAttributes) throws Exception {
        if(credentialService.insertCredential(credentialForm, userService.getUserId(authentication.getName())) !=  0) {
            redirectAttributes.addFlashAttribute("successMessage", "success");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "error");
        }
        return "redirect:/result";
    }

    @PostMapping("/{credentialId}")
    public String updateCredential(@PathVariable Integer credentialId, CredentialForm credentialForm, RedirectAttributes redirectAttributes) {
        if(credentialService.updateCredential(credentialForm) !=  0) {
            redirectAttributes.addFlashAttribute("successMessage", "success");
        } else {
            redirectAttributes.addFlashAttribute("failedEditMessage", "failedEdit");
        }
        return "redirect:/result";
    }

    @RequestMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, RedirectAttributes redirectAttributes) {
        if(credentialService.deleteCredential(credentialId) != 0) {
            redirectAttributes.addFlashAttribute("successMessage", "success");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "error");
        }
        return "redirect:/result";
    }

}
