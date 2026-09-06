package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.Log;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

	@Override
	@EntityGraph(attributePaths = "utilisateur")
	List<Log> findAll();

	@Override
	@EntityGraph(attributePaths = "utilisateur")
	Optional<Log> findById(Long id);
}
