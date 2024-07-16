package com.debugarenaevents.web;

import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.dto.EventDTO;
import com.debugarenaevents.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDTO> registerEvent(@RequestBody AddEventDTO addEventDTO) {

        eventService.registerEvent(addEventDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long id) {

        EventDTO eventDTO = eventService.getEventById(id);

        return ResponseEntity.ok(eventDTO);
    }

    @GetMapping("/weekly-events")
    public ResponseEntity<List<EventDTO>> getWeeklyEvents() {
        return ResponseEntity.ok(eventService.getWeeklyEvents());
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
}
