package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.LogRequest;
import com.schoolmanagement.authentication.entity.Log;
import com.schoolmanagement.authentication.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    // 🔹 Enregistrer une action
    public Log enregistrerAction(String action) {
        Log log = Log.builder()
                .action(action)
                .dateAction(LocalDateTime.now())
                .build();
        return logRepository.save(log);
    }

    // 🔹 Créer un log via DTO
    public Log createLog(LogRequest request) {
        Log log = Log.builder()
                .action(request.getAction())
                .dateAction(request.getDateAction())
                .build();
        return logRepository.save(log);
    }

    // 🔹 Récupérer tous les logs
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    // 🔹 Récupérer un log par ID
    public Log getLogById(Long id) {
        return logRepository.findById(id).orElse(null);
    }

    // 🔹 Supprimer un log
    public boolean deleteLog(Long id) {
        return logRepository.findById(id).map(log -> {
            logRepository.delete(log);
            return true;
        }).orElse(false);
    }
}
