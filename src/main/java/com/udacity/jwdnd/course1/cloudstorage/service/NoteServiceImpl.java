package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private final NoteMapper noteMapper;

    public NoteServiceImpl(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @Override
    public Integer insertNote(NoteForm noteForm, Integer userId) {
        return noteMapper.insertNote(new Note(null, noteForm.noteTitle, noteForm.noteDescription, userId));
    }

    @Override
    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    @Override
    public Integer deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

    @Override
    public Integer updateNote(NoteForm noteForm) {
        System.out.println(noteForm.getNoteId());
        System.out.println(noteForm.getNoteTitle());
        System.out.println(noteForm.getNoteDescription());
        return noteMapper.updateNote(noteForm.getNoteId(), noteForm.getNoteTitle(), noteForm.getNoteDescription());
    }

}
