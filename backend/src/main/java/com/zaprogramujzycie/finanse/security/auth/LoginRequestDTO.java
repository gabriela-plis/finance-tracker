package com.zaprogramujzycie.finanse.security.auth;

public record LoginRequestDTO(
        //validation
        String email,

        String password
) { }
