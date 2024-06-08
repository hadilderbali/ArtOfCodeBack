package com.artofcode.artofcodebck.Entities;

import com.artofcode.artofcodebck.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Blog blog;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}