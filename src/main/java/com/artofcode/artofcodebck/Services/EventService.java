package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.Event;
import com.artofcode.artofcodebck.Repositories.IEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService implements IEventService {
    @Autowired
    private IEventRepository eventRepository;


    @Override
    public Event createOrUpdateEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public  void deleteEvent(Long idEvent) {
        eventRepository.deleteById(idEvent);

    }
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


}
