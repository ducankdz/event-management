package com.ptit.event.services.event;

import com.ptit.event.dtos.EventDto;
import com.ptit.event.entities.enums.EventState;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.Event;
import com.ptit.event.entities.models.RelEventUser;
import com.ptit.event.entities.models.User;
import com.ptit.event.entities.values.EventFilter;
import com.ptit.event.exceptions.CustomException;
import com.ptit.event.exceptions.ErrorMessage;
import com.ptit.event.repositories.EventRepository;
import com.ptit.event.repositories.RelEventUserRepository;
import com.ptit.event.repositories.UserRepository;
import com.ptit.event.utils.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RelEventUserRepository relEventUserRepository;

    @Override
    public Event create(EventDto dto, Long userId) {
        Event event = Common.mapAllProperties(dto, Event.class);
        event.setOwnerId(userId);
        event.setState(EventState.NOT_STARTED);
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event update(Long id, EventDto dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
        Common.mapNonNullProperties(dto, event);
        return eventRepository.save(event);
    }

    @Override
    public Event detail(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
    }

    @Override
    public Page<Event> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> filter(EventFilter filter, Pageable pageable) {
        return eventRepository.filter(filter, pageable);
    }

    @Override
    public void addGuest(Long id, List<String> emails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
        int page = (int) Math.ceil((double) emails.size() / 20);
        int count = 0;
        while(count <= page) {
            Pageable pageable = PageRequest.of(count, 20);
            List<User> users = userRepository.findByEmailIn(emails, pageable);
            List<RelEventUser> relEventUsers = new ArrayList<>();
            users.stream().forEach(user -> {
                RelEventUser relEventUser = new RelEventUser(id, user.getId());
                relEventUsers.add(relEventUser);
            });
            relEventUserRepository.saveAll(relEventUsers);
        }
    }

    @Override
    public void delete(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
        event.setIsDeleted(true);
        eventRepository.save(event);
    }

    @Override
    public Event changState(Long id, EventState state) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
        event.setState(state);
        return eventRepository.save(event);
    }
}
