package com.financetracker.app.category;

import jakarta.validation.constraints.NotBlank;

public record AddCategoryDTO(
    @NotBlank
    String name
) { }
