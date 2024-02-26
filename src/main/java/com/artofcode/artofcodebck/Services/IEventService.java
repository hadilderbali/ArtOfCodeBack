package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Event;

import java.util.List;

public interface IEventService {
    List<Event> getAllEvents();


    Event createOrUpdateEvent(Event event);

    void deleteEvent(Long id);


}
