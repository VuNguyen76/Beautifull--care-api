package com.dailycodework.beautifulcare.service.impl;

import com.dailycodework.beautifulcare.dto.request.TreatmentCreateRequest;
import com.dailycodework.beautifulcare.dto.request.TreatmentResultRequest;
import com.dailycodework.beautifulcare.dto.request.TreatmentUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.ServiceResponse;
import com.dailycodework.beautifulcare.dto.response.TreatmentResponse;
import com.dailycodework.beautifulcare.dto.response.TreatmentResultResponse;
import com.dailycodework.beautifulcare.entity.Booking;
import com.dailycodework.beautifulcare.entity.ServiceEntity;
import com.dailycodework.beautifulcare.entity.Treatment;
import com.dailycodework.beautifulcare.entity.TreatmentResult;
import com.dailycodework.beautifulcare.entity.User;
import com.dailycodework.beautifulcare.entity.enums.TreatmentStatus;
import com.dailycodework.beautifulcare.exception.ResourceNotFoundException;
import com.dailycodework.beautifulcare.repository.BookingRepository;
import com.dailycodework.beautifulcare.repository.ServiceRepository;
import com.dailycodework.beautifulcare.repository.TreatmentRepository;
import com.dailycodework.beautifulcare.repository.TreatmentResultRepository;
import com.dailycodework.beautifulcare.repository.UserRepository;
import com.dailycodework.beautifulcare.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the TreatmentService interface.
 */
@Service
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {

        private final TreatmentRepository treatmentRepository;
        private final TreatmentResultRepository treatmentResultRepository;
        private final BookingRepository bookingRepository;
        private final UserRepository userRepository;
        private final ServiceRepository serviceRepository;

        @Override
        @Transactional
        public TreatmentResponse createTreatment(TreatmentCreateRequest request) {
                // Fetch the booking
                Booking booking = bookingRepository.findById(request.getBookingId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Booking not found with ID: " + request.getBookingId()));

                // Fetch the specialist
                User specialist = userRepository.findById(request.getSpecialistId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Specialist not found with ID: " + request.getSpecialistId()));

                // Fetch the services
                List<ServiceEntity> services = request.getServiceIds().stream()
                                .map(serviceId -> serviceRepository.findById(serviceId)
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Service not found with ID: " + serviceId)))
                                .collect(Collectors.toList());

                // Create and save the treatment
                Treatment treatment = Treatment.builder()
                                .booking(booking)
                                .specialist(specialist)
                                .note(request.getNote())
                                .status(TreatmentStatus.SCHEDULED)
                                .services(services)
                                .build();

                Treatment savedTreatment = treatmentRepository.save(treatment);

                // Map and return the response
                return mapToTreatmentResponse(savedTreatment);
        }

        @Override
        public List<TreatmentResponse> getAllTreatments(String bookingId, String specialistId) {
                List<Treatment> treatments = new ArrayList<>();

                if (bookingId != null && specialistId != null) {
                        treatments = treatmentRepository.findAll().stream()
                                        .filter(t -> t.getBooking().getId().equals(bookingId)
                                                        && t.getSpecialist().getId().equals(specialistId))
                                        .collect(Collectors.toList());
                } else if (bookingId != null) {
                        treatments = treatmentRepository.findByBookingId(bookingId);
                } else if (specialistId != null) {
                        treatments = treatmentRepository.findBySpecialistId(specialistId);
                } else {
                        treatments = treatmentRepository.findAll();
                }

                return treatments.stream()
                                .map(this::mapToTreatmentResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public TreatmentResponse getTreatmentById(String id) {
                Treatment treatment = treatmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with ID: " + id));

                return mapToTreatmentResponse(treatment);
        }

        @Override
        @Transactional
        public TreatmentResponse updateTreatment(String id, TreatmentUpdateRequest request) {
                Treatment treatment = treatmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with ID: " + id));

                // Update specialist if provided
                if (request.getSpecialistId() != null) {
                        User specialist = userRepository.findById(request.getSpecialistId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Specialist not found with ID: " + request.getSpecialistId()));
                        treatment.setSpecialist(specialist);
                }

                // Update note if provided
                if (request.getNote() != null) {
                        treatment.setNote(request.getNote());
                }

                // Update status if provided
                if (request.getStatus() != null) {
                        treatment.setStatus(request.getStatus());
                }

                // Update services if provided
                if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
                        List<ServiceEntity> services = request.getServiceIds().stream()
                                        .map(serviceId -> serviceRepository.findById(serviceId)
                                                        .orElseThrow(
                                                                        () -> new ResourceNotFoundException(
                                                                                        "Service not found with ID: "
                                                                                                        + serviceId)))
                                        .collect(Collectors.toList());
                        treatment.setServices(services);
                }

                Treatment updatedTreatment = treatmentRepository.save(treatment);

                return mapToTreatmentResponse(updatedTreatment);
        }

        @Override
        @Transactional
        public TreatmentResponse startTreatment(String id) {
                Treatment treatment = treatmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with ID: " + id));

                treatment.start();
                Treatment updatedTreatment = treatmentRepository.save(treatment);

                return mapToTreatmentResponse(updatedTreatment);
        }

        @Override
        @Transactional
        public TreatmentResponse completeTreatment(String id) {
                Treatment treatment = treatmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with ID: " + id));

                treatment.complete();
                Treatment updatedTreatment = treatmentRepository.save(treatment);

                return mapToTreatmentResponse(updatedTreatment);
        }

        @Override
        @Transactional
        public TreatmentResultResponse addTreatmentResult(String id, TreatmentResultRequest request) {
                Treatment treatment = treatmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with ID: " + id));

                // Check if result already exists
                if (treatment.hasResult()) {
                        throw new IllegalStateException("Treatment already has a result");
                }

                // Create and save the treatment result
                TreatmentResult result = TreatmentResult.builder()
                                .treatment(treatment)
                                .description(request.getDescription())
                                .recommendations(request.getRecommendations())
                                .imageUrls(request.getImageUrls())
                                .productRecommendations(request.getProductRecommendations())
                                .build();

                TreatmentResult savedResult = treatmentResultRepository.save(result);

                return mapToTreatmentResultResponse(savedResult);
        }

        @Override
        public TreatmentResultResponse getTreatmentResult(String id) {
                Treatment treatment = treatmentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with ID: " + id));

                TreatmentResult result = treatmentResultRepository.findByTreatmentId(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Treatment result not found for treatment ID: " + id));

                return mapToTreatmentResultResponse(result);
        }

        /**
         * Map Treatment entity to TreatmentResponse DTO
         * 
         * @param treatment The treatment entity
         * @return TreatmentResponse DTO
         */
        private TreatmentResponse mapToTreatmentResponse(Treatment treatment) {
                // Map services to service responses
                List<ServiceResponse> serviceResponses = treatment.getServices().stream()
                                .map(service -> ServiceResponse.builder()
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
                                                .build())
                                .collect(Collectors.toList());

                return TreatmentResponse.builder()
                                .id(treatment.getId())
                                .bookingId(treatment.getBooking().getId())
                                .customerId(treatment.getBooking().getCustomer().getId())
                                .customerName(treatment.getBooking().getCustomer().getFirstName() + " "
                                                + treatment.getBooking().getCustomer().getLastName())
                                .specialistId(treatment.getSpecialist().getId())
                                .specialistName(
                                                treatment.getSpecialist().getFirstName() + " "
                                                                + treatment.getSpecialist().getLastName())
                                .createdAt(treatment.getCreatedAt())
                                .startedAt(treatment.getStartedAt())
                                .completedAt(treatment.getCompletedAt())
                                .status(treatment.getStatus())
                                .note(treatment.getNote())
                                .services(serviceResponses)
                                .hasResults(treatment.hasResult())
                                .build();
        }

        /**
         * Map TreatmentResult entity to TreatmentResultResponse DTO
         * 
         * @param result The treatment result entity
         * @return TreatmentResultResponse DTO
         */
        private TreatmentResultResponse mapToTreatmentResultResponse(TreatmentResult result) {
                return TreatmentResultResponse.builder()
                                .id(result.getId())
                                .treatmentId(result.getTreatment().getId())
                                .specialistId(result.getTreatment().getSpecialist().getId())
                                .specialistName(result.getTreatment().getSpecialist().getFirstName() + " "
                                                + result.getTreatment().getSpecialist().getLastName())
                                .createdAt(result.getCreatedAt())
                                .description(result.getDescription())
                                .recommendations(result.getRecommendations())
                                .imageUrls(result.getImageUrls())
                                .productRecommendations(result.getProductRecommendations())
                                .build();
        }
}