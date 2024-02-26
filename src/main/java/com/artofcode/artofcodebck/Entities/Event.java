package com.artofcode.artofcodebck.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvent;
    private String eventName;
    private String description;
    private String city;
    private int availableTickets;
    private Float ticketPrice;
    private Date eventDate;
    private String imageE;

    // Méthode pour définir l'identifiant de l'événement
    public void setId(Long id) {
        this.idEvent = id;
    }



    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
//@ManyToOne
    //private User user;
}