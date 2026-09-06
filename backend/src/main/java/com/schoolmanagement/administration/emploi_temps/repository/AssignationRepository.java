package com.schoolmanagement.administration.emploi_temps.repository;

import com.schoolmanagement.administration.emploi_temps.entity.Assignation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignationRepository extends JpaRepository<Assignation, Long> {
  List<Assignation> findByClasseId(Long classeId);

  List<Assignation> findByEnseignantId(Long enseignantId);
}
