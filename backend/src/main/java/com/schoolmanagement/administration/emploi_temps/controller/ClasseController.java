package com.schoolmanagement.administration.emploi_temps.controller;

import com.schoolmanagement.administration.emploi_temps.dto.request.ClasseRequest;
import com.schoolmanagement.administration.emploi_temps.dto.response.ClasseResponse;
import com.schoolmanagement.administration.emploi_temps.service.ClasseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClasseController {
    private final ClasseService classeService;

    @GetMapping
    public ResponseEntity<List<ClasseResponse>> listerClasses() {
        return ResponseEntity.ok(classeService.obtenirToutesLesClasses());
    }

    @PostMapping
    public ResponseEntity<ClasseResponse> creerClasse(@RequestBody ClasseRequest request) {
        return ResponseEntity.ok(classeService.creerClasse(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClasseResponse> modifierClasse(@PathVariable Long id, @RequestBody ClasseRequest request) {
        return ResponseEntity.ok(classeService.modifierClasse(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerClasse(@PathVariable Long id) {
        classeService.supprimerClasse(id);
        return ResponseEntity.noContent().build();
    }
}