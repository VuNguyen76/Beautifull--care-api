package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.AuthenticationRequest;
import com.dailycodework.beautifulcare.dto.request.IntrospectRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.AuthenticationResponse;
import com.dailycodework.beautifulcare.dto.response.IntrospectResponse;
import com.dailycodework.beautifulcare.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @deprecated This controller is deprecated and will be removed in a future
 *             release.
 *             Please use {@link UserManagementController} instead with
 *             endpoints /api/v1/users/auth/*
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
@Tag(name = "Authentication (Deprecated)", description = "Deprecated API for authentication. Use /api/v1/users/auth/* instead.")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    @Deprecated
    public APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return new APIResponse<>(true, "Authentication successful", result);
    }

    @PostMapping("/introspect")
    @Deprecated
    public APIResponse<IntrospectResponse> introspectToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        return new APIResponse<>(true, "Token introspection successful", result);
    }
}
