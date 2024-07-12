package com.debugarenaevents.service;

import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.dto.EventDTO;

import java.util.List;

public interface EventService {

    void registerEvent(AddEventDTO addEventDTO);

    List<EventDTO> getAllEvents();
}
