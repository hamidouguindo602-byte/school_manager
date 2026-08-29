package com.schoolmanagement.administration.gestion_scolaire.repository;

import com.schoolmanagement.administration.gestion_scolaire.entity.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
}