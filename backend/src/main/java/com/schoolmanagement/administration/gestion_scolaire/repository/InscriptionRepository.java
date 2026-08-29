package com.schoolmanagement.administration.gestion_scolaire.repository;

import com.schoolmanagement.administration.gestion_scolaire.entity.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    // Vous pouvez ajouter ici des requêtes personnalisées si nécessaire par la suite 
    // (ex: List<Inscription> findByStatut(StatusInscription statut);)
}