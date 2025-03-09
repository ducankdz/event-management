package com.ptit.event.services.stage;

import com.ptit.event.dtos.StageDto;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StageService {
  Stage create(StageDto dto);

  Stage update(Long id, StageDto dto);

  void delete(Long id);

  Stage changeState(Long id, State state);

  Page<Stage> getAllByEvent(Long eventId, Pageable pageable);

  Stage detail(Long id);
}
