package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller: Create User");

        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setData(userService.createRequest(request));
        return response;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("UserName : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable UUID userId) {
        return userService.getUserByID(userId);
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder().data(userService.getMyInfo()).build();
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{usersId}")
    String deleteUser(@PathVariable UUID usersId) {
        userService.deleteUser(usersId);
        return "Your account is delected";
    }
}
