package com.zaprogramujzycie.finanse.security.authentication;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Authentication Management")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @ApiOperation(value = "User Registration")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterDetailsDTO registerDetails) {
        authenticationService.registerUser(registerDetails);
        return ResponseEntity.ok("User registered successfully");
    }

    @ApiOperation(value = "User Login")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginDetailsDTO loginDetails) {
        ResponseCookie jwtCookie = authenticationService.loginUser(loginDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }
}
