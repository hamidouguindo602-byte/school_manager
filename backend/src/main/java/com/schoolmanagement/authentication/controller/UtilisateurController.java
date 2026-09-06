package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.dto.request.UtilisateurRequest;
import com.schoolmanagement.authentication.dto.response.UtilisateurResponse;
import com.schoolmanagement.authentication.service.UtilisateurService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UtilisateurController {

  private final UtilisateurService utilisateurService;

  @GetMapping
  public List<UtilisateurResponse> findAll() {
    return utilisateurService.findAll();
  }

  @GetMapping("/{id}")
  public UtilisateurResponse findById(@PathVariable Long id) {
    return utilisateurService.findById(id);
  }

  @PostMapping
  public ResponseEntity<UtilisateurResponse> create(
      @Valid @RequestBody UtilisateurRequest request) {

    UtilisateurResponse created = utilisateurService.create(request);

    return ResponseEntity.created(URI.create("/api/utilisateurs/" + created.id())).body(created);
  }

  @PutMapping("/{id}")
  public UtilisateurResponse update(
      @PathVariable Long id, @Valid @RequestBody UtilisateurRequest request) {

    return utilisateurService.update(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {

    utilisateurService.delete(id);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/activer")
  public ResponseEntity<Void> activer(@PathVariable Long id) {
    utilisateurService.activer(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/desactiver")
  public ResponseEntity<Void> desactiver(@PathVariable Long id) {
    utilisateurService.desactiver(id);
    return ResponseEntity.noContent().build();
  }
}
