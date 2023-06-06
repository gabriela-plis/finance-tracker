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

    public AuthenticationResponse loginUser(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDTO user = userService.getUserByEmail(request.email());

        ResponseCookie token = jwtService.generateTokenCookie(new UserDetails(request.email(), request.password(), List.of(Role.USER)));

        return new AuthenticationResponse(token, user);
    }

    public AuthenticationResponse registerUser(RegisterRequestDTO request) {
        //create user, save user, generate token and return (user and token)

        UserDTO registeredUser = userService.registerUser(request);

        ResponseCookie token = jwtService.generateTokenCookie(new UserDetails(request.email(), request.password(), List.of(Role.USER)));

        return new AuthenticationResponse(token, registeredUser);
    }

}
