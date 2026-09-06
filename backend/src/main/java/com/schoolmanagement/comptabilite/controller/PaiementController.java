package com.schoolmanagement.comptabilite.controller;

import com.schoolmanagement.comptabilite.dto.request.PaiementRequest;
import com.schoolmanagement.comptabilite.dto.response.PaiementResponse;
import com.schoolmanagement.comptabilite.dto.response.RecuResponse;
import com.schoolmanagement.comptabilite.service.PaiementService;
import com.schoolmanagement.comptabilite.service.RecuService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
@PreAuthorize("hasRole('ADMIN') or hasAuthority('PAIEMENT_GERER')")
public class PaiementController {

  private final PaiementService paiementService;
  private final RecuService recuService;

  public PaiementController(PaiementService paiementService, RecuService recuService) {
    this.paiementService = paiementService;
    this.recuService = recuService;
  }

  @GetMapping("/{id}/recu")
  public ResponseEntity<RecuResponse> obtenirRecu(@PathVariable Long id) {
    return ResponseEntity.ok(recuService.obtenirParPaiement(id));
  }

  @GetMapping
  public ResponseEntity<List<PaiementResponse>> obtenirTous() {

    return ResponseEntity.ok(paiementService.obtenirTous());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaiementResponse> obtenirParId(@PathVariable Long id) {

    return ResponseEntity.ok(paiementService.obtenirParId(id));
  }

  @PostMapping
  public ResponseEntity<PaiementResponse> creer(@Valid @RequestBody PaiementRequest request) {

    return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.creer(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PaiementResponse> modifier(
      @PathVariable Long id, @Valid @RequestBody PaiementRequest request) {

    return ResponseEntity.ok(paiementService.modifier(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> supprimer(@PathVariable Long id) {

    paiementService.supprimer(id);

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/annuler")
  public ResponseEntity<Void> annuler(@PathVariable Long id) {

    paiementService.annuler(id);

    return ResponseEntity.noContent().build();
  }
}
