package com.debugarenaevents.service;

import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.dto.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO createEvent(AddEventDTO addEventDTO);

    List<EventDTO> getAllEvents();

    EventDTO getEventById(Long id);

    void deleteEvent(Long eventId);

    List<EventDTO> getWeeklyEvents();

    String checkServerStatus();
}
