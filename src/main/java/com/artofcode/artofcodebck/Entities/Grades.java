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

public class Grades implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdGrade;
    private float CandidacyGrade;

    @OneToOne
    @MapsId("idCandidacy") // Map to the "idCandidacy" property of Candidacy entity
    private CompetitionCandidacy competitionCandidacy;

}