package com.dizsart.textfilecomparator.repository;

import com.dizsart.textfilecomparator.model.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadFile, Long> {
}
