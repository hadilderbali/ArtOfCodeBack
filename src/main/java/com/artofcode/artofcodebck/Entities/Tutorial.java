package com.artofcode.artofcodebck.Entities;

import com.artofcode.artofcodebck.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


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

    @Enumerated(EnumType.STRING)
    private TutorialCategory tutorialCategory;

    @ManyToOne
    private CompetitionCandidacy CompetitionCandidacy;

    @ManyToOne
    @JoinColumn(name = "category_id")

    private Category category;
    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL)

    private List<Comment> comments;

    private int likes;

    private int dislikes;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

}