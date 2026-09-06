package com.schoolmanagement.common.exception;

import java.time.Instant;
import java.util.Map;

/** Format unique des reponses d'erreur de l'API. */
public record ApiError(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path,
    Map<String, String> fieldErrors) {

  public static ApiError of(int status, String error, String message, String path) {
    return new ApiError(Instant.now(), status, error, message, path, Map.of());
  }
}
