package com.ptit.event.services.user;

import com.ptit.event.dtos.UserDTO;
import com.ptit.event.entities.models.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id) throws Exception;
    User findUserByJwt(String jwt) throws Exception;
    User updateRole(Long userId, List<Long> roleIds) throws Exception;
}
