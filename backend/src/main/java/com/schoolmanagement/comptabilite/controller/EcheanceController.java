package com.schoolmanagement.comptabilite.controller;

import com.schoolmanagement.comptabilite.dto.request.EcheanceRequest;
import com.schoolmanagement.comptabilite.dto.response.EcheanceResponse;
import com.schoolmanagement.comptabilite.service.EcheanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/echeances")
public class EcheanceController {

    private final EcheanceService echeanceService;

    public EcheanceController(EcheanceService echeanceService) {
        this.echeanceService = echeanceService;
    }

    @GetMapping
    public ResponseEntity<List<EcheanceResponse>> obtenirToutesLesEcheances() {

        return ResponseEntity.ok(
                echeanceService.obtenirToutesLesEcheances()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EcheanceResponse> obtenirEcheance(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                echeanceService.obtenirEcheance(id)
        );
    }

    @PostMapping
    public ResponseEntity<EcheanceResponse> creerEcheance(
            @Valid @RequestBody EcheanceRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(echeanceService.creerEcheance(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EcheanceResponse> modifierEcheance(
            @PathVariable Long id,
            @Valid @RequestBody EcheanceRequest request
    ) {

        return ResponseEntity.ok(
                echeanceService.modifierEcheance(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEcheance(
            @PathVariable Long id
    ) {

        echeanceService.supprimerEcheance(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/retard")
    public ResponseEntity<List<EcheanceResponse>> obtenirEcheancesEnRetard() {

        return ResponseEntity.ok(
                echeanceService.obtenirEcheancesEnRetard()
        );
    }
}