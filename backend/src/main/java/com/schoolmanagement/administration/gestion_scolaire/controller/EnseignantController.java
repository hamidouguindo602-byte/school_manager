package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EnseignantResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.EnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
public class EnseignantController {

    private final EnseignantService enseignantService;

    @PostMapping
    public ResponseEntity<EnseignantResponse> creerEnseignant(@Valid @RequestBody EnseignantCreationRequest request) {
        return new ResponseEntity<>(enseignantService.creerEnseignant(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantResponse> obtenirEnseignantParId(@PathVariable Long id) {
        return ResponseEntity.ok(enseignantService.obtenirEnseignantParId(id));
    }

    @GetMapping
    public ResponseEntity<List<EnseignantResponse>> listerTousLesEnseignants() {
        return ResponseEntity.ok(enseignantService.listerTousLesEnseignants());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnseignantResponse> mettreAJourEnseignant(@PathVariable Long id, @Valid @RequestBody EnseignantUpdateRequest request) {
        return ResponseEntity.ok(enseignantService.mettreAJourEnseignant(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
        enseignantService.supprimerEnseignant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/emplois-du-temps")
    public ResponseEntity<List<Object>> obtenirEmploisDuTempsParEnseignant(@PathVariable Long id) {
        return ResponseEntity.ok(enseignantService.obtenirEmploisDuTempsParEnseignant(id));
    }

    @GetMapping("/{id}/evaluations")
    public ResponseEntity<List<Object>> obtenirEvaluationsParEnseignant(@PathVariable Long id) {
        return ResponseEntity.ok(enseignantService.obtenirEvaluationsParEnseignant(id));
    }
}