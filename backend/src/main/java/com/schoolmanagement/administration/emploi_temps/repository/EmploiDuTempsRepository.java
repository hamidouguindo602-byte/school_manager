package com.schoolmanagement.administration.emploi_temps.repository;

import com.schoolmanagement.administration.emploi_temps.entity.EmploiDuTemps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalTime;
import java.util.List;

public interface EmploiDuTempsRepository extends JpaRepository<EmploiDuTemps, Long> {

    @Query("SELECT e FROM EmploiDuTemps e JOIN e.assignation a WHERE a.classe.id = :classeId")
    List<EmploiDuTemps> findByClasseId(@Param("classeId") Long classeId);

    @Query("SELECT e FROM EmploiDuTemps e JOIN e.assignation a WHERE a.enseignant.id = :enseignantId")
    List<EmploiDuTemps> findByEnseignantId(@Param("enseignantId") Long enseignantId);

    @Query("""
        SELECT COUNT(e) > 0
        FROM EmploiDuTemps e
        WHERE e.assignation.id = :assignationId
          AND e.jour = :jour
          AND e.heureDebut < :heureFin
          AND e.heureFin > :heureDebut
          AND (:excludeId IS NULL OR e.id != :excludeId)
        """)
    boolean existsConflitHoraire(
        @Param("assignationId") Long assignationId,
        @Param("jour") String jour,
        @Param("heureDebut") LocalTime heureDebut,
        @Param("heureFin") LocalTime heureFin,
        @Param("excludeId") Long excludeId
    );
}