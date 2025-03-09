package com.ptit.event.services.stage;

import com.ptit.event.dtos.StageDto;
import com.ptit.event.entities.enums.State;
import com.ptit.event.entities.models.Event;
import com.ptit.event.entities.models.Stage;
import com.ptit.event.exceptions.CustomException;
import com.ptit.event.exceptions.ErrorMessage;
import com.ptit.event.repositories.EventRepository;
import com.ptit.event.repositories.StageRepository;
import com.ptit.event.utils.Common;
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
public class StageServiceImpl implements StageService {
  @Autowired private StageRepository stageRepository;
  @Autowired private EventRepository eventRepository;

  @Override
  public Stage create(StageDto dto) {
    Event event =
        eventRepository
            .findById(dto.getEventId())
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.EVENT_NOT_FOUND));
    Stage stage = new Stage();
    Common.mapNonNullProperties(dto, stage);
    stage.setStage(State.PENDING);
    return stageRepository.save(stage);
  }

  @Override
  public Stage update(Long id, StageDto dto) {
    Stage stage =
        stageRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.STAGE_NOT_FOUND));
    Common.mapNonNullProperties(dto, stage);
    return stageRepository.save(stage);
  }

  @Override
  public void delete(Long id) {
    Stage stage =
        stageRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.STAGE_NOT_FOUND));
    stage.setIsDeleted(true);
    stageRepository.save(stage);
  }

  @Override
  public Stage changeState(Long id, State state) {
    Stage stage =
        stageRepository
            .findById(id)
            .orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.STAGE_NOT_FOUND));
    stage.setStage(state);
    return stageRepository.save(stage);
  }

  @Override
  public Page<Stage> getAllByEvent(Long eventId, Pageable pageable) {
    pageable =
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt"));
    return stageRepository.findByEventId(eventId, pageable);
  }

  @Override
  public Stage detail(Long id) {
    return stageRepository
        .findById(id)
        .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorMessage.STAGE_NOT_FOUND));
  }
}
