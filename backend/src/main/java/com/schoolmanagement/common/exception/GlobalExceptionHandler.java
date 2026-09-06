package com.schoolmanagement.common.exception;
/*
import com.schoolmanagement.administration.emploi_temps.exception.ConflitHoraireException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/Traduit les exceptions en reponses HTTP normalisees (ApiError).
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(
      ResourceNotFoundException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiError.of(404, "Not Found", ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handleEntityNotFound(
      EntityNotFoundException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ApiError.of(404, "Not Found", ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(AuthentificationException.class)
  public ResponseEntity<ApiError> handleAuthentication(
      AuthentificationException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(ApiError.of(401, "Unauthorized", ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleSpringAuthentication(
      AuthenticationException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ApiError.of(401, "Unauthorized", "Authentification requise", request.getRequestURI()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiError> handleAccessDenied(
      AccessDeniedException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ApiError.of(403, "Forbidden", "Acces refuse", request.getRequestURI()));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrity(
      DataIntegrityViolationException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(
            ApiError.of(
                409,
                "Conflict",
                "La ressource existe deja ou viole une contrainte",
                request.getRequestURI()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, String> fieldErrors = new HashMap<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    ApiError body =
        new ApiError(
            Instant.now(),
            400,
            "Bad Request",
            "Donnees invalides",
            request.getRequestURI(),
            fieldErrors);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(ConflitHoraireException.class)
  public ResponseEntity<ApiError> handleConflitHoraire(
      ConflitHoraireException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiError.of(409, "Conflict", ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgument(
      IllegalArgumentException ex, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiError.of(400, "Bad Request", ex.getMessage(), request.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpected(Exception ignored, HttpServletRequest request) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            ApiError.of(
                500,
                "Internal Server Error",
                "Une erreur interne est survenue",
                request.getRequestURI()));
  }
}*/




import com.schoolmanagement.administration.emploi_temps.exception.ConflitHoraireException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Traduit les exceptions en reponses HTTP normalisees (ApiError). */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(
          ResourceNotFoundException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiError.of(
                    404,
                    "Not Found",
                    ex.getMessage(),
                    request.getRequestURI()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handleEntityNotFound(
          EntityNotFoundException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiError.of(
                    404,
                    "Not Found",
                    ex.getMessage(),
                    request.getRequestURI()));
  }

  @ExceptionHandler(AuthentificationException.class)
  public ResponseEntity<ApiError> handleAuthentication(
          AuthentificationException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiError.of(
                    401,
                    "Unauthorized",
                    ex.getMessage(),
                    request.getRequestURI()));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ApiError> handleSpringAuthentication(
          AuthenticationException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(
                    ApiError.of(
                            401,
                            "Unauthorized",
                            "Authentification requise",
                            request.getRequestURI()));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiError> handleAccessDenied(
          AccessDeniedException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(
                    ApiError.of(
                            403,
                            "Forbidden",
                            "Acces refuse",
                            request.getRequestURI()));
  }

  /**
   * Gestion des violations d'integrite.
   *
   * <p>On ne retourne jamais le message SQL/JPA au client.
   * Les details techniques restent uniquement dans les logs.
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleDataIntegrity(
          DataIntegrityViolationException ex,
          HttpServletRequest request) {

    // Le detail technique est conserve dans les logs serveur.
    // Il ne doit jamais etre retourne au client.
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                    ApiError.of(
                            409,
                            "Conflict",
                            getConflictMessage(request),
                            request.getRequestURI()));
  }

  /**
   * Message metier generique.
   *
   * <p>On distingue simplement le DELETE des autres operations,
   * sans exposer les contraintes SQL.
   */
  private String getConflictMessage(HttpServletRequest request) {

    if ("DELETE".equalsIgnoreCase(request.getMethod())) {
      return "Impossible de supprimer cette ressource car elle est utilisee.";
    }

    return "Un utilisateur avec ces informations existe deja.";
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(
          MethodArgumentNotValidException ex,
          HttpServletRequest request) {

    Map<String, String> fieldErrors = new HashMap<>();

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      fieldErrors.put(
              fieldError.getField(),
              fieldError.getDefaultMessage());
    }

    ApiError body =
            new ApiError(
                    Instant.now(),
                    400,
                    "Bad Request",
                    "Donnees invalides",
                    request.getRequestURI(),
                    fieldErrors);

    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(ConflitHoraireException.class)
  public ResponseEntity<ApiError> handleConflitHoraire(
          ConflitHoraireException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                    ApiError.of(
                            409,
                            "Conflict",
                            ex.getMessage(),
                            request.getRequestURI()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArgument(
          IllegalArgumentException ex,
          HttpServletRequest request) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                    ApiError.of(
                            400,
                            "Bad Request",
                            ex.getMessage(),
                            request.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpected(
          Exception ex,
          HttpServletRequest request) {

    // Important : le detail de l'exception reste cote serveur.
    // On ne l'expose pas dans la reponse HTTP.
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                    ApiError.of(
                            500,
                            "Internal Server Error",
                            "Une erreur interne est survenue",
                            request.getRequestURI()));
  }
}

