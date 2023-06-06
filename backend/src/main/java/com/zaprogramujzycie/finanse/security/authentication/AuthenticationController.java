package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginRequestDTO request) {
        AuthenticationResponse authenticationResponse = authenticationService.loginUser(request);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, authenticationResponse.jwtCookie().toString())
            .body(authenticationResponse.user());

    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody RegisterRequestDTO request) {
        AuthenticationResponse authenticationResponse = authenticationService.registerUser(request);

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, authenticationResponse.jwtCookie().toString())
            .body(authenticationResponse.user());

    }

    //logout?
    //refresh token?
}
