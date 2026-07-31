package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    UUID id;
    String userName;
    String firstName;
    String lastName;
    LocalDate birthDate;

    Set<RoleResponse> roles;
}
