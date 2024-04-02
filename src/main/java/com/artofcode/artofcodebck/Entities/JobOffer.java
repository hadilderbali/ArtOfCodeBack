package com.artofcode.artofcodebck.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JobOffer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idR;
    private String  title ;
    private  String location;
    private  Integer number ;

    private LocalDate datePost;
    private  String description;
    private  String email;
    private  String fileName;
    private  float salaryRange;
    @Enumerated(EnumType.STRING)
    private  Type jobType;
    @JsonIgnore
    @OneToOne (mappedBy = "jobOffer", cascade = CascadeType.REMOVE)
    private JobApplication jobApplication;
    @JsonIgnore
    @ManyToOne
    private  User user;
}
