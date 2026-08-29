package com.schoolmanagement.administration.evaluations.controller;

import com.schoolmanagement.administration.evaluations.dto.NoteRequest;
import com.schoolmanagement.administration.evaluations.dto.NoteResponse;
import com.schoolmanagement.administration.evaluations.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes")
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        return ResponseEntity.ok(noteService.findAll());
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findById(id));
    }

    @GetMapping("/eleves/{id}/notes")
    public ResponseEntity<List<NoteResponse>> getNotesByEleve(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findByEleve(id));
    }

    @GetMapping("/evaluations/{id}/notes")
    public ResponseEntity<List<NoteResponse>> getNotesByEvaluation(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.findByEvaluation(id));
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        return new ResponseEntity<>(noteService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, @Valid @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.update(id, request));
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}