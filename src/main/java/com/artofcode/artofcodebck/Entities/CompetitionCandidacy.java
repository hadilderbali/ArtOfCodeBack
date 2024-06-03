package com.artofcode.artofcodebck.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import com.artofcode.artofcodebck.user.User;

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
    private String username;
    private String userlastname;
    private String CompetitionTitle;
    @JsonIgnore
    @OneToMany(mappedBy = "CompetitionCandidacy")
    private Set<Tutorial> tutorials;
    @JsonIgnore
    @ManyToOne
    private Competition competition;
    @JsonIgnore
    @OneToOne(mappedBy = "CompetitionCandidacy", cascade = CascadeType.ALL)
    private Grades grades;
    @JsonIgnore
    @ManyToOne
    private User user;
}