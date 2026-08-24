package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.UtilisateurRequest;
import com.schoolmanagement.authentication.dto.response.UtilisateurResponse;
import com.schoolmanagement.authentication.entity.Permission;
import com.schoolmanagement.authentication.entity.StatutUtilisateur;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.PermissionRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UtilisateurResponse> findAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurResponse::from)
                .toList();
    }

    public UtilisateurResponse findById(Long id) {
        return UtilisateurResponse.from(getOrThrow(id));
    }

    @Transactional
    public UtilisateurResponse create(UtilisateurRequest request) {

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.nom())
                .prenom(request.prenom())
                .numeroTelephone(request.numeroTelephone())
                .email(normalizeEmail(request.email()))
                .motDePasse(passwordEncoder.encode(request.motDePasse()))
                .statut(request.statut())
                .typeRole(request.typeRole())
                .permissions(getPermissions(request.permissionIds()))
                .build();

        return UtilisateurResponse.from(
                utilisateurRepository.save(utilisateur)
        );
    }

    @Transactional
    public UtilisateurResponse update(Long id, UtilisateurRequest request) {

        Utilisateur entity = getOrThrow(id);

        entity.setNom(request.nom());
        entity.setPrenom(request.prenom());
        entity.setNumeroTelephone(request.numeroTelephone());
        entity.setEmail(normalizeEmail(request.email()));

        entity.setMotDePasse(
                passwordEncoder.encode(request.motDePasse())
        );

        entity.setStatut(request.statut());
        entity.setTypeRole(request.typeRole());

        return UtilisateurResponse.from(entity);
    }

    @Transactional
    public void delete(Long id) {
        utilisateurRepository.delete(getOrThrow(id));
    }

    private Utilisateur getOrThrow(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Utilisateur", id)
                );
    }

    private Set<Permission> getPermissions(Set<Long> permissionIds) {

        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }

        List<Permission> permissions =
                permissionRepository.findAllById(permissionIds);

        if (permissions.size() != permissionIds.size()) {
            throw new IllegalArgumentException(
                    "Une ou plusieurs permissions sont introuvables"
            );
        }

        return new HashSet<>(permissions);
    }

    private String normalizeEmail(String email) {
        return email == null || email.isBlank() ? null : email;
    }

    @Transactional
    public void activer(Long id) {
        Utilisateur utilisateur = getOrThrow(id);
        utilisateur.setStatut(StatutUtilisateur.ACTIF);
    }

    @Transactional
    public void desactiver(Long id) {
        Utilisateur utilisateur = getOrThrow(id);
        utilisateur.setStatut(StatutUtilisateur.INACTIF);
    }
}