package com.example.demo.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate birthDate;

    List<String> roles;
}
