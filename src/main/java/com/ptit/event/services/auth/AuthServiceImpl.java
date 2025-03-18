package com.ptit.event.services.auth;

import com.ptit.event.configurations.JwtTokenUtil;
import com.ptit.event.dtos.AuthDTO;
import com.ptit.event.entities.models.Role;
import com.ptit.event.entities.models.User;
import com.ptit.event.repositories.RoleRepository;
import com.ptit.event.repositories.UserRepository;
import com.ptit.event.services.CustomUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;
  private final CustomUserDetailsServiceImpl customUserDetailsService;
  private final RoleRepository roleRepository;

  @Override
  public User register(AuthDTO authDTO) throws Exception {
    String email = authDTO.getEmail();
    if (userRepository.existsByEmail(email)) {
      throw new RuntimeException("Email already exists");
    }
    if (!authDTO.getPassword().equals(authDTO.getRetypePassword())) {
      throw new RuntimeException("Password doesn't match");
    }
    if (authDTO.getRoleId() == null) {
      authDTO.setRoleId(1L);
    }
    Role role =
        roleRepository
            .findById(authDTO.getRoleId())
            .orElseThrow(() -> new RuntimeException("Role doesn't exist"));
    Set<Role> roles = new HashSet<>();
    roles.add(role);
    User newUser =
        User.builder()
            .email(authDTO.getEmail())
            .password(passwordEncoder.encode(authDTO.getPassword()))
            .fullName(authDTO.getFullName())
            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
            .roles(roles)
            .build();
    return userRepository.save(newUser);
  }

  @Override
  public String login(AuthDTO authDTO) throws Exception {
    String email = authDTO.getEmail();
    String password = authDTO.getPassword();
    UserDetails user = customUserDetailsService.loadUserByUsername(email);
    if (user == null) {
      throw new BadCredentialsException("Invalid email or password");
    }
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("Invalid email or password");
    }
    Authentication auth =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
    return jwtTokenUtil.generateToken(auth);
  }
}
