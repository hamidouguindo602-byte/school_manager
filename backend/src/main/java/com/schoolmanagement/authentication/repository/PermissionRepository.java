package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByNomPermission(String nomPermission);
}
