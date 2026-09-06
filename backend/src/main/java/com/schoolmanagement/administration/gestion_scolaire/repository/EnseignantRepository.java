package com.schoolmanagement.administration.gestion_scolaire.repository;

import com.schoolmanagement.administration.gestion_scolaire.entity.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {}
