package com.dizsart.textfilecomparator.repository;

import com.dizsart.textfilecomparator.model.CompareHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompareHistoryRepository extends JpaRepository<CompareHistory, Long> {
    Optional<CompareHistory> findAllByLecturersUsername(String username);
}
