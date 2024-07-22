package com.debugarenaevents.web;

import com.debugarenaevents.model.entity.Event;
import com.debugarenaevents.model.enums.VideoPlatformEnum;
import com.debugarenaevents.repository.EventRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class EventControllerIT {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clearDB() {
        eventRepository.deleteAll();
    }

    @Test
    void testCreateEvent() throws Exception {

        MvcResult result = mockMvc.perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                     "title" : "TestAPI123",

                                     "description" : "TestAPI123123123123123",

                                     "authorName": "TestAPI",

                                     "authorEmail" : "TestAPI@com",

                                     "date" : "2024-08-25T21:34:55",

                                     "platform" :"DISCORD"
                                 }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Integer id = JsonPath.read(responseBody, "$.id");

        Optional<Event> createdEvent = eventRepository.findById(Long.valueOf(id));

        Assertions.assertTrue(createdEvent.isPresent());
        Assertions.assertEquals(createdEvent.get().getId(), Long.valueOf(id));
        Assertions.assertEquals(createdEvent.get().getTitle(), "TestAPI123");
        Assertions.assertEquals(createdEvent.get().getDescription(), "TestAPI123123123123123");
        Assertions.assertEquals(createdEvent.get().getAuthorName(), "TestAPI");
        Assertions.assertEquals(createdEvent.get().getAuthorEmail(), "TestAPI@com");
        Assertions.assertEquals(createdEvent.get().getDate().toString(), "2024-08-25T21:34:55");
        Assertions.assertEquals(createdEvent.get().getPlatform().name(), "DISCORD");
    }

    @Test
    void testGetEventById() throws Exception {

        Event event = createTestEvent();
        event.setTitle("Test Get Event By Id");
        eventRepository.save(event);

        mockMvc.perform(get("/api/events/{id}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId().intValue())))
                .andExpect(jsonPath("$.title", is(event.getTitle())))
                .andExpect(jsonPath("$.description", is(event.getDescription())))
                .andExpect(jsonPath("$.platform", is(event.getPlatform().toString())));
    }

    @Test
    void testGetAllEvents() throws Exception {

        Event firstEvent = createTestEvent();
        firstEvent.setTitle("First Event");

        Event secondEvent = createTestEvent();
        secondEvent.setTitle("Second Event");

        List<Event> testEvents = List.of(firstEvent, secondEvent);

        eventRepository.saveAll(testEvents);

        mockMvc.perform(get("/api/events/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(firstEvent.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(firstEvent.getTitle())))
                .andExpect(jsonPath("$[0].description", is(firstEvent.getDescription())))
                .andExpect(jsonPath("$[0].authorName", is(firstEvent.getAuthorName())))
                .andExpect(jsonPath("$[0].authorEmail", is(firstEvent.getAuthorEmail())))
                .andExpect(jsonPath("$[0].platform", is(firstEvent.getPlatform().toString())))
                .andExpect(jsonPath("$[1].id", is(secondEvent.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(secondEvent.getTitle())))
                .andExpect(jsonPath("$[1].description", is(secondEvent.getDescription())))
                .andExpect(jsonPath("$[1].authorName", is(secondEvent.getAuthorName())))
                .andExpect(jsonPath("$[1].authorEmail", is(secondEvent.getAuthorEmail())))
                .andExpect(jsonPath("$[1].platform", is(secondEvent.getPlatform().toString())));
    }

    @Test
    void testGetWeeklyEvents() throws Exception {

        Event event = createTestEvent();
        event.setTitle("Test Weekly Event");
        event.setDate(LocalDateTime.now().plusDays(7));

        eventRepository.save(event);

        mockMvc.perform(get("/api/events/weekly-events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(event.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(event.getTitle())))
                .andExpect(jsonPath("$[0].description", is(event.getDescription())))
                .andExpect(jsonPath("$[0].authorName", is(event.getAuthorName())))
                .andExpect(jsonPath("$[0].authorEmail", is(event.getAuthorEmail())))
                .andExpect(jsonPath("$[0].platform", is(event.getPlatform().toString())));
    }

    @Test
    void testCheckServerStatus() throws Exception {

        mockMvc.perform(get("/api/events/check"))
                .andExpect(status().isOk());
    }

    private Event createTestEvent() {

        Event testEvent = new Event();

        testEvent.setDescription("test description");
        testEvent.setAuthorName("test name");
        testEvent.setAuthorEmail("test@test.com");
        testEvent.setDate(LocalDateTime.now());
        testEvent.setPlatform(VideoPlatformEnum.FACEBOOK);

        return testEvent;
    }
}