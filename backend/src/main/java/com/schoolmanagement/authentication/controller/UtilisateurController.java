package com.schoolmanagement.authentication.controller;


import com.schoolmanagement.authentication.dto.request.UtilisateurRequest;
import com.schoolmanagement.authentication.dto.response.UtilisateurResponse;
import com.schoolmanagement.authentication.service.UtilisateurService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    public List<UtilisateurResponse> findAll() {
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public UtilisateurResponse findById(@PathVariable Long id) {
        return utilisateurService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UtilisateurResponse> create(
            @Valid @RequestBody UtilisateurRequest request) {

        UtilisateurResponse created = utilisateurService.create(request);

        return ResponseEntity
                .created(URI.create("/api/utilisateurs/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public UtilisateurResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UtilisateurRequest request) {

        return utilisateurService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        utilisateurService.delete(id);

        return ResponseEntity.noContent().build();
    }
}