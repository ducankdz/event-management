package com.ptit.event.services.auth;

import com.ptit.event.configurations.JwtTokenUtil;
import com.ptit.event.services.role.RoleService;
import com.ptit.event.services.role.RoleServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("eventPermissionEvaluator")
public class EventPermissionEvaluator {

  @Autowired private HttpServletRequest request;
  @Autowired private JwtTokenUtil tokenUtil;
  @Autowired private RoleService roleService;

  public boolean hasPermission(Long eventId, String[] permisisons) {
    String token = request.getHeader("Authorization");
    if (token == null || token.isBlank()) return false;

    try {
      Long userId = tokenUtil.getUserIdFromToken(token);
      roleService.checkPermission(userId, eventId, permisisons);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
