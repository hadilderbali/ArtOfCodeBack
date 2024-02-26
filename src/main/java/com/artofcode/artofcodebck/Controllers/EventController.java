package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Event;
import com.artofcode.artofcodebck.Services.IEventService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {


    private IEventService eventService;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createOrUpdateEvent(event);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        event.setId(id);
        return eventService.createOrUpdateEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

}
