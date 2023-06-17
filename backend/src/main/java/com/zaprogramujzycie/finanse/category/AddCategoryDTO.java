package com.zaprogramujzycie.finanse.category;

import jakarta.validation.constraints.NotBlank;

public record AddCategoryDTO(
    @NotBlank
    String name
) { }
