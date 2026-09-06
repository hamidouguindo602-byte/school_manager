package com.schoolmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/** Active le remplissage automatique de createdAt / updatedAt (voir BaseEntity). */
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
