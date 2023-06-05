package com.zaprogramujzycie.finanse.security.auth;

import com.zaprogramujzycie.finanse.user.UserDTO;

public record AuthenticationResponseDTO(
        String jwt,

        UserDTO user
) {
}
