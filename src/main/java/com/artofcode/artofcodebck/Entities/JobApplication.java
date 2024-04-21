package com.artofcode.artofcodebck.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private  boolean status = false;
    private String coverLetter;
    @JsonIgnore
    @OneToOne
    private JobOffer jobOffer;
  @JsonIgnore
    @ManyToOne
  @JoinColumn(name = "dancer_id")
    private User user;
}