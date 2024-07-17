package com.debugarenaevents.web;

import com.debugarenaevents.exeption.ObjectNotFoundException;
import com.debugarenaevents.model.dto.AddEventDTO;
import com.debugarenaevents.model.dto.EventDTO;
import com.debugarenaevents.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> registerEvent(
            @Valid @RequestBody AddEventDTO addEventDTO) {

        eventService.registerEvent(addEventDTO);

        return new ResponseEntity<>("Event created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @GetMapping("/weekly-events")
    public ResponseEntity<List<EventDTO>> getWeeklyEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getWeeklyEvents());
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAllEvents());
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkServerStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.checkServerStatus());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<String> handleEventNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
