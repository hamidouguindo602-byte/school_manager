package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.entity.Permission;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.repository.PermissionRepository;
import com.schoolmanagement.authentication.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public Permission updatePermission(Long id, Permission updatedPermission) {
        return permissionRepository.findById(id)
                .map(permission -> {
                    permission.setNomPermission(updatedPermission.getNomPermission());
                    permission.setDescription(updatedPermission.getDescription());
                    return permissionRepository.save(permission);
                })
                .orElse(null);
    }

    public boolean deletePermission(Long id) {
        return permissionRepository.findById(id)
                .map(permission -> {
                    permissionRepository.delete(permission);
                    return true;
                })
                .orElse(false);
    }

    public Utilisateur addPermissionToUtilisateur(Long utilisateurId, Long permissionId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        Permission permission = permissionRepository.findById(permissionId).orElse(null);

        if (utilisateur == null || permission == null) return null;

        if ("ADMIN".equalsIgnoreCase(utilisateur.getRole())) {
            utilisateur.getPermissions().add(permission);
            utilisateurRepository.save(utilisateur);
        }

        return utilisateur;
    }

    public Utilisateur removePermissionFromUtilisateur(Long utilisateurId, Long permissionId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        Permission permission = permissionRepository.findById(permissionId).orElse(null);

        if (utilisateur == null || permission == null) return null;

        utilisateur.getPermissions().remove(permission);
        utilisateurRepository.save(utilisateur);

        return utilisateur;
    }
}
