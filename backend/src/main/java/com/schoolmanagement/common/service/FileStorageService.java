package com.schoolmanagement.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;
    private final long maxFileSize;
    private final String[] allowedTypes;

    public FileStorageService(
            @Value("${app.upload.dir:./uploads/absences}") String uploadDir,
            @Value("${app.upload.max-size:10485760}") long maxFileSize,
            @Value("${app.upload.allowed-types:application/pdf,image/jpeg,image/png,image/jpg}") String allowedTypes) {
        
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.maxFileSize = maxFileSize;
        this.allowedTypes = allowedTypes.split(",");
        
        try {
            Files.createDirectories(this.fileStorageLocation);
            log.info("Dossier upload créé: {}", this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Impossible de créer le dossier upload", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        validateFile(file);
        
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }
        String storedFileName = UUID.randomUUID().toString() + extension;

        try {
            if (storedFileName.contains("..")) {
                throw new RuntimeException("Nom fichier invalide");
            }

            Path targetLocation = this.fileStorageLocation.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("Fichier stocké: {}", targetLocation);
            return targetLocation.toString(); // Chemin complet

        } catch (IOException ex) {
            throw new RuntimeException("Échec enregistrement: " + storedFileName, ex);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("Fichier trop volumineux. Max: " + (maxFileSize / 1024 / 1024) + "MB");
        }
        
        String contentType = file.getContentType();
        String originalFileName = file.getOriginalFilename();
        boolean allowed = false;

        // Vérification du Content-Type standard
        for (String type : allowedTypes) {
            if (type.trim().equalsIgnoreCase(contentType)) {
                allowed = true;
                break;
            }
        }

        // Tolérance si l'outil client (Postman) envoie application/octet-stream mais que l'extension est correcte
        if (!allowed && "application/octet-stream".equalsIgnoreCase(contentType) && originalFileName != null) {
            String lowerName = originalFileName.toLowerCase();
            if (lowerName.endsWith(".pdf") || lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") || lowerName.endsWith(".png")) {
                allowed = true;
            }
        }

        if (!allowed) {
            throw new IllegalArgumentException("Type non autorisé. Autorisés: " + String.join(", ", allowedTypes) + " (Reçu: " + contentType + ")");
        }
    }
    
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
            log.info("Fichier supprimé: {}", filePath);
        } catch (IOException ex) {
            log.error("Erreur suppression fichier: {}", filePath, ex);
        }
    }
}