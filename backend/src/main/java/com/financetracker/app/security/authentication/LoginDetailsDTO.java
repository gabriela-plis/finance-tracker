package com.financetracker.app.security.authentication;

import com.financetracker.app.utils.validation.email.ValidEmail;
import com.financetracker.app.utils.validation.password.ValidPassword;

public record LoginDetailsDTO(
        @ValidEmail
        String email,

        @ValidPassword
        String password
) { }
