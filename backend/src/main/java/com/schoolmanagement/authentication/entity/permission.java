package com.schoolmanagement.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermission;

    @Column(nullable = false, unique = true, length = 100)
    private String nomPermission;

    @Column(columnDefinition = "TEXT")
    private String description;
}
