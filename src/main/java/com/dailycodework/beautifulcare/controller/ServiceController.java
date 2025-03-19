package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.ServiceCreateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<ServiceResponse> createService(@Valid @RequestBody ServiceCreateRequest request) {
        ServiceResponse response = serviceService.createService(request);
        return new APIResponse<>(true, "Service created successfully", response);
    }

    @GetMapping
    public APIResponse<List<ServiceResponse>> getAllServices() {
        List<ServiceResponse> services = serviceService.getAllServices();
        return new APIResponse<>(true, "Services retrieved successfully", services);
    }

    @GetMapping("/{id}")
    public APIResponse<ServiceResponse> getServiceById(@PathVariable String id) {
        ServiceResponse service = serviceService.getServiceById(id);
        return new APIResponse<>(true, "Service retrieved successfully", service);
    }

    @PutMapping("/{id}")
    public APIResponse<ServiceResponse> updateService(
            @PathVariable String id,
            @Valid @RequestBody ServiceCreateRequest request) {
        ServiceResponse updatedService = serviceService.updateService(id, request);
        return new APIResponse<>(true, "Service updated successfully", updatedService);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public APIResponse<Void> deleteService(@PathVariable String id) {
        serviceService.deleteService(id);
        return new APIResponse<>(true, "Service deleted successfully", null);
    }

    @GetMapping("/category/{categoryId}")
    public APIResponse<List<ServiceResponse>> getServicesByCategory(@PathVariable String categoryId) {
        List<ServiceResponse> services = serviceService.getServicesByCategory(categoryId);
        return new APIResponse<>(true, "Services for category retrieved successfully", services);
    }
}