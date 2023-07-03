package com.zaprogramujzycie.finanse.utils.validation.date;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateIntervalValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateInterval {
    String message() default "Date interval isn't correct!";

    String startDateField();

    String endDateField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
