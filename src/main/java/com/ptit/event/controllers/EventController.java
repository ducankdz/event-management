package com.ptit.event.controllers;

import com.ptit.event.dtos.EventDto;
import com.ptit.event.dtos.GuestDto;
import com.ptit.event.entities.enums.EventState;
import com.ptit.event.entities.models.Event;
import com.ptit.event.entities.values.EventFilter;
import com.ptit.event.services.event.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {
  @Autowired private EventService eventService;

  @PostMapping
  public ResponseEntity<Event> create(
      @RequestHeader(name = "user-id") Long userId, @Valid EventDto dto) {
    return new ResponseEntity<>(eventService.create(dto, userId), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Event> update(
      @RequestHeader(name = "user-id") Long userId,
      @PathVariable(name = "id") Long id,
      @Valid EventDto dto) {
    dto.setUpdatedUserId(userId);
    return new ResponseEntity<>(eventService.update(id, dto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
    eventService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Event> changeState(
      @PathVariable(name = "id") Long id, @RequestParam EventState state) {
    return new ResponseEntity<>(eventService.changState(id, state), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Event> detail(@PathVariable(name = "id") Long id) {
    return new ResponseEntity<>(eventService.detail(id), HttpStatus.OK);
  }

  @GetMapping
  public Page<Event> filter(@RequestBody EventFilter filter, Pageable pageable) {
    return eventService.filter(filter, pageable);
  }

  @PostMapping("/{id}/add_guest")
  public ResponseEntity<?> addGuest(@PathVariable(name = "id") Long id, @Valid GuestDto dto) {
    eventService.addGuest(id, dto.getEmails());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
