package com.artofcode.artofcodebck.profile;

import com.artofcode.artofcodebck.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    @JsonIgnore
    private User user;
    @Lob
    private String profilephoto;
    @Lob
    private String coverphoto;
    @Enumerated(EnumType.STRING)
    private musicpref musicpref;
    @Enumerated(EnumType.STRING)
    private dancepref dancepref;
    @Enumerated(EnumType.STRING)
    private will will;
    @Column(length = 1000)
    private String aboutMe;
    private String phonenumber;
    private String address;
    private String githublink;
    private String facebooklink;
    private String instagramlink;
}
