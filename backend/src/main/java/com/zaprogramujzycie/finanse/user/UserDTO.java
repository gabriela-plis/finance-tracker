package com.zaprogramujzycie.finanse.user;

import java.util.List;

public record UserDTO (
        String id,

        String username,

        String email,

        List<String> roles
) { }
