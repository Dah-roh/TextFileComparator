package com.dizsart.textfilecomparator.service;

import com.dizsart.textfilecomparator.model.CompareHistory;
import com.dizsart.textfilecomparator.model.UploadFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CompareHistoryService {
    Map<String, String> compareTextFiles(MultipartFile[] filePaths) throws IOException;
    Map<String,String> rerun(List<UploadFile> files);
    List<CompareHistory> findAllHistories(User principal);

    void save(CompareHistory compareHistory);
}
