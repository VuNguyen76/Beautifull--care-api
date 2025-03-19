package com.dailycodework.beautifulcare.mapper;

import com.dailycodework.beautifulcare.dto.response.ServiceCategoryResponse;
import com.dailycodework.beautifulcare.entity.ServiceCategory;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryMapper {

    public ServiceCategoryResponse toServiceCategoryResponse(ServiceCategory serviceCategory) {
        if (serviceCategory == null) {
            return null;
        }

        return ServiceCategoryResponse.builder()
                .id(serviceCategory.getId())
                .name(serviceCategory.getName())
                .description(serviceCategory.getDescription())
                .imageUrl(serviceCategory.getImageUrl())
                .createdAt(serviceCategory.getCreatedAt())
                .updatedAt(serviceCategory.getUpdatedAt())
                .build();
    }
}