package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.PaymentCreateRequest;
import com.dailycodework.beautifulcare.dto.request.PaymentUpdateRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.PaymentMethodResponse;
import com.dailycodework.beautifulcare.dto.response.PaymentResponse;
import com.dailycodework.beautifulcare.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing payment-related operations.
 * Provides APIs to create, retrieve, update, and process payments.
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    /**
     * Create a new payment
     * 
     * @param request Payment creation request data
     * @return PaymentResponse with created payment details
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<PaymentResponse> createPayment(@Valid @RequestBody PaymentCreateRequest request) {
        PaymentResponse payment = paymentService.createPayment(request);
        return new APIResponse<>(true, "Payment created successfully", payment);
    }

    /**
     * Get all payments with optional filtering
     * 
     * @param bookingId Optional booking ID filter
     * @return List of payment responses
     */
    @GetMapping
    public APIResponse<List<PaymentResponse>> getAllPayments(
            @RequestParam(required = false) String bookingId) {
        List<PaymentResponse> payments = paymentService.getAllPayments(bookingId);
        return new APIResponse<>(true, "Payments retrieved successfully", payments);
    }

    /**
     * Get payment by ID
     * 
     * @param id Payment ID
     * @return Payment details
     */
    @GetMapping("/{id}")
    public APIResponse<PaymentResponse> getPaymentById(@PathVariable String id) {
        PaymentResponse payment = paymentService.getPaymentById(id);
        return new APIResponse<>(true, "Payment retrieved successfully", payment);
    }

    /**
     * Update a payment
     * 
     * @param id      Payment ID
     * @param request Update data
     * @return Updated payment details
     */
    @PutMapping("/{id}")
    public APIResponse<PaymentResponse> updatePayment(
            @PathVariable String id, @Valid @RequestBody PaymentUpdateRequest request) {
        PaymentResponse updatedPayment = paymentService.updatePayment(id, request);
        return new APIResponse<>(true, "Payment updated successfully", updatedPayment);
    }

    /**
     * Process a payment
     * 
     * @param id Payment ID
     * @return Processed payment details
     */
    @PostMapping("/{id}/process")
    public APIResponse<PaymentResponse> processPayment(@PathVariable String id) {
        PaymentResponse processedPayment = paymentService.processPayment(id);
        return new APIResponse<>(true, "Payment processed successfully", processedPayment);
    }

    /**
     * Get available payment methods
     * 
     * @return List of payment methods
     */
    @GetMapping("/methods")
    public APIResponse<List<PaymentMethodResponse>> getPaymentMethods() {
        List<PaymentMethodResponse> paymentMethods = paymentService.getPaymentMethods();
        return new APIResponse<>(true, "Payment methods retrieved successfully", paymentMethods);
    }
}