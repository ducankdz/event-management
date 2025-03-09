package com.ptit.event.services.event;

import com.ptit.event.dtos.EventDto;
import com.ptit.event.entities.enums.EventState;
import com.ptit.event.entities.models.Event;
import com.ptit.event.entities.values.EventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    Event create(EventDto dto, Long userId);
    Event update(Long id, EventDto dto);
    Event detail(Long id);
    Page<Event> getAll(Pageable pageable);
    Page<Event> filter(EventFilter filter, Pageable pageable);
    void addGuest(Long id, List<String> emails);
    void delete(Long id);
    Event changState(Long id, EventState state);
}
