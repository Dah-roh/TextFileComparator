package com.dizsart.textfilecomparator.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class CompareHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<UploadFile> files;
    private String percentageResults;
    @Column(columnDefinition = "TEXT")
    private String similaritiesResults;
    private String lecturersUsername;
}
