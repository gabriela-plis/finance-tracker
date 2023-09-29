package com.financetracker.app.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO (

    @NotBlank
    String id,

    @NotBlank
    String name

) { }
