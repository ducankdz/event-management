package com.ptit.event.services.role;

import com.ptit.event.entities.enums.Type;

import java.util.List;

public interface RoleService {
  boolean checkPermission(Long userId, Type type, Long eventId, String[] permissions);

  void grant(Long userId, Type type, Long objectId, List<String> permissions);
}
