package com.denarced.repository;

import java.util.List;

public interface NoteRepository {
    void addNote(Note note);
    List<Note> fetchNotes();

    Note fetchNote(String uuid);
}
