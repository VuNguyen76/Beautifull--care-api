package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.ServiceCategoryCreateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.ServiceCategoryResponse;
import com.dailycodework.beautifulcare.service.ServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {
    private final ServiceCategoryService serviceCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<ServiceCategoryResponse> createServiceCategory(
            @Valid @RequestBody ServiceCategoryCreateRequest request) {
        ServiceCategoryResponse response = serviceCategoryService.createServiceCategory(request);
        return new APIResponse<>(true, "Service category created successfully", response);
    }

    @GetMapping
    public APIResponse<List<ServiceCategoryResponse>> getAllServiceCategories() {
        List<ServiceCategoryResponse> categories = serviceCategoryService.getAllServiceCategories();
        return new APIResponse<>(true, "Service categories retrieved successfully", categories);
    }

    @GetMapping("/{id}")
    public APIResponse<ServiceCategoryResponse> getServiceCategoryById(@PathVariable String id) {
        ServiceCategoryResponse category = serviceCategoryService.getServiceCategoryById(id);
        return new APIResponse<>(true, "Service category retrieved successfully", category);
    }

    @PutMapping("/{id}")
    public APIResponse<ServiceCategoryResponse> updateServiceCategory(
            @PathVariable String id,
            @Valid @RequestBody ServiceCategoryCreateRequest request) {
        ServiceCategoryResponse updatedCategory = serviceCategoryService.updateServiceCategory(id, request);
        return new APIResponse<>(true, "Service category updated successfully", updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public APIResponse<Void> deleteServiceCategory(@PathVariable String id) {
        serviceCategoryService.deleteServiceCategory(id);
        return new APIResponse<>(true, "Service category deleted successfully", null);
    }
}