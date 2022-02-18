package com.dizsart.textfilecomparator.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

public interface FileService {
    Map<String, String> save(MultipartFile[] file, String user, String[] studentDTO)throws IOException;

}
