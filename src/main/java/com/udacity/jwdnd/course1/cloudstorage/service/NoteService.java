package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteForm;

import java.util.List;

public interface NoteService {

    Integer insertNote(NoteForm noteForm, Integer userId);

    List<Note> getNotes(Integer userId);

    Integer deleteNote(Integer noteId);

    Integer updateNote(NoteForm noteForm);

}
