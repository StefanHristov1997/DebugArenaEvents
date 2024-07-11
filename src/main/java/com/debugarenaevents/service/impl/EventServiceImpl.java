package com.debugarenaevents.service.impl;

import com.debugarenaevents.repository.EventRepository;
import com.debugarenaevents.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void registerEvent() {

    }
}
