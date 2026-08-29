package com.schoolmanagement.administration.emploi_temps.controller;

import com.schoolmanagement.administration.emploi_temps.dto.request.EmploiDuTempsRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.EmploiDuTempsResponse;
import com.schoolmanagement.administration.emploi_temps.service.EmploiDuTempsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emploi-du-temps")
@RequiredArgsConstructor
public class EmploiDuTempsController {
    private final EmploiDuTempsService service;

    @GetMapping("/classe/{id}")
    public ResponseEntity<List<EmploiDuTempsResponse>> getByClasse(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenirParClasse(id));
    }

    @GetMapping("/grille/classe/{id}")
    public ResponseEntity<Map<String, List<EmploiDuTempsResponse>>> getGrilleParClasse(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenirGrilleParClasse(id));
    }

    @PostMapping
    public ResponseEntity<EmploiDuTempsResponse> create(@Valid @RequestBody EmploiDuTempsRequest req) {
        return ResponseEntity.ok(service.creerCreneau(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmploiDuTempsResponse> update(@PathVariable Long id, @Valid @RequestBody EmploiDuTempsRequest req) {
        return ResponseEntity.ok(service.modifierCreneau(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.supprimerCreneau(id);
        return ResponseEntity.noContent().build();
    }
}