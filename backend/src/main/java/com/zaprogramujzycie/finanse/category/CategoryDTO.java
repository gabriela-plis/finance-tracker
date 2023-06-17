package com.zaprogramujzycie.finanse.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO (

    @NotBlank
    String id,

    @NotBlank
    String name

) { }
