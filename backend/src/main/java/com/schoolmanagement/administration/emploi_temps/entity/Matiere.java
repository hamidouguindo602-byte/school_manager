package com.schoolmanagement.administration.emploi_temps.entity;

import com.schoolmanagement.common.domain.EntieBase;
import com.schoolmanagement.common.domain.EntieBase;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matieres")
public class Matiere extends EntieBase {

	@Column(nullable = false, length = 100)
	private String nom;

	@Column(nullable = false, unique = true, length = 20)
	private String code;

	@Column(nullable = true)
	private Double coefficient;
}
