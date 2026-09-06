package com.schoolmanagement.authentication.entity;

import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission extends EntieBase {

  @Column(nullable = false, unique = true, length = 100)
  private String nomPermission;

  @Column(columnDefinition = "TEXT")
  private String description;
}
