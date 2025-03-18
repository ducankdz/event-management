package com.ptit.event.responses;

import com.ptit.event.entities.models.Role;
import com.ptit.event.entities.models.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
  Long id;
  String email;
  String fullName;
  String avatar;
  Set<Role> roles;
  Timestamp createdAt;

  public static UserResponse fromUser(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .fullName(user.getFullName())
        .avatar(user.getAvatar())
        .roles(user.getRoles())
        .createdAt(user.getCreatedAt())
        .build();
  }
}
