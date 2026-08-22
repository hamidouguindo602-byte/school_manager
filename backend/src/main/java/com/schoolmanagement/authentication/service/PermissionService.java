package com.schoolmanagement.authentication.service;

import com.schoolmanagement.authentication.dto.request.PermissionRequest;
import com.schoolmanagement.authentication.entity.Permission;
import com.schoolmanagement.authentication.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission createPermission(PermissionRequest request) {
        Permission permission = Permission.builder()
                .nomPermission(request.getNomPermission())
                .description(request.getDescription())
                .build();
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public Permission updatePermission(Long id, PermissionRequest request) {
        return permissionRepository.findById(id).map(permission -> {
            permission.setNomPermission(request.getNomPermission());
            permission.setDescription(request.getDescription());
            return permissionRepository.save(permission);
        }).orElse(null);
    }

    public boolean deletePermission(Long id) {
        return permissionRepository.findById(id).map(permission -> {
            permissionRepository.delete(permission);
            return true;
        }).orElse(false);
    }
}
