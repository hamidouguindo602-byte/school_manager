package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.dto.request.LogRequest;
import com.schoolmanagement.authentication.dto.response.LogResponse;
import com.schoolmanagement.authentication.entity.Log;
import com.schoolmanagement.authentication.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
@PreAuthorize("hasRole('ADMIN')")
public class LogController {

    @Autowired
    private LogService logService;

    // 🔹 GET all logs
    @GetMapping
    public List<LogResponse> getAllLogs() {
        return logService.getAllLogs().stream()
                .map(log -> LogResponse.builder()
                        .id(log.getId())
                        .action(log.getAction())
                        .dateAction(log.getDateAction())
                        .build())
                .collect(Collectors.toList());
    }

    // 🔹 GET log by ID
    @GetMapping("/{id}")
    public ResponseEntity<LogResponse> getLogById(@PathVariable Long id) {
        Log log = logService.getLogById(id);
        if (log == null) return ResponseEntity.notFound().build();

        LogResponse response = LogResponse.builder()
                .id(log.getId())
                .action(log.getAction())
                .dateAction(log.getDateAction())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<LogResponse> createLog(@RequestBody LogRequest request) {
        Log log = logService.createLog(request);
        LogResponse response = LogResponse.builder()
                .id(log.getId())
                .action(log.getAction())
                .dateAction(log.getDateAction())
                .build();
        return ResponseEntity.ok(response);
    }

    // 🔹 DELETE log
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLog(@PathVariable Long id) {
        boolean deleted = logService.deleteLog(id);
        return deleted ? ResponseEntity.ok("Log supprimé")
                       : ResponseEntity.notFound().build();
    }
}
