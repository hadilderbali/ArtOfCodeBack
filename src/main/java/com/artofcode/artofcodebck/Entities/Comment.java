package com.artofcode.artofcodebck.Entities;

import com.artofcode.artofcodebck.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Comment implements Serializable {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long commentId;
 private String content;
 @Temporal(TemporalType.TIMESTAMP)
 @Column(nullable = false)
 private LocalDateTime createdAt;

 @ManyToOne
 @JoinColumn(name = "tutorial_id")
 @JsonIgnore
 private Tutorial tutorial;
 @ManyToOne
 @JoinColumn(name = "user_id")
 private User user;

 public Comment() {
  this.createdAt = LocalDateTime.now();
 }

}




