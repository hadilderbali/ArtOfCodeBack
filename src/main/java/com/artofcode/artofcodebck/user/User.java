package com.artofcode.artofcodebck.user;

import com.artofcode.artofcodebck.Entities.*;
import com.artofcode.artofcodebck.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.artofcode.artofcodebck.profile.profile;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import lombok.*;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "_user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private boolean enabled; // Indicates if user account is enabled (confirmed)
  private boolean confirmed;
  @JsonIgnore
@ManyToMany
  private List<User> followerUsers;
  @JsonIgnore
@ManyToMany
private List<User> followingUsers;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private profile profile; // Adjusted to include the profile


  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;
  @OneToMany
  private Set<Blog> blogs;
  @OneToOne
  private Profil profil;
  @OneToMany(mappedBy = "user")
  private Set<Club>clubs;
  @OneToMany
  private Set<com.artofcode.artofcodebck.Entities.CompetitionCandidacy> CompetitionCandidacy;
  @OneToMany(mappedBy = "user")
  private Set<Event>events;
  @OneToMany(mappedBy = "user")
  private Set<JobApplication>jobApplications;
  @OneToMany(mappedBy = "user")
  private Set<JobOffer>jobOffers;
  @OneToMany(mappedBy = "user")
  private Set<Challenges>challenges;
  @OneToMany(mappedBy = "senderId")
  @JsonIgnore
  private Set<Message> messages1;
  @OneToMany(mappedBy = "recipientId")
  @JsonIgnore
  private Set<Message> messages2;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


}
