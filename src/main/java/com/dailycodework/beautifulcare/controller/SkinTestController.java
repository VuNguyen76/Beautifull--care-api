package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.SkinTestCreateRequest;
import com.dailycodework.beautifulcare.dto.request.SkinTestResultRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.ServiceRecommendationResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResultResponse;
import com.dailycodework.beautifulcare.service.SkinTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing skin test-related operations.
 * Provides APIs to create and manage skin tests, process test results, and get
 * recommendations.
 */
@RestController
@RequestMapping("/api/v1/skin-tests")
@RequiredArgsConstructor
public class SkinTestController {
    private final SkinTestService skinTestService;

    /**
     * Create a new skin test
     * 
     * @param request Skin test creation request data
     * @return SkinTestResponse with created skin test details
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<SkinTestResponse> createSkinTest(@Valid @RequestBody SkinTestCreateRequest request) {
        SkinTestResponse skinTest = skinTestService.createSkinTest(request);
        return new APIResponse<>(true, "Skin test created successfully", skinTest);
    }

    /**
     * Get all skin tests
     * 
     * @param active Optional active status filter
     * @return List of skin test responses
     */
    @GetMapping
    public APIResponse<List<SkinTestResponse>> getAllSkinTests(
            @RequestParam(required = false) Boolean active) {
        List<SkinTestResponse> skinTests = skinTestService.getAllSkinTests(active);
        return new APIResponse<>(true, "Skin tests retrieved successfully", skinTests);
    }

    /**
     * Get skin test by ID
     * 
     * @param id Skin test ID
     * @return Skin test details
     */
    @GetMapping("/{id}")
    public APIResponse<SkinTestResponse> getSkinTestById(@PathVariable String id) {
        SkinTestResponse skinTest = skinTestService.getSkinTestById(id);
        return new APIResponse<>(true, "Skin test retrieved successfully", skinTest);
    }

    /**
     * Save skin test result
     * 
     * @param request Skin test result request data
     * @return Skin test result response
     */
    @PostMapping("/results")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<SkinTestResultResponse> saveSkinTestResult(
            @Valid @RequestBody SkinTestResultRequest request) {
        SkinTestResultResponse result = skinTestService.saveSkinTestResult(request);
        return new APIResponse<>(true, "Skin test result saved successfully", result);
    }

    /**
     * Get skin test result by ID
     * 
     * @param id Skin test result ID
     * @return Skin test result details
     */
    @GetMapping("/results/{id}")
    public APIResponse<SkinTestResultResponse> getSkinTestResultById(@PathVariable String id) {
        SkinTestResultResponse result = skinTestService.getSkinTestResultById(id);
        return new APIResponse<>(true, "Skin test result retrieved successfully", result);
    }

    /**
     * Get service recommendations based on skin test result
     * 
     * @param resultId Skin test result ID
     * @return List of service recommendations
     */
    @GetMapping("/recommendations")
    public APIResponse<List<ServiceRecommendationResponse>> getRecommendations(
            @RequestParam String resultId) {
        List<ServiceRecommendationResponse> recommendations = skinTestService.getServiceRecommendations(resultId);
        return new APIResponse<>(true, "Service recommendations retrieved successfully", recommendations);
    }

    /**
     * Get all skin test results for a customer
     * 
     * @param customerId Customer ID
     * @return List of skin test result responses
     */
    @GetMapping("/results/customer/{customerId}")
    public APIResponse<List<SkinTestResultResponse>> getCustomerSkinTestResults(
            @PathVariable String customerId) {
        List<SkinTestResultResponse> results = skinTestService.getCustomerSkinTestResults(customerId);
        return new APIResponse<>(true, "Customer skin test results retrieved successfully", results);
    }
}