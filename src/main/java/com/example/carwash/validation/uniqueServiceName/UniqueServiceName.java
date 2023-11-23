package com.example.carwash.validation.uniqueServiceName;

import com.example.carwash.validation.uniqueEmail.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueServiceNameValidator.class)
public @interface UniqueServiceName {

    String message() default "Service name already exists";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
