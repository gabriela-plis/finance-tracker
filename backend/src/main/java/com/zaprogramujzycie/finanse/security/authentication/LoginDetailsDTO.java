package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.utils.validation.email.ValidEmail;
import com.zaprogramujzycie.finanse.utils.validation.password.ValidPassword;

public record LoginDetailsDTO(
        @ValidEmail  //TODO check correctness
        String email,

        @ValidPassword
        String password
) { }
