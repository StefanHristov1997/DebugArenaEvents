package com.debugarenaevents.service.impl;

import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.dto.EventDTO;
import com.debugarenaevents.model.entity.Event;
import com.debugarenaevents.repository.EventRepository;
import com.debugarenaevents.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<EventDTO> getAllEvents() {
        return eventRepository
                .findAll()
                .stream()
                .map(event -> mapper.map(event, EventDTO.class))
                .toList();
    }

    @Override
    public EventDTO getEventById(Long id) {
        return eventRepository
                .findById(id)
                .map(event -> mapper.map(event, EventDTO.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<EventDTO> getWeeklyEvents() {
        return eventRepository
                .findEventsByDateBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(7))
                .stream()
                .map(event -> mapper.map(event, EventDTO.class))
                .toList();
    }
}
