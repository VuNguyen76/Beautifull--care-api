package com.dailycodework.beautifulcare.dto.request;

import com.dailycodework.beautifulcare.entity.SkinType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be at least 0")
    private Double price;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    private String imageUrl;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

    private SkinType suitableForSkinType;
}