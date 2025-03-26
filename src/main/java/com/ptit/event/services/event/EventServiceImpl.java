package com.ptit.event.services.event;

import com.ptit.event.dtos.EventDto;
import com.ptit.event.entities.enums.Action;
import com.ptit.event.entities.enums.EventState;
import com.ptit.event.entities.enums.NotificationCode;
import com.ptit.event.entities.enums.NotificationType;
import com.ptit.event.entities.models.Event;
import com.ptit.event.entities.models.RelEventUser;
import com.ptit.event.entities.models.User;
import com.ptit.event.entities.values.EventFilter;
import com.ptit.event.exceptions.CustomException;
import com.ptit.event.exceptions.ErrorMessage;
import com.ptit.event.repositories.EventRepository;
import com.ptit.event.repositories.RelEventUserRepository;
import com.ptit.event.repositories.UserRepository;
import com.ptit.event.services.notification.NotificationService;
import com.ptit.event.utils.Common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventServiceImpl implements EventService {
  @Autowired private EventRepository eventRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private RelEventUserRepository relEventUserRepository;
  @Autowired private NotificationService notificationService;

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
    Event event =
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
    Common.mapNonNullProperties(dto, event);
    createNotifications(
        event, Action.UPDATE, NotificationType.EVENT, NotificationCode.UPDATE_EVENT);
    return eventRepository.save(event);
  }

  @Override
  public Event detail(Long id) {
    return eventRepository
        .findById(id)
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
    Event event =
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));

    Map<String, Object> data = new HashMap<>();
    data.put("name", event.getName());
    data.put("id", id);

    int page = (int) Math.ceil((double) emails.size() / 20);
    int count = 0;
    while (count <= page) {
      Pageable pageable = PageRequest.of(count, 20);
      List<User> users = userRepository.findByEmailIn(emails, pageable);
      List<RelEventUser> relEventUsers = new ArrayList<>();
      users.stream()
          .forEach(
              user -> {
                RelEventUser relEventUser = new RelEventUser(id, user.getId());
                relEventUsers.add(relEventUser);
              });
      relEventUserRepository.saveAll(relEventUsers);
      createNotifications(
          event, Action.CREATE, NotificationType.EVENT, NotificationCode.CREATE_EVENT);
    }
  }

  @Override
  public void delete(Long id) {
    Event event =
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
    event.setIsDeleted(true);
    eventRepository.save(event);
    createNotifications(
        event, Action.DELETE, NotificationType.EVENT, NotificationCode.DELETE_EVENT);
  }

  @Override
  public Event changState(Long id, EventState state) {
    Event event =
        eventRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
    event.setState(state);
    return eventRepository.save(event);
  }

  private void createNotifications(
      Event event, Action action, NotificationType type, NotificationCode code) {
    Long count = relEventUserRepository.countGuestInEvent(event.getId());
    int totalPage = (int) Math.ceil((double) count / 20);
    int page = 0;

    Map<String, Object> data = new HashMap<>();
    data.put("name", event.getName());
    data.put("id", event.getId());

    while (page <= totalPage) {
      Pageable pageable = PageRequest.of(page, 20);
      notificationService.createBatchByUsers(
          getUserInEvent(event.getId(), pageable), action, type, code, data);
    }
  }

  private List<User> getUserInEvent(Long id, Pageable pageable) {
    List<RelEventUser> relEventUsers = relEventUserRepository.findByEventId(id, pageable);
    return relEventUsers.stream().map(RelEventUser::getUser).collect(Collectors.toList());
  }
}
