package com.dizsart.textfilecomparator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<UploadFile> files;
    private String percentageResults;
    @Column(columnDefinition = "TEXT")
    private String similaritiesResults;
    private String lecturersUsername;
}
