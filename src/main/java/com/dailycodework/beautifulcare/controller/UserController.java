package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.UserCreateRequest;
import com.dailycodework.beautifulcare.dto.request.UserUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.UserResponse;
import com.dailycodework.beautifulcare.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return new APIResponse<>(true, "User created successfully", createdUser);
    }

    @GetMapping
    public APIResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new APIResponse<>(true, "Users retrieved successfully", users);
    }

    @GetMapping("/{userId}")
    public APIResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return new APIResponse<>(true, "User retrieved successfully", user);
    }

    @PutMapping("/{userId}")
    public APIResponse<UserResponse> updateUser(@PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUser(userId, request);
        return new APIResponse<>(true, "User updated successfully", updatedUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public APIResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new APIResponse<>(true, "User deleted successfully", null);
    }
}
