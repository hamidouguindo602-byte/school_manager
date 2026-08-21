package com.schoolmanagement.administration.emploi_temps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schoolmanagement.administration.emploi_temps.entity.Matiere;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {

	boolean existsByCode(String code);
}
