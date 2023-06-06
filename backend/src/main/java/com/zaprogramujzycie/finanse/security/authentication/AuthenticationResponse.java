package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.user.UserDTO;
import org.springframework.http.ResponseCookie;

public record AuthenticationResponse(
        ResponseCookie jwtCookie,

        UserDTO user
) {
}
