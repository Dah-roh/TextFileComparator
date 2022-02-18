package com.dizsart.textfilecomparator.repository;

import com.dizsart.textfilecomparator.model.CompareHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompareHistoryRepository extends JpaRepository<CompareHistory, Long> {
    List<CompareHistory> findAllByLecturersUsername(String username);
}
