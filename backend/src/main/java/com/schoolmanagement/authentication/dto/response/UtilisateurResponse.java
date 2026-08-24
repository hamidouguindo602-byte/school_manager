package com.schoolmanagement.authentication.dto.response;

import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.TypeRole;
import com.schoolmanagement.authentication.entity.Utilisateur;

import java.util.Set;
import java.util.stream.Collectors;

public record UtilisateurResponse(
        Long id,
        String numeroTelephone,
        String email,
        String nom,
        String prenom,
        StatutUtilisateur statut,
        TypeRole typeRole,
        Set<Long> permissionIds
) {

    public static UtilisateurResponse from(Utilisateur utilisateur) {

        Set<Long> permissionIds = utilisateur.getPermissions()
                .stream()
                .map(permission -> permission.getId())
                .collect(Collectors.toSet());

        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getNumeroTelephone(),
                utilisateur.getEmail(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getStatut(),
                utilisateur.getTypeRole(),
                permissionIds
        );
    }
}