package com.ptit.event.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthDTO {
    String email;
    String password;
    String retypePassword;
    String fullName;
    Long roleId;
}