package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/{noteId}")
    public String updateNote(@PathVariable Integer noteId, NoteForm noteForm, RedirectAttributes redirectAttributes) {

        if(noteService.updateNote(noteForm) != 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Success");
        } else {
            redirectAttributes.addFlashAttribute("failedEditMessage", "Error");
        }
        return "redirect:/result";
    }

    @PostMapping
    public String insertNote(NoteForm noteForm, Authentication authentication, RedirectAttributes redirectAttributes) {

        if(noteService.insertNote(noteForm, userService.getUserId(authentication.getName())) != 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Success");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error");
        }
        return "redirect:/result";
    }

    @RequestMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, RedirectAttributes redirectAttributes) {

        if(noteService.deleteNote(noteId) != 0) {
            redirectAttributes.addFlashAttribute("successMessage", "deleted");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error");
        }
        return "redirect:/result";
    }

}
