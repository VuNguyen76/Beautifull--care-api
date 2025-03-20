package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.*;
import com.dailycodework.beautifulcare.dto.response.*;
import com.dailycodework.beautifulcare.service.AuthenticationService;
import com.dailycodework.beautifulcare.service.CustomerService;
import com.dailycodework.beautifulcare.service.UserService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "API for managing users, customers, and authentication")
public class UserManagementController {
    private final UserService userService;
    private final CustomerService customerService;
    private final AuthenticationService authenticationService;

    // Authentication endpoints
    @PostMapping("/auth/login")
    @Operation(summary = "Authenticate user and get token")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Authentication successful",
                result);
    }

    @PostMapping("/auth/validate")
    @Operation(summary = "Validate token")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<IntrospectResponse> validateToken(
            @Valid @RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Token validation successful",
                result);
    }

    // Generic User endpoints
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request) {
        UserResponse createdUser = userService.createUser(request);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "User created successfully",
                createdUser);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Users retrieved successfully",
                users);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<UserResponse> getUserById(
            @PathVariable("userId") String userId) {
        UserResponse user = userService.getUserById(userId);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "User retrieved successfully",
                user);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request) {
        UserResponse updatedUser = userService.updateUser(userId, request);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "User updated successfully",
                updatedUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "User deleted successfully", null);
    }

    // Customer-specific endpoints
    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new customer")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Customer created successfully",
                response);
    }

    @GetMapping("/customers")
    @Operation(summary = "Get all customers")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Customers retrieved successfully",
                customers);
    }

    @GetMapping("/customers/{customerId}")
    @Operation(summary = "Get customer by ID")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<CustomerResponse> getCustomerById(
            @PathVariable String customerId) {
        CustomerResponse customer = customerService.getCustomerById(customerId);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Customer retrieved successfully",
                customer);
    }

    @PutMapping("/customers/{customerId}")
    @Operation(summary = "Update customer")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<CustomerResponse> updateCustomer(
            @PathVariable String customerId,
            @Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(customerId, request);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Customer updated successfully",
                updatedCustomer);
    }

    @DeleteMapping("/customers/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete customer")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<Void> deleteCustomer(
            @PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true, "Customer deleted successfully",
                null);
    }

    @GetMapping("/customers/{customerId}/bookings")
    @Operation(summary = "Get customer bookings")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<List<BookingResponse>> getCustomerBookings(
            @PathVariable String customerId) {
        List<BookingResponse> bookings = customerService.getCustomerBookings(customerId);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true,
                "Customer bookings retrieved successfully", bookings);
    }

    @GetMapping("/customers/{customerId}/skin-test-results")
    @Operation(summary = "Get customer skin test results")
    public com.dailycodework.beautifulcare.dto.response.APIResponse<List<SkinTestResultResponse>> getCustomerSkinTestResults(
            @PathVariable String customerId) {
        List<SkinTestResultResponse> results = customerService.getCustomerSkinTestResults(customerId);
        return new com.dailycodework.beautifulcare.dto.response.APIResponse<>(true,
                "Customer skin test results retrieved successfully", results);
    }
}