package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Competition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdCompetition;
    private String CompetitionName;
    private Date Deadline;
    private String Description;
    private  String image;
 @ManyToOne
 private  User user;
    @OneToMany(mappedBy = "competition")
    private  Set<ReclamationCompetition> reclamationCompetitions;
    @OneToMany(mappedBy = "competition")
    private Set<CompetitionCandidacy> competitionCandidacies;
    @ManyToOne
    private Category category;
}