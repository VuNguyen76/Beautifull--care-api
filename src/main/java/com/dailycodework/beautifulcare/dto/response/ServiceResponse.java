package com.dailycodework.beautifulcare.dto.response;

import com.dailycodework.beautifulcare.entity.SkinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private int durationMinutes;
    private String imageUrl;
    private String categoryId;
    private String categoryName;
    private SkinType suitableForSkinType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}