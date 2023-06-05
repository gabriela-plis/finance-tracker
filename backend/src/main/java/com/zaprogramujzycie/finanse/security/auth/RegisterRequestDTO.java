package com.zaprogramujzycie.finanse.security.auth;

public record RegisterRequestDTO (
        String username,

        String email,

        String password
) { }
