package com.debugarenaevents.web;

import com.debugarenaevents.exeption.ObjectNotFoundException;
import com.debugarenaevents.model.entity.Event;
import com.debugarenaevents.model.enums.VideoPlatformEnum;
import com.debugarenaevents.repository.EventRepository;
import com.debugarenaevents.service.EventService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
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
    private EventService eventService;

    @Autowired
    private MockMvc mockMvc;

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

        String resultBody = result.getResponse().getContentAsString();
        Integer id = JsonPath.read(resultBody, "$.id");

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
    void testCheckServerStatus() throws Exception {

        mockMvc.perform(get("/api/events/check"))
                .andExpect(status().isOk());
    }

    private Event createTestEvent() {

        Event testEvent = new Event();

        testEvent.setTitle("test title");
        testEvent.setDescription("test description");
        testEvent.setAuthorName("test name");
        testEvent.setAuthorEmail("test@test.com");
        testEvent.setDate(LocalDateTime.now());
        testEvent.setPlatform(VideoPlatformEnum.FACEBOOK);

        return testEvent;
    }
}