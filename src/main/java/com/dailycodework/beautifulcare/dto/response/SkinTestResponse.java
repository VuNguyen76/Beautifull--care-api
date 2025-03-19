package com.dailycodework.beautifulcare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SkinTestResponse {
    private String id;
    private String name;
    private String description;
    private boolean active;
    private List<SkinTestQuestionResponse> questions;
    private LocalDateTime createdAt;
}