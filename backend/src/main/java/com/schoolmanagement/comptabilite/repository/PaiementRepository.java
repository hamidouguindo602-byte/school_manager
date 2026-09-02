package com.schoolmanagement.comptabilite.repository;

import com.schoolmanagement.comptabilite.entity.Paiement;
import com.schoolmanagement.comptabilite.entity.StatutPaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository
        extends JpaRepository<Paiement, Long> {

    List<Paiement> findByEcheanceIdEcheance(
            Long idEcheance
    );

    List<Paiement> findByStatut(
            StatutPaiement statut
    );

    boolean existsByEcheanceIdEcheanceAndStatut(
            Long idEcheance,
            StatutPaiement statut
    );
}