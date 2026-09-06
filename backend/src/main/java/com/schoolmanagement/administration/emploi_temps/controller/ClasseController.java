package com.schoolmanagement.administration.emploi_temps.controller;

import com.schoolmanagement.administration.emploi_temps.dto.request.ClasseRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.ClasseResponse;
import com.schoolmanagement.administration.emploi_temps.service.ClasseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasAuthority('CLASSE_GERER')")
public class ClasseController {
  private final ClasseService classeService;

  @GetMapping
  public ResponseEntity<List<ClasseResponse>> listerClasses() {
    return ResponseEntity.ok(classeService.obtenirToutesLesClasses());
  }

  @PostMapping
  public ResponseEntity<ClasseResponse> creerClasse(@Valid @RequestBody ClasseRequest request) {
    return ResponseEntity.ok(classeService.creerClasse(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClasseResponse> modifierClasse(
      @PathVariable Long id, @Valid @RequestBody ClasseRequest request) {
    return ResponseEntity.ok(classeService.modifierClasse(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimerClasse(@PathVariable Long id) {
    classeService.supprimerClasse(id);
    return ResponseEntity.noContent().build();
  }
}
