package com.dizsart.textfilecomparator.service;

import com.dizsart.textfilecomparator.model.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CompareHistoryService {
    Map<String, String> compareTextFiles(MultipartFile[] filePaths) throws IOException;
    Map<String,String> rerun(List<UploadFile> files);
}
