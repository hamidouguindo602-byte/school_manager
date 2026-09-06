package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.dto.request.LogRequest;
import com.schoolmanagement.authentication.dto.response.LogResponse;
import com.schoolmanagement.authentication.entity.Log;
import com.schoolmanagement.authentication.service.LogService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class LogController {

  private final LogService logService;

  // 🔹 GET all logs
  @GetMapping
  public List<LogResponse> getAllLogs() {
    return logService.getAllLogs().stream()
        .map(
            log ->
                LogResponse.builder()
                    .id(log.getId())
                    .action(log.getAction())
                    .description(log.getDescription())
                    .ressource(log.getRessource())
                    .resultat(log.getResultat())
                    .dateAction(log.getDateAction())
                    .utilisateurId(log.getUtilisateur() == null ? null : log.getUtilisateur().getId())
                    .build())
        .collect(Collectors.toList());
  }

  // 🔹 GET log by ID
  @GetMapping("/{id}")
  public ResponseEntity<LogResponse> getLogById(@PathVariable Long id) {
    Log log = logService.getLogById(id);
    if (log == null) return ResponseEntity.notFound().build();

    LogResponse response =
        LogResponse.builder()
            .id(log.getId())
            .action(log.getAction())
            .description(log.getDescription())
            .ressource(log.getRessource())
            .resultat(log.getResultat())
            .dateAction(log.getDateAction())
            .utilisateurId(log.getUtilisateur() == null ? null : log.getUtilisateur().getId())
            .build();

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<LogResponse> createLog(@Valid @RequestBody LogRequest request) {
    Log log = logService.createLog(request);
    LogResponse response =
        LogResponse.builder()
            .id(log.getId())
            .action(log.getAction())
            .description(log.getDescription())
            .ressource(log.getRessource())
            .resultat(log.getResultat())
            .dateAction(log.getDateAction())
            .utilisateurId(log.getUtilisateur() == null ? null : log.getUtilisateur().getId())
            .build();
    return ResponseEntity.ok(response);
  }

  // 🔹 DELETE log
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteLog(@PathVariable Long id) {
    boolean deleted = logService.deleteLog(id);
    return deleted ? ResponseEntity.ok("Log supprimé") : ResponseEntity.notFound().build();
  }
}
