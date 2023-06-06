package com.zaprogramujzycie.finanse.security.authentication;

public record RegisterRequestDTO (
        String username,

        String email,

        String password
) { }
