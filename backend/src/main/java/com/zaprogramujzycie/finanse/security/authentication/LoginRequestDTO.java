package com.zaprogramujzycie.finanse.security.authentication;

public record LoginRequestDTO(
        //validation
        String email,

        String password
) { }
