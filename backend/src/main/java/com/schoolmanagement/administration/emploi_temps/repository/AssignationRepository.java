package com.schoolmanagement.administration.emploi_temps.repository;

import com.schoolmanagement.administration.emploi_temps.entity.Assignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignationRepository extends JpaRepository<Assignation, Long> {
    List<Assignation> findByClasseId(Long classeId);
    List<Assignation> findByEnseignantId(Long enseignantId);
}