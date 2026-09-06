package com.schoolmanagement.common.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileStorageService {

  private final Path fileStorageLocation;
  private final long maxFileSize;
  private final String[] allowedTypes;

  public FileStorageService(
      @Value("${app.upload.dir:./uploads/absences}") String uploadDir,
      @Value("${app.upload.max-size:10485760}") long maxFileSize,
      @Value("${app.upload.allowed-types:application/pdf,image/jpeg,image/png,image/jpg}")
          String allowedTypes) {

    this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    this.maxFileSize = maxFileSize;
    this.allowedTypes = allowedTypes.split(",");

    try {
      Files.createDirectories(this.fileStorageLocation);
      log.info("Dossier upload créé: {}", this.fileStorageLocation);
    } catch (IOException | SecurityException ex) {
      throw new IllegalStateException("Impossible de créer le dossier upload", ex);
    }
  }

  public String storeFile(MultipartFile file) {
    validateFile(file);

    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
    String extension = "";
    int dotIndex = originalFileName.lastIndexOf('.');
    if (dotIndex > 0) {
      extension = originalFileName.substring(dotIndex).toLowerCase(Locale.ROOT);
    }
    String storedFileName = UUID.randomUUID().toString() + extension;

    try {
      Path targetLocation = this.fileStorageLocation.resolve(storedFileName);
      if (!targetLocation.getParent().equals(this.fileStorageLocation)) {
        throw new IllegalArgumentException("Nom fichier invalide");
      }
      Files.copy(file.getInputStream(), targetLocation);

      log.info("Fichier stocké: {}", storedFileName);
      return storedFileName;

    } catch (IOException ex) {
      throw new IllegalStateException("Echec de l'enregistrement du fichier", ex);
    }
  }

  private void validateFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Un fichier non vide est obligatoire");
    }
    if (file.getSize() > maxFileSize) {
      throw new IllegalArgumentException(
          "Fichier trop volumineux. Max: " + (maxFileSize / 1024 / 1024) + "MB");
    }

    String contentType = file.getContentType();
    String originalFileName = file.getOriginalFilename();
    if (originalFileName == null || StringUtils.cleanPath(originalFileName).isBlank()) {
      throw new IllegalArgumentException("Le nom du fichier est obligatoire");
    }
    boolean allowed = false;

    // Vérification du Content-Type standard
    for (String type : allowedTypes) {
      if (type.trim().equalsIgnoreCase(contentType)) {
        allowed = true;
        break;
      }
    }

    if (!allowed) {
      throw new IllegalArgumentException("Type de fichier non autorise");
    }
  }

  public void deleteFile(String filePath) {
    if (filePath == null || filePath.isBlank()) {
      return;
    }
    try {
      Path path = this.fileStorageLocation.resolve(filePath).normalize();
      if (!path.startsWith(this.fileStorageLocation)) {
        throw new IllegalArgumentException("Chemin de fichier invalide");
      }
      Files.deleteIfExists(path);
      log.info("Fichier supprime: {}", filePath);
    } catch (IOException ex) {
      throw new IllegalStateException("Echec de la suppression du fichier", ex);
    }

  }
}
