package com.dailycodework.beautifulcare.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // User errors
    USER_EXISTED(HttpStatus.BAD_REQUEST, "User already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USERNAME_OR_PASSWORD_INVALID(HttpStatus.UNAUTHORIZED, "Username or password is invalid"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Email already exists"),

    // Customer errors
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "Customer not found"),

    // Specialist errors
    SPECIALIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Specialist not found"),
    SPECIALIST_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "Specialist is not available at the requested time"),

    // Service errors
    SERVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Service not found"),
    SERVICE_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Service category not found"),

    // Booking errors
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "Booking not found"),
    BOOKING_TIME_INVALID(HttpStatus.BAD_REQUEST, "Booking time is invalid"),
    INVALID_STATE_TRANSITION(HttpStatus.BAD_REQUEST, "Invalid state transition for booking"),

    // Payment errors
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment not found"),
    PAYMENT_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "Payment has already been processed"),

    // Skin test errors
    SKIN_TEST_NOT_FOUND(HttpStatus.NOT_FOUND, "Skin test not found"),
    SKIN_TEST_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "Skin test result not found"),

    // Blog errors
    BLOG_CATEGORY_ALREADY_EXISTS(HttpStatus.CONFLICT, "Blog category already exists"),
    BLOG_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Blog category not found"),
    BLOG_NOT_FOUND(HttpStatus.NOT_FOUND, "Blog not found"),

    // Generic errors
    UNCATEGORIZED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Uncategorized exception");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
