package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Event;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    List<Event> getAllEvents();


    Event createOrUpdateEvent(Event event);

    void deleteEvent(Long id);


    Optional<Event> retrieveEventById(Long id);

}
