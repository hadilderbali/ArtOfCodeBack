package com.artofcode.artofcodebck.Entities;

import com.artofcode.artofcodebck.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReclamationCompetition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reclamationId;

    @ElementCollection
    private List<String> type = new ArrayList<>();

    @NotBlank(message = "Description is required")
    private String description;
    private String sentiment;


    private LocalDate dateCreation;

    @Enumerated(EnumType.STRING)
    private ReclamationStatus reclamationStatus;

    @ManyToOne
    @JoinColumn(name = "competition_id")  // Nom de la colonne faisant r�f�rence � la comp�tition dans la table de r�clamation
    @JsonIgnore
    private Competition competition;
    @ManyToOne
    private User user;

}

