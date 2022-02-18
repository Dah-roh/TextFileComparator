package com.dizsart.textfilecomparator.service;

import com.dizsart.textfilecomparator.model.CompareHistory;
import com.dizsart.textfilecomparator.model.UploadFile;
import com.dizsart.textfilecomparator.repository.CompareHistoryRepository;
import com.dizsart.textfilecomparator.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileServiceImpl implements FileService{

    private CompareHistory compareHistory = new CompareHistory();
    private FileRepository fileRepository;
    private CompareHistoryRepository compareHistoryRepository;
    private CompareHistoryServiceImpl compareHistoryService;
    int count = 0;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, CompareHistoryRepository compareHistoryRepository, CompareHistoryServiceImpl compareHistoryService) {
        this.fileRepository = fileRepository;
        this.compareHistoryRepository = compareHistoryRepository;
        this.compareHistoryService = compareHistoryService;
    }

    public Map<String, String> save(MultipartFile[] file, String userName, String[] studentDTO) throws IOException {
        List<UploadFile> uploadedFiles =  new ArrayList<>();
        for (MultipartFile uploadFile: file) {
            UploadFile fileToUpload = new UploadFile();
            fileToUpload.setStudentName(studentDTO[count]);
            fileToUpload.setFileType(uploadFile.getContentType());
            fileToUpload.setFileName(StringUtils.cleanPath(Objects.requireNonNull(uploadFile.getOriginalFilename())));
            fileToUpload.setContent(uploadFile.getBytes());
            fileToUpload.setSize(uploadFile.getSize());
            fileToUpload.setLecturersUsername(userName);
            uploadedFiles.add(fileRepository.saveAndFlush(fileToUpload));
            count++;
        }
        var results = compareHistoryService.compareTextFiles(file);
        compareHistory.setFiles(uploadedFiles);
        compareHistory.setPercentageResults(results.get("Percentage"));
        compareHistory.setSimilaritiesResults(results.get("Similarities"));
        compareHistory.setLecturersUsername(userName);
        this.saveHistory(compareHistory);
        return results;
    }

    @CachePut("compareHistories")
    public CompareHistory saveHistory(CompareHistory compareHistory){
        return compareHistoryRepository.save(compareHistory);
    }

}
