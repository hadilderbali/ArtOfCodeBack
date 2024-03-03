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

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    private String username;
    private String userlastname;
    private String usermail;
    private String usertel;
    private String userpassword;
    private String userbdate;
    private String userphoto;
    @Enumerated(EnumType.STRING)
    private  Role role;

@OneToMany
    private Set<Blog>blogs;
@OneToOne
    private Profil profil;
@OneToMany(mappedBy = "user")
    private Set<Club>clubs;

@OneToMany(mappedBy = "user")
    private Set<Event>events;
@OneToMany(mappedBy = "user")
    private Set<JobApplication>jobApplications;
@OneToMany(mappedBy = "user")
    private Set<JobOffer>jobOffers;
@OneToMany(mappedBy = "user")
    private Set<Challenges>challenges;


}

