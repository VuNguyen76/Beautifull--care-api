package com.dailycodework.beautifulcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkinTestResultResponse {
    private String id;
    private String testId;
    private String testName;
    private String customerId;
    private String customerName;
    private String skinType;
    private Map<String, String> answers;
    private String recommendation;
    private LocalDateTime createdAt;
}