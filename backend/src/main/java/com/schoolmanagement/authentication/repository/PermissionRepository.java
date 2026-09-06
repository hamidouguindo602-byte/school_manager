package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.Permission;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
  Optional<Permission> findByNomPermission(String nomPermission);
}
