package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompetitionCandidacy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidacy;
    private String video;
    @OneToMany(mappedBy = "CompetitionCandidacy")
    private Set<Tutorial> tutorials;
    @ManyToOne
    private Competition competition;
    @OneToOne(mappedBy = "CompetitionCandidacy")
    private Grades grade;
    @OneToOne(mappedBy = "CompetitionCandidacy", cascade = CascadeType.ALL)
    private Grades grades;



}