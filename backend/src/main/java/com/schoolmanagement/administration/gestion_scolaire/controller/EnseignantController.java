package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.emploi_temps.dto.response.EmploiDuTempsResponse;
import com.schoolmanagement.administration.evaluations.dto.response.EvaluationResponse;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EnseignantUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EnseignantResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.EnseignantService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('ENSEIGNANT_GERER')")
public class EnseignantController {

  private final EnseignantService enseignantService;

  @PostMapping
  public ResponseEntity<EnseignantResponse> creerEnseignant(
      @Valid @RequestBody EnseignantCreationRequest request) {
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
  public ResponseEntity<EnseignantResponse> mettreAJourEnseignant(
      @PathVariable Long id, @Valid @RequestBody EnseignantUpdateRequest request) {
    return ResponseEntity.ok(enseignantService.mettreAJourEnseignant(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimerEnseignant(@PathVariable Long id) {
    enseignantService.supprimerEnseignant(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/emplois-du-temps")
  public ResponseEntity<List<EmploiDuTempsResponse>> obtenirEmploisDuTempsParEnseignant(
      @PathVariable Long id) {
    return ResponseEntity.ok(enseignantService.obtenirEmploisDuTempsParEnseignant(id));
  }

  @GetMapping("/{id}/evaluations")
  public ResponseEntity<List<EvaluationResponse>> obtenirEvaluationsParEnseignant(
      @PathVariable Long id) {
    return ResponseEntity.ok(enseignantService.obtenirEvaluationsParEnseignant(id));
  }
}
