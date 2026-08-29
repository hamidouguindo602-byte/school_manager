package com.schoolmanagement.administration.absences.repository;

import com.schoolmanagement.administration.absences.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEleveId(Long eleveId);

    @Query("SELECT a FROM Absence a JOIN a.emploiDuTemps e JOIN e.assignation asg WHERE asg.classe.id = :classeId")
    List<Absence> findByClasseId(@Param("classeId") Long classeId);

    @Query("SELECT a FROM Absence a JOIN a.emploiDuTemps e JOIN e.assignation asg WHERE asg.enseignant.id = :enseignantId")
    List<Absence> findByEnseignantId(@Param("enseignantId") Long enseignantId);

    @Query("SELECT a FROM Absence a JOIN a.emploiDuTemps e JOIN e.assignation asg WHERE asg.classe.id = :classeId AND a.dateAbsence BETWEEN :debut AND :fin")
    List<Absence> findByClasseIdAndDateBetween(@Param("classeId") Long classeId, @Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}