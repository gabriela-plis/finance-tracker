package com.zaprogramujzycie.finanse.utils.validation.numbers;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumberRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumberRange {
    String message() default "Number range isn't correct!";

    String startNumberField();

    String endNumberField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
