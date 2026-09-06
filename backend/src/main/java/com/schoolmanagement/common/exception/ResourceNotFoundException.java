package com.schoolmanagement.common.exception;

/** Levee quand une ressource demandee par son identifiant n'existe pas. */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String resource, Object id) {
    super("%s introuvable avec l'id %s".formatted(resource, id));
  }
}
