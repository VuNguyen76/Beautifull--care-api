package com.dailycodework.beautifulcare.mapper;

import com.dailycodework.beautifulcare.dto.request.SpecialistCreateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.dto.response.SpecialistResponse;
import com.dailycodework.beautifulcare.entity.ServiceEntity;
import com.dailycodework.beautifulcare.entity.Specialist;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SpecialistMapper {

    public Specialist toSpecialist(SpecialistCreateRequest request) {
        Specialist specialist = new Specialist();
        specialist.setBio(request.getBio());
        specialist.setExpertise(request.getExpertise());
        specialist.setYearsOfExperience(request.getYearsOfExperience());
        specialist.setCertifications(request.getCertifications());
        return specialist;
    }

    public SpecialistResponse toSpecialistResponse(Specialist specialist) {
        SpecialistResponse response = new SpecialistResponse();
        response.setId(specialist.getId());
        response.setUsername(specialist.getUsername());
        response.setEmail(specialist.getEmail());
        response.setPhone(specialist.getPhone());
        response.setFirstName(specialist.getFirstName());
        response.setLastName(specialist.getLastName());
        response.setDob(specialist.getDob());
        response.setAvatar(specialist.getAvatar());
        response.setRole(specialist.getRole());
        response.setActive(specialist.isActive());
        response.setCreatedAt(specialist.getCreatedAt());
        response.setBio(specialist.getBio());
        response.setExpertise(specialist.getExpertise());
        response.setYearsOfExperience(specialist.getYearsOfExperience());
        response.setCertifications(specialist.getCertifications());

        // Map services
        response.setServices(specialist.getServices().stream()
                .map(this::toServiceResponse)
                .collect(Collectors.toSet()));

        return response;
    }

    private ServiceResponse toServiceResponse(ServiceEntity service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .durationMinutes(service.getDurationMinutes())
                .imageUrl(service.getImageUrl())
                .categoryId(service.getCategory().getId())
                .categoryName(service.getCategory().getName())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public void updateSpecialist(Specialist specialist, SpecialistCreateRequest request) {
        specialist.setBio(request.getBio());
        specialist.setExpertise(request.getExpertise());
        specialist.setYearsOfExperience(request.getYearsOfExperience());
        specialist.setCertifications(request.getCertifications());
    }
}