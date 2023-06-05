package com.zaprogramujzycie.finanse.security;

public record RegisterRequestDTO (
        String username,

        String email,

        String password
) { }
