package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long categoryId;
    private String title;
    private String description;
    private  String imageC;
    @OneToMany(mappedBy = "category")
    private Set<Tutorial> tutorials;
    @OneToMany(mappedBy = "category")
    private  Set<Blog> blogList;
    @OneToMany(mappedBy = "category")
    private Set<Competition> competitions;

}
