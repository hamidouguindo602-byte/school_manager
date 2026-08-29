package com.schoolmanagement.administration.emploi_temps.controller;

import com.schoolmanagement.administration.emploi_temps.dto.request.MatiereRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.MatiereResponse;
import com.schoolmanagement.administration.emploi_temps.service.MatiereService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matieres")
@RequiredArgsConstructor
public class MatiereController {
    private final MatiereService matiereService;

    @GetMapping
    public ResponseEntity<List<MatiereResponse>> listerMatieres() {
        return ResponseEntity.ok(matiereService.obtenirToutesLesMatieres());
    }

    @PostMapping
    public ResponseEntity<MatiereResponse> creerMatiere(@RequestBody MatiereRequest request) {
        return ResponseEntity.ok(matiereService.creerMatiere(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatiereResponse> modifierMatiere(@PathVariable Long id, @RequestBody MatiereRequest request) {
        return ResponseEntity.ok(matiereService.modifierMatiere(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMatiere(@PathVariable Long id) {
        matiereService.supprimerMatiere(id);
        return ResponseEntity.noContent().build();
    }
}