package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.gestion_scolaire.dto.request.AnneeScolariteCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.AnneeScolariteUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.AnneeScolariteResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.AnneeScolariteService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/annees-scolaires")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('ANNEE_SCOLAIRE_GERER')")
public class AnneeScolariteController {

  private final AnneeScolariteService anneeScolariteService;

  @PostMapping
  public ResponseEntity<AnneeScolariteResponse> creerAnneeScolarite(
      @Valid @RequestBody AnneeScolariteCreationRequest request) {
    return new ResponseEntity<>(
        anneeScolariteService.creerAnneeScolarite(request), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AnneeScolariteResponse> obtenirAnneeScolariteParId(@PathVariable Long id) {
    return ResponseEntity.ok(anneeScolariteService.obtenirAnneeScolariteParId(id));
  }

  @GetMapping
  public ResponseEntity<List<AnneeScolariteResponse>> listerToutesLesAnneesScolaires() {
    return ResponseEntity.ok(anneeScolariteService.listerToutesLesAnneesScolaires());
  }

  @PutMapping("/{id}")
  public ResponseEntity<AnneeScolariteResponse> mettreAJourAnneeScolarite(
      @PathVariable Long id, @Valid @RequestBody AnneeScolariteUpdateRequest request) {
    return ResponseEntity.ok(anneeScolariteService.mettreAJourAnneeScolarite(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimerAnneeScolarite(@PathVariable Long id) {
    anneeScolariteService.supprimerAnneeScolarite(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/activer")
  public ResponseEntity<AnneeScolariteResponse> activerAnneeScolarite(@PathVariable Long id) {
    return ResponseEntity.ok(anneeScolariteService.activerAnneeScolarite(id));
  }
}
