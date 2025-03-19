package com.dailycodework.beautifulcare.controller;

import com.dailycodework.beautifulcare.dto.request.AuthenticationRequest;
import com.dailycodework.beautifulcare.dto.request.IntrospectRequest;
import com.dailycodework.beautifulcare.dto.response.APIResponse;
import com.dailycodework.beautifulcare.dto.response.AuthenticationResponse;
import com.dailycodework.beautifulcare.dto.response.IntrospectResponse;
import com.dailycodework.beautifulcare.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        return new APIResponse<>(true, "Authentication successful", result);
    }

    @PostMapping("/introspect")
    public APIResponse<IntrospectResponse> introspectToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        return new APIResponse<>(true, "Token introspection successful", result);
    }
}
