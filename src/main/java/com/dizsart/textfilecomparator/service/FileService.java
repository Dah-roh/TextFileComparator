package com.dizsart.textfilecomparator.service;

import com.dizsart.textfilecomparator.model.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    void save(MultipartFile[] file, String user, String[] studentDTO)throws IOException;
    Optional<UploadFile> getFile(Long id);
    List<UploadFile> getAllFiles();

}
