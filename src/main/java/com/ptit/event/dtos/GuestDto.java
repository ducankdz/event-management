package com.ptit.event.dtos;

import java.util.List;
import lombok.Data;

@Data
public class GuestDto {
  private List<String> emails;
}
