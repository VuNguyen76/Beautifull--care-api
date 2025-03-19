package com.dailycodework.beautifulcare.service;

import com.dailycodework.beautifulcare.dto.request.ServiceCreateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;

import java.util.List;

public interface ServiceService {
    ServiceResponse createService(ServiceCreateRequest request);

    List<ServiceResponse> getAllServices();

    ServiceResponse getServiceById(String id);

    ServiceResponse updateService(String id, ServiceCreateRequest request);

    void deleteService(String id);

    List<ServiceResponse> getServicesByCategory(String categoryId);
}