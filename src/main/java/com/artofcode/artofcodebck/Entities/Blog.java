package com.artofcode.artofcodebck.Entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.artofcode.artofcodebck.user.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Blog implements Serializable {
    @Id
    @GeneratedValue
    private Long idBlog;
    private String title;
    private String content;
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd'T'HH:mm:ss", timezone = "UTC")
    private Date createdDate;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private  BlogCategory blogCategory;
    @JsonIgnore
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Rating> ratings;
}
