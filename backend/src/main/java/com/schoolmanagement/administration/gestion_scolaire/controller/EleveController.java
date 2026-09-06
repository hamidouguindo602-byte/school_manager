package com.schoolmanagement.administration.gestion_scolaire.controller;

import com.schoolmanagement.administration.absences.dto.response.AbsenceResponse;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EleveCreationRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.request.EleveUpdateRequest;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.EleveResponse;
import com.schoolmanagement.administration.gestion_scolaire.dto.response.InscriptionResponse;
import com.schoolmanagement.administration.gestion_scolaire.service.EleveService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eleves")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('ELEVE_GERER')")
public class EleveController {

  private final EleveService eleveService;

  @PostMapping
  public ResponseEntity<EleveResponse> creerEleve(
      @Valid @RequestBody EleveCreationRequest request) {
    EleveResponse nouveauEleve = eleveService.creerEleve(request);
    return ResponseEntity.status(201).body(nouveauEleve);
  }

  /** Récupérer un élève par son ID */
  @GetMapping("/{id}")
  public ResponseEntity<EleveResponse> obtenirEleveParId(@PathVariable Long id) {
    EleveResponse eleve = eleveService.obtenirEleveParId(id);
    return ResponseEntity.ok(eleve);
  }

  /** Récupérer la liste de tous les élèves */
  @GetMapping
  public ResponseEntity<List<EleveResponse>> listerTousLesEleves() {
    List<EleveResponse> eleves = eleveService.listerTousLesEleves();
    return ResponseEntity.ok(eleves);
  }

  /** Mettre à jour un élève existant */
  @PutMapping("/{id}")
  public ResponseEntity<EleveResponse> mettreAJourEleve(
      @PathVariable Long id, @Valid @RequestBody EleveUpdateRequest request) {
    EleveResponse eleveMisAJour = eleveService.mettreAJourEleve(id, request);
    return ResponseEntity.ok(eleveMisAJour);
  }

  /** Supprimer un élève */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimerEleve(@PathVariable Long id) {
    eleveService.supprimerEleve(id);
    return ResponseEntity.noContent().build();
  }

  /** Récupérer les inscriptions d'un élève */
  @GetMapping("/{id}/inscriptions")
  public ResponseEntity<List<InscriptionResponse>> obtenirInscriptionsParEleve(
      @PathVariable Long id) {
    return ResponseEntity.ok(eleveService.obtenirInscriptionsParEleve(id));
  }

  /** Récupérer les absences d'un élève */
  @GetMapping("/{id}/absences")
  public ResponseEntity<List<AbsenceResponse>> obtenirAbsencesParEleve(@PathVariable Long id) {
    return ResponseEntity.ok(eleveService.obtenirAbsencesParEleve(id));
  }
}
