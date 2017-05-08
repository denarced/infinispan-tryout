package com.denarced.controller;

import com.denarced.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/api/note")
    public List<String> allNotes() {
        return noteService.fetchNotes();
    }

    @PostMapping("/api/note")
    public void addNote(@RequestParam String value) {
        noteService.addNote(value);
    }
}
