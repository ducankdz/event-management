package com.ptit.event.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TaskDto {
  private Long taskId;
  private Long createdUserId;
  private Long updatedUserId;
  private Long eventId;
  private Long stageId;
  private String name;
  private String description;
  private List<String> images;
  private List<Long> userIds;
  private Long budget;
}
