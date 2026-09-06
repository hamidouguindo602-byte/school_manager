package com.schoolmanagement.administration.absences.controller;

import com.schoolmanagement.administration.absences.dto.request.AbsenceRequest;
import com.schoolmanagement.administration.absences.dto.request.BatchJustificationRequest;
import com.schoolmanagement.administration.absences.dto.request.JustificationRequest;
import com.schoolmanagement.administration.absences.dto.response.AbsenceResponse;
import com.schoolmanagement.administration.absences.dto.response.AbsenceStatsResponse;
import com.schoolmanagement.administration.absences.service.AbsenceService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('ABSENCE_GERER')")
public class AbsenceController {
  private final AbsenceService service;

  @GetMapping("/eleve/{id}")
  public ResponseEntity<List<AbsenceResponse>> getByEleve(@PathVariable Long id) {
    return ResponseEntity.ok(service.obtenirAbsencesEleve(id));
  }

  @GetMapping("/classe/{classeId}")
  public ResponseEntity<List<AbsenceResponse>> getByClasse(@PathVariable Long classeId) {
    return ResponseEntity.ok(service.obtenirAbsencesParClasse(classeId));
  }

  @GetMapping("/classe/{classeId}/periode")
  public ResponseEntity<List<AbsenceResponse>> getByClasseAndPeriode(
      @PathVariable Long classeId, @RequestParam LocalDate debut, @RequestParam LocalDate fin) {
    return ResponseEntity.ok(service.obtenirAbsencesParClasseEtPeriode(classeId, debut, fin));
  }

  @GetMapping("/enseignant/{enseignantId}")
  public ResponseEntity<List<AbsenceResponse>> getByEnseignant(@PathVariable Long enseignantId) {
    return ResponseEntity.ok(service.obtenirAbsencesParEnseignant(enseignantId));
  }

  @GetMapping("/stats/classe/{classeId}")
  public ResponseEntity<AbsenceStatsResponse> getStatsByClasse(@PathVariable Long classeId) {
    return ResponseEntity.ok(service.getStatsParClasse(classeId));
  }

  @PostMapping
  public ResponseEntity<AbsenceResponse> declarer(@Valid @RequestBody AbsenceRequest req) {
    return ResponseEntity.ok(service.declarerAbsence(req));
  }

  // 1. Endpoint pour le Motif (Reçoit du JSON pur)
  @PutMapping(value = "/{id}/justifier")
  public ResponseEntity<AbsenceResponse> justifierMotif(
      @PathVariable Long id, @Valid @RequestBody JustificationRequest req) {
    return ResponseEntity.ok(service.justifierAbsence(id, req, null));
  }

  // 2. Endpoint séparé pour le Document (Reçoit du Multipart / Fichier)
  @PutMapping(value = "/{id}/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<AbsenceResponse> uploaderDocument(
      @PathVariable Long id, @RequestParam("document") MultipartFile document) {
    return ResponseEntity.ok(service.uploaderDocumentAbsence(id, document));
  }

  @PutMapping("/batch-justifier")
  public ResponseEntity<List<AbsenceResponse>> justifierEnLot(
      @Valid @RequestBody BatchJustificationRequest req) {
    return ResponseEntity.ok(service.justifierEnLot(req));
  }
}
