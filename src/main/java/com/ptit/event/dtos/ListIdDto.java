package com.ptit.event.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ListIdDto {
  private List<UUID> uuids;
}
