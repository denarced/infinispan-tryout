package com.denarced.repository;

import java.util.List;

public interface NoteRepository {
    void addNote(String value);
    List<String> fetchNotes();
}
