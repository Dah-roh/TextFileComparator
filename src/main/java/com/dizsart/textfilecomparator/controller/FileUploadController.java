package com.dizsart.textfilecomparator.controller;

import com.dizsart.textfilecomparator.model.CompareHistory;
import com.dizsart.textfilecomparator.model.StudentDTO;
import com.dizsart.textfilecomparator.model.UserDao;
import com.dizsart.textfilecomparator.repository.CompareHistoryRepository;
import com.dizsart.textfilecomparator.service.CompareHistoryService;
import com.dizsart.textfilecomparator.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class FileUploadController {

    private FileService fileService;
    private CompareHistoryService compareHistoryService;
    private CompareHistoryRepository compareHistoryRepository;

    @Autowired
    public FileUploadController(FileService fileService, CompareHistoryService compareHistoryService, CompareHistoryRepository compareHistoryRepository) {
        this.fileService = fileService;
        this.compareHistoryService = compareHistoryService;
        this.compareHistoryRepository = compareHistoryRepository;
    }

    @PostMapping("/files")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile[] file, @RequestParam("students") String[] studentNames) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = principal.getUsername();
        try {
            fileService.save(file, userName, studentNames);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Files uploaded successfully: %s, %s", file[0].getOriginalFilename(), file[1].getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the files: %s!", file[0].getOriginalFilename()+" "+file[1].getOriginalFilename()));
        }
    }

    @PostMapping("/rerun/{historyId}")
    public Map<String, String> rerun (@PathVariable Long historyId) {
        Optional<CompareHistory>  compareHistory = compareHistoryRepository.findById(historyId);
        return compareHistoryService.rerun(compareHistory.get().getFiles());

    }

    @GetMapping("/history")
    public ResponseEntity<?> viewHistory () {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CompareHistory>  compareHistory = compareHistoryRepository.findAllByLecturersUsername(principal.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(compareHistory);

    }


}
