package com.schoolmanagement.administration.emploi_temps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Payload d'entree pour la creation / mise a jour de l'entite Matiere.
 */
public record MatiereRequest(
		@NotBlank @Size(max = 100) String nom,
		@NotBlank @Size(max = 20) String code,
		Double coefficient) {
}
