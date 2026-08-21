package com.schoolmanagement.authentication.entity;

import com.schoolmanagement.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "journalisation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Journalisation extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String action;

    @Column(nullable = false)
    private LocalDateTime dateAction;
}
