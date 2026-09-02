package com.schoolmanagement.authentication.controller;

import com.schoolmanagement.authentication.dto.request.PermissionRequest;
import com.schoolmanagement.authentication.dto.response.PermissionResponse;
import com.schoolmanagement.authentication.entity.Permission;
import com.schoolmanagement.authentication.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public List<PermissionResponse> getAllPermissions() {
        return permissionService.getAllPermissions().stream()
                .map(permission -> PermissionResponse.builder()
                        .id(permission.getId())
                        .nomPermission(permission.getNomPermission())
                        .description(permission.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        if (permission == null) return ResponseEntity.notFound().build();

        PermissionResponse response = PermissionResponse.builder()
                .id(permission.getId())
                .nomPermission(permission.getNomPermission())
                .description(permission.getDescription())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(@Valid @RequestBody PermissionRequest request) {
        Permission permission = permissionService.createPermission(request);
        PermissionResponse response = PermissionResponse.builder()
                .id(permission.getId())
                .nomPermission(permission.getNomPermission())
                .description(permission.getDescription())
                .build();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> updatePermission(@PathVariable Long id, @RequestBody PermissionRequest request) {
        Permission permission = permissionService.updatePermission(id, request);
        if (permission == null) return ResponseEntity.notFound().build();

        PermissionResponse response = PermissionResponse.builder()
                .id(permission.getId())
                .nomPermission(permission.getNomPermission())
                .description(permission.getDescription())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePermission(@PathVariable Long id) {
        boolean deleted = permissionService.deletePermission(id);
        return deleted ? ResponseEntity.ok("Permission supprimée")
                       : ResponseEntity.notFound().build();
    }
}
