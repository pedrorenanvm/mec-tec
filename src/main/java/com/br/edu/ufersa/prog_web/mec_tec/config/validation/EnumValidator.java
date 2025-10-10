package com.br.edu.ufersa.prog_web.mec_tec.config.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface EnumValidator {

    Class<? extends Enum<?>> enumClass();

    String message() default "Invalid Enum";

    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
