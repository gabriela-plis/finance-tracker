package com.zaprogramujzycie.finanse.security;

public record AuthenticationRequestDTO (

        String email,

        String password
) { }
