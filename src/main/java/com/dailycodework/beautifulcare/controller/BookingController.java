package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.BookingCreateRequest;
import com.dailycodework.beautifulcare.dto.request.BookingUpdateRequest;
import com.dailycodework.beautifulcare.dto.request.SpecialistAssignmentRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.BookingResponse;
import com.dailycodework.beautifulcare.entity.BookingStatus;
import com.dailycodework.beautifulcare.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing booking-related operations.
 * Provides APIs to create, retrieve, update and delete bookings,
 * as well as specialized operations like check-in, check-out, and specialist
 * assignment.
 */
@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    /**
     * Create a new booking
     * 
     * @param request Booking creation request data
     * @return BookingResponse with created booking details
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse<BookingResponse> createBooking(@Valid @RequestBody BookingCreateRequest request) {
        BookingResponse booking = bookingService.createBooking(request);
        return new APIResponse<>(true, "Booking created successfully", booking);
    }

    /**
     * Get all bookings with optional filtering
     * 
     * @param status Optional status filter
     * @return List of booking responses
     */
    @GetMapping
    public APIResponse<List<BookingResponse>> getAllBookings(
            @RequestParam(required = false) BookingStatus status) {
        List<BookingResponse> bookings = bookingService.getAllBookings(status);
        return new APIResponse<>(true, "Bookings retrieved successfully", bookings);
    }

    /**
     * Get booking by ID
     * 
     * @param id Booking ID
     * @return Booking details
     */
    @GetMapping("/{id}")
    public APIResponse<BookingResponse> getBookingById(@PathVariable String id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return new APIResponse<>(true, "Booking retrieved successfully", booking);
    }

    /**
     * Update a booking
     * 
     * @param id      Booking ID
     * @param request Update data
     * @return Updated booking details
     */
    @PutMapping("/{id}")
    public APIResponse<BookingResponse> updateBooking(
            @PathVariable String id, @Valid @RequestBody BookingUpdateRequest request) {
        BookingResponse updatedBooking = bookingService.updateBooking(id, request);
        return new APIResponse<>(true, "Booking updated successfully", updatedBooking);
    }

    /**
     * Delete/cancel a booking
     * 
     * @param id Booking ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public APIResponse<Void> deleteBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return new APIResponse<>(true, "Booking cancelled successfully", null);
    }

    /**
     * Update booking status
     * 
     * @param id     Booking ID
     * @param status New status
     * @return Updated booking details
     */
    @PutMapping("/{id}/status")
    public APIResponse<BookingResponse> updateBookingStatus(
            @PathVariable String id, @RequestParam BookingStatus status) {
        BookingResponse updatedBooking = bookingService.updateBookingStatus(id, status);
        return new APIResponse<>(true, "Booking status updated successfully", updatedBooking);
    }

    /**
     * Check-in a customer for their booking
     * 
     * @param id Booking ID
     * @return Updated booking details
     */
    @PutMapping("/{id}/checkin")
    public APIResponse<BookingResponse> checkinCustomer(@PathVariable String id) {
        BookingResponse updatedBooking = bookingService.checkinCustomer(id);
        return new APIResponse<>(true, "Customer checked in successfully", updatedBooking);
    }

    /**
     * Assign a specialist to a booking
     * 
     * @param id      Booking ID
     * @param request Specialist assignment details
     * @return Updated booking details
     */
    @PutMapping("/{id}/assign")
    public APIResponse<BookingResponse> assignSpecialist(
            @PathVariable String id, @Valid @RequestBody SpecialistAssignmentRequest request) {
        BookingResponse updatedBooking = bookingService.assignSpecialist(id, request);
        return new APIResponse<>(true, "Specialist assigned successfully", updatedBooking);
    }

    /**
     * Check-out a customer after their booking is complete
     * 
     * @param id Booking ID
     * @return Updated booking details
     */
    @PutMapping("/{id}/checkout")
    public APIResponse<BookingResponse> checkoutCustomer(@PathVariable String id) {
        BookingResponse updatedBooking = bookingService.checkoutCustomer(id);
        return new APIResponse<>(true, "Customer checked out successfully", updatedBooking);
    }
}