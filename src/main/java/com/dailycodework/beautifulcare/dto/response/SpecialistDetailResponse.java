package com.dailycodework.beautifulcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistDetailResponse {
    private String id;
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dob;
    private String specialization;
    private String bio;
    private String expertise;
    private Integer yearsOfExperience;
    private String avatar;
    private List<String> certifications;
    private List<ServiceResponse> services;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}