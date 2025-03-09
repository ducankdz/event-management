package com.ptit.event.services.task;

import com.ptit.event.dtos.TaskDto;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task create(TaskDto dto);

    Task update(Long id, TaskDto dto);

    void delete(Long id);

    Task changeState(Long id, State state);

    Page<Task> getByStageId(Long stageId, Pageable pageable);

    Task detail(Long id);
}
