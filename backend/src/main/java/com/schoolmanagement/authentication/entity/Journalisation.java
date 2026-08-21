package com.schoolmanagement.authentication.entity;

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
public class Journalisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String action;

    @Column(nullable = false)
    private LocalDateTime dateAction;
}
