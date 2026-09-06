package com.schoolmanagement.communication.controller;

import com.schoolmanagement.communication.dto.Response.AnnonceResponse;
import com.schoolmanagement.communication.dto.request.AnnonceRequest;
import com.schoolmanagement.communication.service.AnnonceService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/annonces")
@RequiredArgsConstructor
public class AnnonceController {

  private final AnnonceService annonceService;

  // =========================
  // LISTER LES ANNONCES
  // =========================

  @GetMapping
  public ResponseEntity<List<AnnonceResponse>> findAll() {

    List<AnnonceResponse> responses = annonceService.findAll();

    return ResponseEntity.ok(responses);
  }

  // =========================
  // CHERCHER UNE ANNONCE
  // =========================

  @GetMapping("/{id}")
  public ResponseEntity<AnnonceResponse> findById(@PathVariable Long id) {

    AnnonceResponse response = annonceService.findById(id);

    return ResponseEntity.ok(response);
  }

  // =========================
  // CREER UNE ANNONCE
  // =========================

  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('ANNONCE_GERER')")
  public ResponseEntity<AnnonceResponse> create(@Valid @ModelAttribute AnnonceRequest request) {

    AnnonceResponse response = annonceService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // =========================
  // MODIFIER UNE ANNONCE
  // =========================

  @PutMapping(
      value = "/{id}",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('ANNONCE_GERER')")
  public ResponseEntity<AnnonceResponse> update(
      @PathVariable Long id, @Valid @ModelAttribute AnnonceRequest request) {

    AnnonceResponse response = annonceService.update(id, request);

    return ResponseEntity.ok(response);
  }

  // =========================
  // SUPPRIMER UNE ANNONCE
  // =========================

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or hasAuthority('ANNONCE_GERER')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {

    annonceService.delete(id);

    return ResponseEntity.noContent().build();
  }

  // =========================
  // AFFICHER LE PDF
  // =========================

  @GetMapping(value = "/{id}/fichier", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> getFichier(@PathVariable Long id) {

    try {
      byte[] fichier = annonceService.getFichier(id);

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
          .contentType(MediaType.APPLICATION_PDF)
          .body(fichier);
    } catch (EntityNotFoundException exception) {
      return ResponseEntity.notFound().build();
    }
  }
}
