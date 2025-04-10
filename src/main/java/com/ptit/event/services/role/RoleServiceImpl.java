package com.ptit.event.services.role;

import com.ptit.event.responses.RelUserRolePermissonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired private RelUserRolePermissonRepository repository;

  @Override
  public boolean checkPermission(Long userId, Long eventId, String[] permissions) {
    List<String> roles = repository.findPermissionsByUserIdAndEventId(userId, eventId);
    List<String> requiredRoles = List.copyOf(List.of(permissions));

    return roles.containsAll(requiredRoles);
  }
}
