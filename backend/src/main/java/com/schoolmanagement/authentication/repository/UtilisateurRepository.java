package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.Utilisateur;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByNumeroTelephone(String numeroTelephone);

    Optional<Utilisateur> findByEmail(String email);

    @EntityGraph(attributePaths = "permissions")
    Optional<Utilisateur> findWithPermissionsById(Long id);

    @Query("SELECT u FROM Utilisateur u WHERE u.email = :identifiant OR u.numeroTelephone = :identifiant")
    Optional<Utilisateur> findByIdentifiant(@Param("identifiant") String identifiant);
}