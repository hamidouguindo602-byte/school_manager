package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.emploi_temps.dto.response.ClasseResponse;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.FormationCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.FormationUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.FormationResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.FormationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('FORMATION_GERER')")
public class FormationController {

  private final FormationService formationService;

  @PostMapping
  public ResponseEntity<FormationResponse> creerFormation(
      @Valid @RequestBody FormationCreationRequest request) {
    return new ResponseEntity<>(formationService.creerFormation(request), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<FormationResponse> obtenirFormationParId(@PathVariable Long id) {
    return ResponseEntity.ok(formationService.obtenirFormationParId(id));
  }

  @GetMapping
  public ResponseEntity<List<FormationResponse>> listerToutesLesFormations() {
    return ResponseEntity.ok(formationService.listerToutesLesFormations());
  }

  @PutMapping("/{id}")
  public ResponseEntity<FormationResponse> mettreAJourFormation(
      @PathVariable Long id, @Valid @RequestBody FormationUpdateRequest request) {
    return ResponseEntity.ok(formationService.mettreAJourFormation(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimerFormation(@PathVariable Long id) {
    formationService.supprimerFormation(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/classes")
  public ResponseEntity<List<ClasseResponse>> obtenirClassesParFormation(@PathVariable Long id) {
    return ResponseEntity.ok(formationService.obtenirClassesParFormation(id));
  }
}
