package com.schoolmanagement.administration.evaluations.controller;

import com.schoolmanagement.administration.evaluations.dto.EvaluationRequest;
import com.schoolmanagement.administration.evaluations.dto.EvaluationResponse;
import com.schoolmanagement.administration.evaluations.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping("/evaluations")
    public ResponseEntity<List<EvaluationResponse>> getAllEvaluations() {
        return ResponseEntity.ok(evaluationService.findAll());
    }

    @GetMapping("/evaluations/{id}")
    public ResponseEntity<EvaluationResponse> getEvaluationById(@PathVariable Long id) {
        return ResponseEntity.ok(evaluationService.findById(id));
    }

    @GetMapping("/classes/{id}/evaluations")
    public ResponseEntity<List<EvaluationResponse>> getEvaluationsByClasse(@PathVariable Long id) {
        return ResponseEntity.ok(evaluationService.findByClasse(id));
    }

    @PostMapping("/evaluations")
    public ResponseEntity<EvaluationResponse> createEvaluation(@Valid @RequestBody EvaluationRequest request) {
        return new ResponseEntity<>(evaluationService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/evaluations/{id}")
    public ResponseEntity<EvaluationResponse> updateEvaluation(@PathVariable Long id, @Valid @RequestBody EvaluationRequest request) {
        return ResponseEntity.ok(evaluationService.update(id, request));
    }

    @DeleteMapping("/evaluations/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        evaluationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}