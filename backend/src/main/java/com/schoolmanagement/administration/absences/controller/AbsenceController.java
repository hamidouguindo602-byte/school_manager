package com.schoolmanagement.administration.absences.controller;

import com.schoolmanagement.administration.absences.dto.request.AbsenceRequest;
import com.schoolmanagement.administration.absences.dto.request.BatchJustificationRequest;
import com.schoolmanagement.administration.absences.dto.request.JustificationRequest;
import com.schoolmanagement.administration.absences.dto.response.AbsenceResponse;
import com.schoolmanagement.administration.absences.dto.response.AbsenceStatsResponse;
import com.schoolmanagement.administration.absences.service.AbsenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
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
            @PathVariable Long classeId,
            @RequestParam LocalDate debut,
            @RequestParam LocalDate fin) {
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

    @PutMapping("/{id}/justifier")
    public ResponseEntity<AbsenceResponse> justifier(@PathVariable Long id, @Valid @RequestBody JustificationRequest req) {
        return ResponseEntity.ok(service.justifierAbsence(id, req));
    }

    @PutMapping("/batch-justifier")
    public ResponseEntity<List<AbsenceResponse>> justifierEnLot(@Valid @RequestBody BatchJustificationRequest req) {
        return ResponseEntity.ok(service.justifierEnLot(req));
    }
}