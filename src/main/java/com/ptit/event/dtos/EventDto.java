package com.ptit.event.dtos;

import com.ptit.event.entities.values.Location;
import com.ptit.event.entities.values.Schedule;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class EventDto {
  private Long createdUserId;
  private Long updatedUserId;
  private String name;
  private String avatar;
  private List<String> images;
  private String description;
  private Location location;
  private Schedule schedule;
  private Timestamp startedAt;
  private Timestamp endedAt;
}
