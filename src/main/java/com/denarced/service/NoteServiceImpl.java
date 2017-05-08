package com.denarced.service;

import com.denarced.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public void addNote(String value) {
        noteRepository.addNote(value);
    }

    @Override
    public List<String> fetchNotes() {
        return noteRepository.fetchNotes();
    }
}
