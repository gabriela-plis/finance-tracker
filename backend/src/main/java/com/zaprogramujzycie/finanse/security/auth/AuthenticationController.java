package com.zaprogramujzycie.finanse.security.auth;

import com.zaprogramujzycie.finanse.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserDTO loginUser(@RequestBody LoginRequestDTO request) {
        return authenticationService.loginUser(request);
    }

    @PostMapping("/logout")
    public void logoutUser() {
        authenticationService.logoutUser();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> registerUser(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok()
                .body(authenticationService.registerUser(request));
    }

    //logout?
    //refresh token?
}
