package com.schoolmanagement.administration.emploi_temps.dto;


import com.schoolmanagement.administration.emploi_temps.entity.Matiere;

/**
 * Vue exposee par l'API pour l'entite Matiere.
 */
public record MatiereResponse(
		Long id,
		String nom,
		String code,
		Double coefficient) {

	public static MatiereResponse from(Matiere matiere) {
		return new MatiereResponse(
				matiere.getId(),
				matiere.getNom(),
				matiere.getCode(),
				matiere.getCoefficient());
	}
}
