package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobApplication  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idDancer;
    private String nameD;
    private String emailDancer;
    private Integer phoneNumberDancer;
    private  String imageA;

    private String coverLetter;

    @OneToOne
    private JobOffer jobOffer;
    @ManyToOne
    private User user;
}