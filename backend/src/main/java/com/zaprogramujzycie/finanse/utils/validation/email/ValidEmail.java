package com.zaprogramujzycie.finanse.utils.validation.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The email must meet the following criteria:
 * - Not null
 * - Contains the "@" symbol
 * - The local part can contain periods (.) except as the first, last character or consecutive
 * - The domain part must not start and end with a hyphen (-)
 * - The domain part must contain at least one period (.) to separate parts
 * - The top level domain part must consist of two to six alphabetic characters
 */
@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "Invalid Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
