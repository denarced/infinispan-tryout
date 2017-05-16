package com.denarced.service;

import com.denarced.repository.Note;
import com.denarced.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public void addNote(Note note) {
        noteRepository.addNote(note);
    }

    @Override
    public List<Note> fetchNotes() {
        return noteRepository.fetchNotes();
    }

    @Override
    public Note fetchNote(String uuid) {
        return noteRepository.fetchNote(uuid);
    }
}
