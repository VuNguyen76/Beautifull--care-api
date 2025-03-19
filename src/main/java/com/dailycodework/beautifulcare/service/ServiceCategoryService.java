package com.dailycodework.beautifulcare.service;

import com.dailycodework.beautifulcare.dto.request.ServiceCategoryCreateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceCategoryResponse;

import java.util.List;

public interface ServiceCategoryService {
    ServiceCategoryResponse createServiceCategory(ServiceCategoryCreateRequest request);

    List<ServiceCategoryResponse> getAllServiceCategories();

    ServiceCategoryResponse getServiceCategoryById(String id);

    ServiceCategoryResponse updateServiceCategory(String id, ServiceCategoryCreateRequest request);

    void deleteServiceCategory(String id);
}