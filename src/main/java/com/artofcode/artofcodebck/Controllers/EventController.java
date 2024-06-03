package com.artofcode.artofcodebck.Controllers;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.artofcode.artofcodebck.Entities.Event;
import com.artofcode.artofcodebck.Services.IEventService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    private IEventService eventService;

    @PostMapping("/createEvent/")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createOrUpdateEvent(event);
    }

    @PutMapping("/updateEvent/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        event.setIdEvent(id);
        return eventService.createOrUpdateEvent(event);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/get")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/getEventById/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> eventOptional = eventService.retrieveEventById(id);
        return eventOptional.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Controller method to like an event
    @PostMapping("/likeEvent/{id}")
    public ResponseEntity<Event> likeEvent(@PathVariable Long id) {
        Optional<Event> eventOptional = eventService.retrieveEventById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setLikes(event.getLikes() + 1); // Increment likes count
            return ResponseEntity.ok(eventService.createOrUpdateEvent(event));
        }
        return ResponseEntity.notFound().build();
    }

    // Controller method to dislike an event
    @PostMapping("/dislikeEvent/{id}")
    public ResponseEntity<Event> dislikeEvent(@PathVariable Long id) {
        Optional<Event> eventOptional = eventService.retrieveEventById(id);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setDislikes(event.getDislikes() + 1); // Increment dislikes count
            return ResponseEntity.ok(eventService.createOrUpdateEvent(event));
        }
        return ResponseEntity.notFound().build();
    }

}
