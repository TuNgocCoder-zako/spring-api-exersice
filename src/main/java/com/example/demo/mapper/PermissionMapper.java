package com.example.demo.mapper;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse userToResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    default String map(Role role) {
        if (role == null) {
            return null;
        }
        return role.getName();
    }
}
