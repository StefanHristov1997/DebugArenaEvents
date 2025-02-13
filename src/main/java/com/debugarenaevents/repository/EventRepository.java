package com.debugarenaevents.repository;

import com.debugarenaevents.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventsByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
