package com.dizsart.textfilecomparator.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String studentName;
    private String fileName;
    @Lob
    private byte[] content;
    private String fileType;
    private Long size;
    private String lecturersUsername;

}
