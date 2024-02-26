package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class JobOffer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdR;
    private String  Title ;
    private  String Location;
    private  Integer Number ;
    private  String Description;
    private Date DatePost;
    private  String Email;
    private  String image;
    private  float SalaryRange;
    @Enumerated(EnumType.STRING)
    private  Type JobType;
    @OneToOne(mappedBy = "jobOffer" )
    private JobApplication jobApplication;
    @ManyToOne
    private  User user;
}
