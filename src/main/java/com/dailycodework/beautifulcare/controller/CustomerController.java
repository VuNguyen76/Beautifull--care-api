package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.CustomerCreateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.BookingResponse;
import com.dailycodework.beautifulcare.dto.response.CustomerResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResultResponse;
import com.dailycodework.beautifulcare.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new APIResponse<>(true, "Customer created successfully", response);
    }

    @GetMapping
    public APIResponse<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return new APIResponse<>(true, "Customers retrieved successfully", customers);
    }

    @GetMapping("/{id}")
    public APIResponse<CustomerResponse> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return new APIResponse<>(true, "Customer retrieved successfully", customer);
    }

    @PutMapping("/{id}")
    public APIResponse<CustomerResponse> updateCustomer(
            @PathVariable String id,
            @Valid @RequestBody CustomerCreateRequest request) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, request);
        return new APIResponse<>(true, "Customer updated successfully", updatedCustomer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public APIResponse<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return new APIResponse<>(true, "Customer deleted successfully", null);
    }

    @GetMapping("/{id}/bookings")
    public APIResponse<List<BookingResponse>> getCustomerBookings(@PathVariable String id) {
        List<BookingResponse> bookings = customerService.getCustomerBookings(id);
        return new APIResponse<>(true, "Customer bookings retrieved successfully", bookings);
    }

    @GetMapping("/{id}/skin-test-results")
    public APIResponse<List<SkinTestResultResponse>> getCustomerSkinTestResults(@PathVariable String id) {
        List<SkinTestResultResponse> results = customerService.getCustomerSkinTestResults(id);
        return new APIResponse<>(true, "Customer skin test results retrieved successfully", results);
    }
}