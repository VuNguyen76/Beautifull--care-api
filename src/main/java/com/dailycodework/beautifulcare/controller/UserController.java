package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.UserCreateRequest;
import com.dailycodework.beautifulcare.dto.request.UserUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.UserResponse;
import com.dailycodework.beautifulcare.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @deprecated This controller is deprecated and will be removed in a future
 *             release.
 *             Please use {@link UserManagementController} instead.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
@Tag(name = "Users (Deprecated)", description = "Deprecated API for user management. Use /api/v1/users instead.")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Deprecated
    public APIResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return new APIResponse<>(true, "User created successfully", createdUser);
    }

    @GetMapping
    @Deprecated
    public APIResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new APIResponse<>(true, "Users retrieved successfully", users);
    }

    @GetMapping("/{userId}")
    @Deprecated
    public APIResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return new APIResponse<>(true, "User retrieved successfully", user);
    }

    @PutMapping("/{userId}")
    @Deprecated
    public APIResponse<UserResponse> updateUser(@PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUser(userId, request);
        return new APIResponse<>(true, "User updated successfully", updatedUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Deprecated
    public APIResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new APIResponse<>(true, "User deleted successfully", null);
    }
}
