package com.schoolmanagement.administration.emploi_temps.service;

import com.schoolmanagement.administration.emploi_temps.dto.MatiereRequest;
import com.schoolmanagement.administration.emploi_temps.dto.MatiereResponse;
import com.schoolmanagement.administration.emploi_temps.entity.Matiere;
import com.schoolmanagement.administration.emploi_temps.repository.MatiereRepository;
import com.schoolmanagement.common.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatiereService {

	private final MatiereRepository matiereRepository;


	public List<MatiereResponse> findAll() {
		return matiereRepository.findAll().stream()
				.map(MatiereResponse::from)
				.toList();
	}

	public MatiereResponse findById(Long id) {
		return MatiereResponse.from(getOrThrow(id));
	}

	@Transactional
	public MatiereResponse create(MatiereRequest request) {
		Matiere matiere = Matiere.builder()
				.nom(request.nom())
				.code(request.code())
				.coefficient(request.coefficient())
				.build();
		return MatiereResponse.from(matiereRepository.save(matiere));
	}

	@Transactional
	public MatiereResponse update(Long id, MatiereRequest request) {
		Matiere entity = getOrThrow(id);
		entity.setNom(request.nom());
		entity.setCode(request.code());
		entity.setCoefficient(request.coefficient());
		return MatiereResponse.from(entity);
	}

	@Transactional
	public void delete(Long id) {
		matiereRepository.delete(getOrThrow(id));
	}

	private Matiere getOrThrow(Long id) {
		return matiereRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Matiere", id));
	}

}
