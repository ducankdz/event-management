package com.ptit.event.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    String email;
    String fullName;
    String password;
    String retypePassword;
    String avatar;
}
