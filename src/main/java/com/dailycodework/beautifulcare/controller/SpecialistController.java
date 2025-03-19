package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.SpecialistCreateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.SpecialistResponse;
import com.dailycodework.beautifulcare.dto.response.WorkScheduleResponse;
import com.dailycodework.beautifulcare.service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialists")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<SpecialistResponse> createSpecialist(@Valid @RequestBody SpecialistCreateRequest request) {
        SpecialistResponse response = specialistService.createSpecialist(request);
        return new APIResponse<>(true, "Specialist created successfully", response);
    }

    @GetMapping
    public APIResponse<List<SpecialistResponse>> getAllSpecialists() {
        List<SpecialistResponse> specialists = specialistService.getAllSpecialists();
        return new APIResponse<>(true, "Specialists retrieved successfully", specialists);
    }

    @GetMapping("/{id}")
    public APIResponse<SpecialistResponse> getSpecialistById(@PathVariable String id) {
        SpecialistResponse specialist = specialistService.getSpecialistById(id);
        return new APIResponse<>(true, "Specialist retrieved successfully", specialist);
    }

    @PutMapping("/{id}")
    public APIResponse<SpecialistResponse> updateSpecialist(
            @PathVariable String id,
            @Valid @RequestBody SpecialistCreateRequest request) {
        SpecialistResponse updatedSpecialist = specialistService.updateSpecialist(id, request);
        return new APIResponse<>(true, "Specialist updated successfully", updatedSpecialist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public APIResponse<Void> deleteSpecialist(@PathVariable String id) {
        specialistService.deleteSpecialist(id);
        return new APIResponse<>(true, "Specialist deleted successfully", null);
    }

    @GetMapping("/{id}/schedule")
    public APIResponse<List<WorkScheduleResponse>> getSpecialistSchedule(@PathVariable String id) {
        List<WorkScheduleResponse> schedule = specialistService.getSpecialistSchedule(id);
        return new APIResponse<>(true, "Specialist schedule retrieved successfully", schedule);
    }

    @GetMapping("/service/{serviceId}")
    public APIResponse<List<SpecialistResponse>> getSpecialistsByService(@PathVariable String serviceId) {
        List<SpecialistResponse> specialists = specialistService.getSpecialistsByService(serviceId);
        return new APIResponse<>(true, "Specialists for service retrieved successfully", specialists);
    }
}