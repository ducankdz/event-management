package com.ptit.event.services.task;

import com.ptit.event.dtos.TaskDto;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.RelTaskUser;
import com.ptit.event.entities.models.Stage;
import com.ptit.event.entities.models.Task;
import com.ptit.event.exceptions.CustomException;
import com.ptit.event.exceptions.ErrorMessage;
import com.ptit.event.repositories.RelTaskUserRepository;
import com.ptit.event.repositories.StageRepository;
import com.ptit.event.repositories.TaskRepository;
import com.ptit.event.utils.Common;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

  @Autowired private StageRepository stageRepository;
  @Autowired private TaskRepository taskRepository;
  @Autowired private RelTaskUserRepository relTaskUserRepository;

  @Override
  public Task create(TaskDto dto) {
    Stage stage =
        stageRepository
            .findById(dto.getStageId())
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.STAGE_NOT_FOUND));
    Task task = new Task();
    Common.mapNonNullProperties(dto, task);
    task.setEventId(stage.getEventId());
    task.setState(State.PENDING);
    List<RelTaskUser> relTaskUsers = createBatchRelTaskUser(task, dto.getUserIds());
    task.setRelTaskUsers(relTaskUsers);
    return taskRepository.save(task);
  }

  @Override
  public Task update(Long id, TaskDto dto) {
    Task task =
        taskRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.TASK_NOT_FOUND));
    Common.mapNonNullProperties(dto, task);
    List<RelTaskUser> relTaskUsers = new ArrayList<>();
    task.getRelTaskUsers().clear();
    task.setRelTaskUsers(relTaskUsers);
    return taskRepository.save(task);
  }

  private List<RelTaskUser> createBatchRelTaskUser(Task task, List<Long> userIds) {
    return userIds.stream()
        .map(userId -> new RelTaskUser(task, userId))
        .collect(Collectors.toList());
  }

  @Override
  public void delete(Long id) {
    Task task =
        taskRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.TASK_NOT_FOUND));
    task.setIsDeleted(true);
    taskRepository.save(task);
  }

  @Transactional
  @Override
  public Task changeState(Long id, State state) {
    Task task =
        taskRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.TASK_NOT_FOUND));
    task.setIsDeleted(true);
    return taskRepository.save(task);
  }

  @Override
  public Page<Task> getByStageId(Long stageId, Pageable pageable) {
    pageable =
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("created_at"));
    return taskRepository.findByStageId(stageId, pageable);
  }

  @Override
  public Task detail(Long id) {
    return taskRepository
        .findById(id)
        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.TASK_NOT_FOUND));
  }
}
