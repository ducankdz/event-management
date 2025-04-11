package com.ptit.event.services.auth;

import com.ptit.event.configurations.JwtTokenUtil;
import com.ptit.event.entities.enums.Type;
import com.ptit.event.services.role.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("eventPermissionEvaluator")
public class EventPermissionEvaluator {

  @Autowired private HttpServletRequest request;
  @Autowired private JwtTokenUtil tokenUtil;
  @Autowired private RoleService roleService;

  public boolean hasPermission(String typeStr, Long objectId, String[] permisisons) {
    String token = request.getHeader("Authorization");
    if (token == null || token.isBlank()) return false;
    Type type = Type.valueOf(typeStr);
    try {
      Long userId = tokenUtil.getUserIdFromToken(token);
      roleService.checkPermission(userId, type, objectId, permisisons);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
