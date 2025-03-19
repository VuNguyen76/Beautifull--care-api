package com.dailycodework.beautifulcare.service.impl;

import com.dailycodework.beautifulcare.dto.request.SkinTestCreateRequest;
import com.dailycodework.beautifulcare.dto.request.SkinTestResultRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceRecommendationResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResponse;
import com.dailycodework.beautifulcare.dto.response.SkinTestResultResponse;
import com.dailycodework.beautifulcare.entity.ServiceEntity;
import com.dailycodework.beautifulcare.entity.SkinTest;
import com.dailycodework.beautifulcare.entity.SkinTestResult;
import com.dailycodework.beautifulcare.entity.SkinType;
import com.dailycodework.beautifulcare.entity.User;
import com.dailycodework.beautifulcare.entity.Customer;
import com.dailycodework.beautifulcare.exception.AppException;
import com.dailycodework.beautifulcare.exception.ErrorCode;
import com.dailycodework.beautifulcare.repository.ServiceRepository;
import com.dailycodework.beautifulcare.repository.SkinTestRepository;
import com.dailycodework.beautifulcare.repository.SkinTestResultRepository;
import com.dailycodework.beautifulcare.repository.UserRepository;
import com.dailycodework.beautifulcare.service.SkinTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SkinTestServiceImpl implements SkinTestService {

    private final SkinTestRepository skinTestRepository;
    private final SkinTestResultRepository skinTestResultRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Override
    @Transactional
    public SkinTestResponse createSkinTest(SkinTestCreateRequest request) {
        // Implementation for creating a skin test
        SkinTest skinTest = new SkinTest();
        skinTest.setName(request.getName());
        skinTest.setDescription(request.getDescription());
        skinTest.setActive(true);
        skinTest.setCreatedAt(LocalDateTime.now());

        // More implementation...

        SkinTest savedSkinTest = skinTestRepository.save(skinTest);

        return mapToSkinTestResponse(savedSkinTest);
    }

    @Override
    public List<SkinTestResponse> getAllSkinTests(Boolean active) {
        List<SkinTest> skinTests;

        if (active != null) {
            skinTests = skinTestRepository.findByActive(active);
        } else {
            skinTests = skinTestRepository.findAll();
        }

        return skinTests.stream()
                .map(this::mapToSkinTestResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SkinTestResponse getSkinTestById(String id) {
        SkinTest skinTest = skinTestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKIN_TEST_NOT_FOUND));

        return mapToSkinTestResponse(skinTest);
    }

    @Override
    @Transactional
    public SkinTestResultResponse saveSkinTestResult(SkinTestResultRequest request) {
        // Find skin test
        SkinTest skinTest = skinTestRepository.findById(request.getSkinTestId())
                .orElseThrow(() -> new AppException(ErrorCode.SKIN_TEST_NOT_FOUND));

        // Find customer
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        // Process answers and calculate skin type
        // This is a simplified example; in a real system, this would be more complex
        Map<SkinType, Integer> skinTypeScores = new HashMap<>();

        // Process answers...

        // Find the skin type with the highest score
        SkinType determinedSkinType = SkinType.NORMAL; // Default

        // Create and save skin test result
        SkinTestResult result = new SkinTestResult();
        result.setSkinTest(skinTest);
        result.setCustomer((Customer) customer);
        result.setResultSkinType(determinedSkinType);
        result.setAnswers(request.getAnswers());
        result.setCreatedAt(LocalDateTime.now());

        SkinTestResult savedResult = skinTestResultRepository.save(result);

        return mapToSkinTestResultResponse(savedResult);
    }

    @Override
    public SkinTestResultResponse getSkinTestResultById(String id) {
        SkinTestResult result = skinTestResultRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKIN_TEST_RESULT_NOT_FOUND));

        return mapToSkinTestResultResponse(result);
    }

    @Override
    public List<ServiceRecommendationResponse> getServiceRecommendations(String resultId) {
        // Find skin test result
        SkinTestResult result = skinTestResultRepository.findById(resultId)
                .orElseThrow(() -> new AppException(ErrorCode.SKIN_TEST_RESULT_NOT_FOUND));

        // Get determined skin type
        SkinType skinType = result.getResultSkinType();

        // Find services suitable for this skin type
        List<ServiceEntity> suitableServices = serviceRepository.findBySuitableForSkinType(skinType);

        // Map to recommendation responses with match scores
        return suitableServices.stream()
                .map(service -> {
                    // Calculate match score based on how well the service matches the skin type
                    int matchScore = calculateMatchScore(service, skinType);

                    return ServiceRecommendationResponse.builder()
                            .serviceId(service.getId())
                            .serviceName(service.getName())
                            .description(service.getDescription())
                            .price(service.getPrice())
                            .imageUrl(service.getImageUrl())
                            .categoryName(service.getCategory().getName())
                            .matchScore(matchScore)
                            .recommendationReason(generateRecommendationReason(service, skinType))
                            .build();
                })
                .sorted(Comparator.comparing(ServiceRecommendationResponse::getMatchScore).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<SkinTestResultResponse> getCustomerSkinTestResults(String customerId) {
        List<SkinTestResult> results = skinTestResultRepository.findByCustomerId(customerId);

        return results.stream()
                .map(this::mapToSkinTestResultResponse)
                .collect(Collectors.toList());
    }

    // Helper methods
    private SkinTestResponse mapToSkinTestResponse(SkinTest skinTest) {
        // Implementation for mapping SkinTest to SkinTestResponse
        return SkinTestResponse.builder()
                .id(skinTest.getId())
                .name(skinTest.getName())
                .description(skinTest.getDescription())
                .active(skinTest.isActive())
                .createdAt(skinTest.getCreatedAt())
                // Map other fields...
                .build();
    }

    private SkinTestResultResponse mapToSkinTestResultResponse(SkinTestResult result) {
        return SkinTestResultResponse.builder()
                .id(result.getId())
                .testId(result.getSkinTest().getId())
                .testName(result.getSkinTest().getName())
                .customerId(result.getCustomer().getId())
                .customerName(result.getCustomer().getFirstName() + " " + result.getCustomer().getLastName())
                .skinType(result.getResultSkinType().toString())
                .answers(result.getAnswers())
                .recommendation(result.getRecommendation())
                .createdAt(result.getCreatedAt())
                .build();
    }

    private int calculateMatchScore(ServiceEntity service, SkinType skinType) {
        // Nếu loại da và dịch vụ hoàn toàn khớp
        if (service.getSuitableForSkinType() == skinType) {
            return 100;
        }

        // Tính điểm dựa trên mức độ tương thích
        // Ví dụ: da hỗn hợp có thể phù hợp với các dịch vụ cho da dầu hoặc da khô ở mức
        // thấp hơn
        if (service.getSuitableForSkinType() == null) {
            return 30; // Default score for services without skin type specification
        }

        switch (skinType) {
            case OILY:
                return service.getSuitableForSkinType() == SkinType.COMBINATION ? 70 : 30;
            case DRY:
                return service.getSuitableForSkinType() == SkinType.COMBINATION ? 70 : 30;
            case COMBINATION:
                return (service.getSuitableForSkinType() == SkinType.OILY ||
                        service.getSuitableForSkinType() == SkinType.DRY) ? 60 : 30;
            case SENSITIVE:
                // Các dịch vụ dành cho da nhạy cảm thường chỉ phù hợp với da nhạy cảm
                return 30;
            case NORMAL:
                // Da thường có thể phù hợp với nhiều loại dịch vụ khác
                return 50;
            default:
                return 30;
        }
    }

    private String generateRecommendationReason(ServiceEntity service, SkinType skinType) {
        if (service.getSuitableForSkinType() == null) {
            return "Dịch vụ này có thể phù hợp với nhiều loại da khác nhau.";
        }

        if (service.getSuitableForSkinType() == skinType) {
            return String.format("Dịch vụ này được thiết kế đặc biệt cho làn da %s của bạn.",
                    skinType.name().toLowerCase());
        } else {
            return String.format("Dịch vụ này có thể phù hợp với làn da %s của bạn với một số điều chỉnh.",
                    skinType.name().toLowerCase());
        }
    }
}