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
public class Tutorial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutorialId;
    private String title;
    private String description;
    private Long duration;
    @Enumerated(EnumType.STRING)
    private Level level;
    @ManyToOne
    private CompetitionCandidacy CompetitionCandidacy;
    @ManyToOne
    private Category category;



}