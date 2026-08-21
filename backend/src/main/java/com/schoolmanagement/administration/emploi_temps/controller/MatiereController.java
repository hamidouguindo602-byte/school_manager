package com.schoolmanagement.administration.emploi_temps.controller;

import com.schoolmanagement.administration.emploi_temps.dto.MatiereRequest;
import com.schoolmanagement.administration.emploi_temps.dto.MatiereResponse;
import com.schoolmanagement.administration.emploi_temps.service.MatiereService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matieres")
@RequiredArgsConstructor
public class MatiereController {

	private final MatiereService matiereService;

	@GetMapping
	public List<MatiereResponse> findAll() {
		return matiereService.findAll();
	}

	@GetMapping("/{id}")
	public MatiereResponse findById(@PathVariable Long id) {
		return matiereService.findById(id);
	}

	@PostMapping
	public ResponseEntity<MatiereResponse> create(@Valid @RequestBody MatiereRequest request) {
		MatiereResponse created = matiereService.create(request);
		return ResponseEntity.created(URI.create("/api/matieres/" + created.id())).body(created);
	}

	@PutMapping("/{id}")
	public MatiereResponse update(@PathVariable Long id, @Valid @RequestBody MatiereRequest request) {
		return matiereService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		matiereService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
