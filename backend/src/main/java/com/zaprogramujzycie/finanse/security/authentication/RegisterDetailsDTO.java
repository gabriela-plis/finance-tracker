package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.utils.validation.ValidPassword;
import jakarta.validation.constraints.*;

public record RegisterDetailsDTO(

        @Size(min = 3, max = 30)
        @NotBlank
        String username,

        @Email(
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            flags = Pattern.Flag.CASE_INSENSITIVE
        )
        @NotNull
        String email,

        @ValidPassword
        @NotNull
        String password
) { }
