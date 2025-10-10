package com.br.edu.ufersa.prog_web.mec_tec.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, CharSequence> {
    private Set<String> acceptedValues;

    @Override
    public void initialize(EnumValidator annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString());
    }
}
