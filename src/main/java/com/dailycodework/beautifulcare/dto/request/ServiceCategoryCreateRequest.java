package com.dailycodework.beautifulcare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ServiceCategoryCreateRequest {
    @NotBlank(message = "Category name is required")
    private String name;

    private String description;

    private String imageUrl;
}