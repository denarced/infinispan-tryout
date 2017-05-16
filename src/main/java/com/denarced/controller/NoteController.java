package com.denarced.controller;

import com.denarced.repository.Note;
import com.denarced.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/api/note")
    public List<Note> allNotes() {
        return noteService.fetchNotes();
    }

    @PostMapping("/api/note")
    public void addNote(
        @RequestParam String title,
        @RequestParam String value) {

        noteService.addNote(new Note(null, title, value));
    }

    @GetMapping("/api/note/{uuid}")
    public Note aNote(@PathVariable String uuid) {
        return noteService.fetchNote(uuid);
    }
}
