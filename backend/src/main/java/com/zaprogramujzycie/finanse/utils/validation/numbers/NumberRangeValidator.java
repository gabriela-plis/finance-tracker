package com.zaprogramujzycie.finanse.utils.validation.numbers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;

public class NumberRangeValidator implements ConstraintValidator<ValidNumberRange, Object> {

    private String startNumberField;
    private String endNumberField;

    @Override
    public void initialize(ValidNumberRange constraintAnnotation) {
        this.startNumberField = constraintAnnotation.startNumberField();
        this.endNumberField = constraintAnnotation.endNumberField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BigDecimal startNumber = (BigDecimal) new BeanWrapperImpl(value).getPropertyValue(startNumberField);
        BigDecimal endNumber = (BigDecimal) new BeanWrapperImpl(value).getPropertyValue(endNumberField);

        if (startNumber == null || endNumber == null) {
            return true;
        }

        return startNumber.compareTo(endNumber) <= 0;
    }
}
