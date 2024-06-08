package com.artofcode.artofcodebck.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalTime;
import java.util.Set;
import com.artofcode.artofcodebck.user.User;

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
    private  String Picture;
    private LocalTime hour;
    private boolean expired;
    private  byte [] image;
    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private  Set<ReclamationCompetition> reclamationCompetitions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "judge_id")
    @JsonIgnore
    private User judge;

    @OneToMany(mappedBy = "competition")
    @JsonIgnore
    private Set<CompetitionCandidacy> CompetitionCandidacy;




}
