package com.denarced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class NoteController {
    private List<String> notes = new LinkedList<>();

    @GetMapping("/api/note")
    public List<String> allNotes() {
        return notes;
    }

    @PostMapping("/api/note")
    public void addNote(@RequestParam String value) {
        notes.add(value);
    }
}
