package com.dailycodework.beautifulcare.mapper;

import com.dailycodework.beautifulcare.dto.request.ServiceCreateRequest;
import com.dailycodework.beautifulcare.dto.request.ServiceUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.entity.Service;
import com.dailycodework.beautifulcare.entity.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceResponse toResponse(Service service) {
        if (service == null) {
            return null;
        }

        ServiceResponse response = new ServiceResponse();
        response.setId(service.getId());
        response.setName(service.getName());
        response.setDescription(service.getDescription());
        response.setPrice(service.getPrice());
        response.setDurationMinutes(service.getDuration());
        response.setImageUrl(service.getImageUrl());
        response.setCreatedAt(service.getCreatedAt());
        response.setUpdatedAt(service.getUpdatedAt());

        if (service.getCategory() != null) {
            response.setCategoryId(service.getCategory().getId());
            response.setCategoryName(service.getCategory().getName());
        }

        return response;
    }

    @Override
    public Service toService(ServiceCreateRequest request, ServiceCategory category) {
        if (request == null) {
            return null;
        }

        Service service = new Service();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDuration(request.getDuration());
        service.setImageUrl(request.getImageUrl());
        service.setCategory(category);

        return service;
    }

    @Override
    public void updateService(Service service, ServiceUpdateRequest request, ServiceCategory category) {
        if (service == null || request == null) {
            return;
        }

        if (request.getName() != null) {
            service.setName(request.getName());
        }

        if (request.getDescription() != null) {
            service.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            service.setPrice(request.getPrice());
        }

        if (request.getDuration() != null) {
            service.setDuration(request.getDuration());
        }

        if (request.getImageUrl() != null) {
            service.setImageUrl(request.getImageUrl());
        }

        if (category != null) {
            service.setCategory(category);
        }
    }
}