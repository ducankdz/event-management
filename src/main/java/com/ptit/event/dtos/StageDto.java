package com.ptit.event.dtos;

import lombok.Data;

@Data
public class StageDto {
  private Long eventId;
  private Long createdUserId;
  private Long updatedUserId;
  private String name;
  private String description;
}
