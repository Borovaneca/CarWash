package com.example.carwash.validation.validImage;

import com.example.carwash.validation.uniqueUsername.UniqueUsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidImageValidator.class)
public @interface ValidImage {

    String message() default "Image is not selected or is not valid!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
