package com.ptit.event.controllers;

import com.ptit.event.dtos.StageDto;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.Stage;
import com.ptit.event.services.stage.StageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StageController {
  @Autowired private StageService stageService;

  @PostMapping
  public ResponseEntity<Stage> create(
      @RequestHeader(name = "user-id") Long userId, @Valid StageDto dto) {
    dto.setCreatedUserId(userId);
    return new ResponseEntity<>(stageService.create(dto), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Stage> update(
      @RequestHeader(name = "user-id") Long userId,
      @PathVariable(name = "id") Long id,
      @Valid StageDto dto) {
    dto.setUpdatedUserId(userId);
    return new ResponseEntity<>(stageService.update(id, dto), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Stage> changeState(@PathVariable Long id, @RequestParam State state) {
    return new ResponseEntity<>(stageService.changeState(id, state), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    stageService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Stage> detail(@PathVariable Long id) {
    return new ResponseEntity<>(stageService.detail(id), HttpStatus.OK);
  }

  @GetMapping("/{eventId}")
  public Page<Stage> getAll(@PathVariable Long eventId, Pageable pageable) {
    return stageService.getAllByEvent(eventId, pageable);
  }
}
