package com.ptit.event.services.role;

public interface RoleService {
  boolean checkPermission(Long userId, Long eventId, String[] permissions);
}
