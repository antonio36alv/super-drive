package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @PostMapping
    public String insertNote(NoteForm noteForm, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {

        String message;
        Integer jawn = noteService.insertNote(noteForm, userService.getUserId(authentication.getName()));
        redirectAttributes.addFlashAttribute("message",
                String.format("%s", jawn));
        redirectAttributes.addFlashAttribute("successMessage", "stuff happened");

        return "redirect:/result";
    }

    @RequestMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        noteService.deleteNote(noteId);
        return null;
    }

}
