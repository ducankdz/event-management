package com.ptit.event.controllers;

import com.ptit.event.dtos.TaskDto;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.Task;
import com.ptit.event.services.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {
  @Autowired private TaskService taskService;

  @PostMapping
  public ResponseEntity<Task> create(
      @RequestHeader(name = "user-id") Long userId, @Valid TaskDto dto) {
    dto.setCreatedUserId(userId);
    return new ResponseEntity<>(taskService.create(dto), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> update(
      @RequestHeader(name = "user-id") Long userId,
      @PathVariable(name = "id") Long id,
      @Valid TaskDto dto) {
    dto.setUpdatedUserId(userId);
    return new ResponseEntity<>(taskService.update(id, dto), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Task> changeState(@PathVariable Long id, @RequestParam State state) {
    return new ResponseEntity<>(taskService.changeState(id, state), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    taskService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> detail(@PathVariable Long id) {
    return new ResponseEntity<>(taskService.detail(id), HttpStatus.OK);
  }

  @GetMapping("/{eventId}")
  public Page<Task> getAll(@PathVariable Long stageId, Pageable pageable) {
    return taskService.getByStageId(stageId, pageable);
  }
}
