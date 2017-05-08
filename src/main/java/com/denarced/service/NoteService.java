package com.denarced.service;

import java.util.List;

public interface NoteService {
    void addNote(String value);
    List<String> fetchNotes();
}
