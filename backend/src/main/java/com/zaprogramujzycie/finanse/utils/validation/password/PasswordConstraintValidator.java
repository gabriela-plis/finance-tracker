package com.zaprogramujzycie.finanse.utils.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }

        PasswordValidator validator = new PasswordValidator(List.of(
           new LengthRule(8,24),
           new CharacterRule(EnglishCharacterData.UpperCase, 1),
           new CharacterRule(EnglishCharacterData.LowerCase, 1),
           new CharacterRule(EnglishCharacterData.Digit, 1),
           new CharacterRule(EnglishCharacterData.Special, 1),
           new WhitespaceRule()
        ));

         RuleResult resultOfValidation = validator.validate(new PasswordData(password));

         return resultOfValidation.isValid();
    }
}
