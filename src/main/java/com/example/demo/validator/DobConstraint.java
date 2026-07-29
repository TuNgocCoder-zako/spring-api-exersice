package com.example.demo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobConstraint {

    String message() default "Invalid date format";
    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
