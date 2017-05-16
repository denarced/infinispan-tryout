package com.denarced.service;

import com.denarced.repository.Note;

import java.util.List;

public interface NoteService {
    void addNote(Note note);
    List<Note> fetchNotes();

    Note fetchNote(String uuid);
}
