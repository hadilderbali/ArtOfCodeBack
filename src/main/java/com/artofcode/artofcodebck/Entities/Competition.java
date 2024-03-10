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
    private long IdCompetition;
    private String CompetitionName;
    private Date Deadline;
    private String Description;
    private  byte [] image;

    @OneToMany(mappedBy = "competition")
    private  Set<ReclamationCompetition> reclamationCompetitions;

    @ManyToOne
    private Category category;



    @OneToMany(mappedBy = "competition")
    private Set<CompetitionCandidacy> CompetitionCandidacy;}
