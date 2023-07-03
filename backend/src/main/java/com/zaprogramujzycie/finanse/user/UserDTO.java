package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.utils.validation.email.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserDTO (
        @NotBlank
        String id,

        @Size(min = 3, max = 30)
        @NotBlank
        String username,

        @ValidEmail
        String email,

        List<String> roles
) { }
