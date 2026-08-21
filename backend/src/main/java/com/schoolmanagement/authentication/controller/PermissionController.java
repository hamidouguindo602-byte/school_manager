package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.entity.Permission;
import com.schoolmanagement.authentication.entity.Utilisateur;
import com.schoolmanagement.authentication.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permissions")
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return (permission != null) ? ResponseEntity.ok(permission) : ResponseEntity.notFound().build();
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        return ResponseEntity.ok(permissionService.createPermission(permission));
    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission updatedPermission) {
        Permission permission = permissionService.updatePermission(id, updatedPermission);
        return (permission != null) ? ResponseEntity.ok(permission) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        boolean deleted = permissionService.deletePermission(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/utilisateurs/{utilisateurId}/permissions")
    public ResponseEntity<Utilisateur> addPermissionToUtilisateur(@PathVariable Long utilisateurId, @RequestParam Long permissionId) {
        Utilisateur utilisateur = permissionService.addPermissionToUtilisateur(utilisateurId, permissionId);
        return (utilisateur != null) ? ResponseEntity.ok(utilisateur) : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/utilisateurs/{utilisateurId}/permissions")
    public ResponseEntity<Utilisateur> removePermissionFromUtilisateur(@PathVariable Long utilisateurId, @RequestParam Long permissionId) {
        Utilisateur utilisateur = permissionService.removePermissionFromUtilisateur(utilisateurId, permissionId);
        return (utilisateur != null) ? ResponseEntity.ok(utilisateur) : ResponseEntity.notFound().build();
    }
}
