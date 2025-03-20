package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.CustomerCreateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.BookingResponse;
import com.dailycodework.beautifulcare.dto.response.CustomerResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResultResponse;
import com.dailycodework.beautifulcare.service.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @deprecated This controller is deprecated and will be removed in a future
 *             release.
 *             Please use {@link UserManagementController} instead with
 *             endpoints /api/v1/users/customers/*
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
@Tag(name = "Customers (Deprecated)", description = "Deprecated API for customer management. Use /api/v1/users/customers/* instead.")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Deprecated
    public APIResponse<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new APIResponse<>(true, "Customer created successfully", response);
    }

    @GetMapping
    @Deprecated
    public APIResponse<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return new APIResponse<>(true, "Customers retrieved successfully", customers);
    }

    @GetMapping("/{id}")
    @Deprecated
    public APIResponse<CustomerResponse> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return new APIResponse<>(true, "Customer retrieved successfully", customer);
    }

    @PutMapping("/{id}")
    @Deprecated
    public APIResponse<CustomerResponse> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, request);
        return new APIResponse<>(true, "Customer updated successfully", updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Deprecated
    public APIResponse<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return new APIResponse<>(true, "Customer deleted successfully", null);
    }

    @GetMapping("/{id}/bookings")
    @Deprecated
    public APIResponse<List<BookingResponse>> getCustomerBookings(@PathVariable String id) {
        List<BookingResponse> bookings = customerService.getCustomerBookings(id);
        return new APIResponse<>(true, "Customer bookings retrieved successfully", bookings);
    }

    @GetMapping("/{id}/skin-test-results")
    @Deprecated
    public APIResponse<List<SkinTestResultResponse>> getCustomerSkinTestResults(@PathVariable String id) {
        List<SkinTestResultResponse> results = customerService.getCustomerSkinTestResults(id);
        return new APIResponse<>(true, "Customer skin test results retrieved successfully", results);
    }
}