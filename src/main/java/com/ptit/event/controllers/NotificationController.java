package com.ptit.event.controllers;

import com.ptit.event.configurations.header.Header;
import com.ptit.event.dtos.ListIdDto;
import com.ptit.event.entities.models.Notification;
import com.ptit.event.services.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notification")
public class NotificationController {
  @Autowired private NotificationService service;

  @GetMapping
  public ResponseEntity<Page<Notification>> filter(
      @RequestHeader(name = Header.userId) Long userId,
      @RequestParam(name = "keyword") List<UUID> keyword,
      @SortDefault(sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable) {
    return ResponseEntity.ok(service.filter(userId, keyword, pageable));
  }

  @PutMapping
  public ResponseEntity<?> read(@RequestBody ListIdDto dto) {
    service.read(dto.getUuids());
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
