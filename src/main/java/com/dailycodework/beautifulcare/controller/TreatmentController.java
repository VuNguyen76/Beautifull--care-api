package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.TreatmentCreateRequest;
import com.dailycodework.beautifulcare.dto.request.TreatmentResultRequest;
import com.dailycodework.beautifulcare.dto.request.TreatmentUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.TreatmentResponse;
import com.dailycodework.beautifulcare.dto.response.TreatmentResultResponse;
import com.dailycodework.beautifulcare.service.TreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing treatment-related operations.
 * Provides APIs to create, retrieve, update and manage treatments and their
 * results.
 */
@RestController
@RequestMapping("/api/v1/treatments")
@RequiredArgsConstructor
public class TreatmentController {
    private final TreatmentService treatmentService;

    /**
     * Create a new treatment
     * 
     * @param request Treatment creation request data
     * @return TreatmentResponse with created treatment details
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<TreatmentResponse> createTreatment(@Valid @RequestBody TreatmentCreateRequest request) {
        TreatmentResponse treatment = treatmentService.createTreatment(request);
        return new APIResponse<>(true, "Treatment created successfully", treatment);
    }

    /**
     * Get all treatments with optional booking ID filter
     * 
     * @param bookingId    Optional booking ID filter
     * @param specialistId Optional specialist ID filter
     * @return List of treatment responses
     */
    @GetMapping
    public APIResponse<List<TreatmentResponse>> getAllTreatments(
            @RequestParam(required = false) String bookingId,
            @RequestParam(required = false) String specialistId) {
        List<TreatmentResponse> treatments = treatmentService.getAllTreatments(bookingId, specialistId);
        return new APIResponse<>(true, "Treatments retrieved successfully", treatments);
    }

    /**
     * Get treatment by ID
     * 
     * @param id Treatment ID
     * @return Treatment details
     */
    @GetMapping("/{id}")
    public APIResponse<TreatmentResponse> getTreatmentById(@PathVariable String id) {
        TreatmentResponse treatment = treatmentService.getTreatmentById(id);
        return new APIResponse<>(true, "Treatment retrieved successfully", treatment);
    }

    /**
     * Update a treatment
     * 
     * @param id      Treatment ID
     * @param request Update data
     * @return Updated treatment details
     */
    @PutMapping("/{id}")
    public APIResponse<TreatmentResponse> updateTreatment(
            @PathVariable String id, @Valid @RequestBody TreatmentUpdateRequest request) {
        TreatmentResponse updatedTreatment = treatmentService.updateTreatment(id, request);
        return new APIResponse<>(true, "Treatment updated successfully", updatedTreatment);
    }

    /**
     * Start a treatment
     * 
     * @param id Treatment ID
     * @return Updated treatment details
     */
    @PutMapping("/{id}/start")
    public APIResponse<TreatmentResponse> startTreatment(@PathVariable String id) {
        TreatmentResponse updatedTreatment = treatmentService.startTreatment(id);
        return new APIResponse<>(true, "Treatment started successfully", updatedTreatment);
    }

    /**
     * Complete a treatment
     * 
     * @param id Treatment ID
     * @return Updated treatment details
     */
    @PutMapping("/{id}/complete")
    public APIResponse<TreatmentResponse> completeTreatment(@PathVariable String id) {
        TreatmentResponse updatedTreatment = treatmentService.completeTreatment(id);
        return new APIResponse<>(true, "Treatment completed successfully", updatedTreatment);
    }

    /**
     * Add a treatment result
     * 
     * @param id      Treatment ID
     * @param request Treatment result data
     * @return Treatment result details
     */
    @PostMapping("/{id}/results")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<TreatmentResultResponse> addTreatmentResult(
            @PathVariable String id, @Valid @RequestBody TreatmentResultRequest request) {
        TreatmentResultResponse result = treatmentService.addTreatmentResult(id, request);
        return new APIResponse<>(true, "Treatment result added successfully", result);
    }

    /**
     * Get treatment results by treatment ID
     * 
     * @param id Treatment ID
     * @return Treatment result details
     */
    @GetMapping("/{id}/results")
    public APIResponse<TreatmentResultResponse> getTreatmentResult(@PathVariable String id) {
        TreatmentResultResponse result = treatmentService.getTreatmentResult(id);
        return new APIResponse<>(true, "Treatment result retrieved successfully", result);
    }
}