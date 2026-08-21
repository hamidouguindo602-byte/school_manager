package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.UtilisateurRequest;
import com.schoolmanagement.authentication.dto.response.UtilisateurResponse;
import com.schoolmanagement.authentication.entity.Utilisateur;
//import com.schoolmanagement.authentication.repository.PermissionRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    //private final PermissionRepository permissionRepository;

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
                .email(request.email())
                .motDePasse(request.motDePasse())
                .statut(request.statut())
                .typeRole(request.typeRole())
                //.permissions(getPermissions(request.permissionIds()))
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
        entity.setEmail(request.email());
        entity.setMotDePasse(request.motDePasse());
        entity.setStatut(request.statut());
        entity.setTypeRole(request.typeRole());
        //entity.setPermissions(getPermissions(request.permissionIds()));

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
/*
    private Set<Permission> getPermissions(Set<Long> permissionIds) {

        if (permissionIds == null || permissionIds.isEmpty()) {
            return new HashSet<>();
        }

       List<Permission> permissions = PermissionRepository.findAllById(permissionIds);

        if (permissions.size() != permissionIds.size()) {
            throw new IllegalArgumentException(
                    "Une ou plusieurs permissions sont introuvables"
            );
        }

        return new HashSet<>(permissions);
    }*/

}