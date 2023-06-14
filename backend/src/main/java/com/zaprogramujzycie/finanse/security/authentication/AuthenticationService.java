package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.security.authorization.Role;
import com.zaprogramujzycie.finanse.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public ResponseCookie loginUser(LoginDetailsDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        return jwtService.generateTokenCookie(new UserDetails(request.email(), request.password(), List.of(Role.USER)));

    }

    public void registerUser(RegisterDetailsDTO request) {

        userService.registerUser(request);
    }

}
