package com.debugarenaevents.service.impl;

import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.entity.Event;
import com.debugarenaevents.repository.EventRepository;
import com.debugarenaevents.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final ModelMapper mapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ModelMapper mapper) {
        this.eventRepository = eventRepository;
        this.mapper = mapper;
    }


    @Override
    public void registerEvent(AddEventDTO addEventDTO) {
        Event eventToSave = mapper.map(addEventDTO, Event.class);

        eventRepository.save(eventToSave);
    }
}
