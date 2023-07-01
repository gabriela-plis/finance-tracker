package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.utils.validation.email.ValidEmail;
import com.zaprogramujzycie.finanse.utils.validation.password.ValidPassword;
import jakarta.validation.constraints.*;

public record RegisterDetailsDTO(
        @Size(min = 3, max = 30)
        @NotBlank
        String username,

        @ValidEmail
        @NotNull
        String email,

        @ValidPassword
        @NotNull
        String password
) { }
