package com.zaprogramujzycie.finanse.utils.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;


public class DateIntervalValidator implements ConstraintValidator<ValidDateInterval, Object> {

    private String startDateField;

    private String endDateField;

    @Override
    public void initialize(ValidDateInterval constraintAnnotation) {
        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        LocalDate startDate = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(startDateField);
        LocalDate endDate = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(endDateField);

        if (startDate == null || endDate == null) {
            return true;
        }

        return startDate.isBefore(endDate);
    }

}
