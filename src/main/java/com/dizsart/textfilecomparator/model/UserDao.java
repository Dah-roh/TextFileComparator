package com.dizsart.textfilecomparator.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column
    @JsonIgnore
    private String password;


}

