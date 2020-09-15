package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

        if(noteService.insertNote(noteForm, userService.getUserId(authentication.getName())) != 0) {
            model.addAttribute("successMessage");
        } else {
            model.addAttribute("errorMessage", "Error has occurred.");
        }

        return "redirect:/result";
    }

    @RequestMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model) {

        if(noteService.deleteNote(noteId) != 0) {
            model.addAttribute("successMessage", "Note deleted.");
        } else {
            model.addAttribute("errorMessage", "Error has occurred.");
        }
        return "redirect:/result";
    }

    @PutMapping
    public Integer updateNote(NoteForm noteForm) {
        // TODO update returns the number of rows affected
        noteService.updateNote(noteForm);
        return null;
    }

}
