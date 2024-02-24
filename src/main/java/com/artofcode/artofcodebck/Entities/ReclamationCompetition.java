package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReclamationCompetition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reclamationId;
    private String type;
    private String desciption;
    private String reclamationStatus;
    @ManyToOne
    private Competition competition;
}

