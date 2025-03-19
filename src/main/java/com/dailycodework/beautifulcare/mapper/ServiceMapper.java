package com.dailycodework.beautifulcare.mapper;

import com.dailycodework.beautifulcare.dto.request.ServiceCreateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.entity.ServiceCategory;
import com.dailycodework.beautifulcare.entity.ServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    public ServiceEntity toService(ServiceCreateRequest request, ServiceCategory category) {
        ServiceEntity service = new ServiceEntity();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setImageUrl(request.getImageUrl());
        service.setCategory(category);
        service.setSuitableForSkinType(request.getSuitableForSkinType());
        return service;
    }

    public ServiceResponse toServiceResponse(ServiceEntity service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .durationMinutes(service.getDurationMinutes())
                .imageUrl(service.getImageUrl())
                .categoryId(service.getCategory() != null ? service.getCategory().getId() : null)
                .categoryName(service.getCategory() != null ? service.getCategory().getName() : null)
                .suitableForSkinType(service.getSuitableForSkinType())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public void updateService(ServiceEntity service, ServiceCreateRequest request, ServiceCategory category) {
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        service.setDurationMinutes(request.getDurationMinutes());
        service.setImageUrl(request.getImageUrl());
        service.setCategory(category);
        service.setSuitableForSkinType(request.getSuitableForSkinType());
    }
}