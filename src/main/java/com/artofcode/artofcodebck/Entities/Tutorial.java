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
    private String video;
    @Enumerated(EnumType.STRING)
    private Level level;
    @ManyToOne
    private CompetitionCandidacy CompetitionCandidacy;
    @ManyToOne
    @JoinColumn(name = "category_id") // Utilisez cette annotation pour la colonne de jointure
    private Category category; // Renommez cette propriété pour correspondre à la propriété mappée dans Category


}