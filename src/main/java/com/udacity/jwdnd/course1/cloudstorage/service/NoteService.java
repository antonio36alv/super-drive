package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.NoteForm;

import java.util.List;

public interface NoteService {

    Integer insertNote(NoteForm noteForm, Integer userId);

    List<Note> getNotes(Integer userId);

    String deleteNote(Integer noteId);

}
