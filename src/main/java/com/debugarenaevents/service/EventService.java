package com.debugarenaevents.service;

import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.dto.EventDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDTO registerEvent(AddEventDTO addEventDTO);

    List<EventDTO> getAllEvents();

    EventDTO getEventById(Long id);

    List<EventDTO> getWeeklyEvents();

    String checkServerStatus();
}
