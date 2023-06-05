package com.zaprogramujzycie.finanse.security;

import com.zaprogramujzycie.finanse.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserDTO loginUser() {
        return null;
    }

    @PostMapping("/register")
    public UserDTO registerUser() {
        return null;
    }

    //logout?
    //refresh token?
}
