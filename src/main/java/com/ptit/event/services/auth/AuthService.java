package com.ptit.event.services.auth;

import com.ptit.event.dtos.AuthDTO;
import com.ptit.event.entities.models.User;

public interface AuthService {
    User register(AuthDTO authDTO) throws Exception;
    String login(AuthDTO authDTO) throws Exception;
}
