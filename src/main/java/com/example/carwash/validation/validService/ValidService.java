package com.example.carwash.validation.validService;

import com.example.carwash.validation.uniqueUsername.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidServiceValidator.class)
public @interface ValidService {

    String message() default "Service don't exist!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
