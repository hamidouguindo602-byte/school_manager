package com.schoolmanagement.administration.emploi_temps.controller;

import com.schoolmanagement.administration.emploi_temps.dto.request.AssignationRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.AssignationResponse;
import com.schoolmanagement.administration.emploi_temps.service.AssignationService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('EMPLOI_TEMPS_GERER')")
public class AssignationController {

  private final AssignationService assignationService;

  @PostMapping
  public ResponseEntity<AssignationResponse> creer(@Valid @RequestBody AssignationRequest request) {
    AssignationResponse response = assignationService.creerAssignation(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AssignationResponse> modifier(
      @PathVariable Long id, @Valid @RequestBody AssignationRequest request) {
    AssignationResponse response = assignationService.modifierAssignation(id, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<AssignationResponse>> listerToutes() {
    return ResponseEntity.ok(assignationService.listerToutes());
  }

  @GetMapping("/classe/{classeId}")
  public ResponseEntity<List<AssignationResponse>> listerParClasse(@PathVariable Long classeId) {
    return ResponseEntity.ok(assignationService.listerParClasse(classeId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimer(@PathVariable Long id) {
    assignationService.supprimerAssignation(id);
    return ResponseEntity.noContent().build();
  }
}
