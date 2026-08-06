package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private RoleRepository roleRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private LocalDate birthDate;

    @BeforeEach
    public void initData() {
        birthDate = LocalDate.of(1999, 1, 1);

        request = UserCreationRequest.builder()
                .userName("john")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .birthDate(birthDate)
                .build();

        userResponse = UserResponse.builder()
                .id(UUID.randomUUID())
                .userName("john")
                .firstName("John")
                .lastName("Doe")
                .birthDate(birthDate)
                .build();

        user = User.builder()
                .id(UUID.randomUUID())
                .userName("john")
                .password("12345678")
                .firstName("John")
                .lastName("Doe")
                .birthDate(birthDate)
                .build();
    }

    @Test
    void createUser_valid_success() {
        // GIVEN
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(roleRepository.findById(anyString()))
                .thenReturn(Optional.of(
                        Role.builder().name("USER").description("User role").build()));
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createRequest(request);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo(user.getId());
        Assertions.assertThat(response.getUserName()).isEqualTo("john");
    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN
        when(roleRepository.findById(anyString()))
                .thenReturn(Optional.of(
                        Role.builder().name("USER").description("User role").build()));
        when(userRepository.save(any()))
                .thenThrow(new org.springframework.dao.DataIntegrityViolationException("Duplicate entry"));

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.createRequest(request));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_valid_success() {
        // GIVEN
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.of(user));

        // WHEN
        var response = userService.getMyInfo();

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo(user.getId());
        Assertions.assertThat(response.getUserName()).isEqualTo("john");
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_notFound_fail() {
        // GIVEN
        when(userRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        // WHEN
        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
    }
}
