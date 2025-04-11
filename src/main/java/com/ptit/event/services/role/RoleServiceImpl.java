package com.ptit.event.services.role;

import com.ptit.event.entities.enums.Type;
import com.ptit.event.entities.models.RelUserRole;
import com.ptit.event.entities.models.Role;
import com.ptit.event.repositories.RoleRepository;
import com.ptit.event.responses.RelUserRolePermissonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired private RelUserRolePermissonRepository repository;
  @Autowired private RoleRepository roleRepository;

  @Override
  public boolean checkPermission(Long userId, Type type, Long objectId, String[] permissions) {
    List<String> roles = repository.findPermissionsByUserIdAndEventId(type, userId, objectId);
    List<String> requiredRoles = List.copyOf(List.of(permissions));

    return roles.containsAll(requiredRoles);
  }

  @Override
  public void grant(Long userId, Type type, Long objectId, List<String> permissions) {
    List<Role> roles = roleRepository.findByNameIn(permissions);
    Map<String, Role> roleByName =
        roles.stream().collect(Collectors.toMap(role -> role.getName().toString(), role -> role));

    List<RelUserRole> relUserRoles =
        permissions.stream()
            .map(
                permission -> {
                  RelUserRole relUserRole = new RelUserRole();
                  relUserRole.setUserId(userId);
                  relUserRole.setObjectId(objectId);
                  relUserRole.setType(type);
                  relUserRole.setRoleId(roleByName.get(permission).getId());
                  return relUserRole;
                })
            .collect(Collectors.toList());
    repository.saveAll(relUserRoles);
  }
}
