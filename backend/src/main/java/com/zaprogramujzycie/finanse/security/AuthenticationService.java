package com.zaprogramujzycie.finanse.security;

import com.zaprogramujzycie.finanse.user.UserDTO;
import com.zaprogramujzycie.finanse.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public UserDTO loginUser() {
        return null;
    }

    public UserDTO registerUser() {
        return null;
    }

}
