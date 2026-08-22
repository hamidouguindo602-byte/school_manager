package com.schoolmanagement.authentication.repository;

import com.schoolmanagement.authentication.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
