package com.example.carwash.validation.validImage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ValidImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private String message;

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.isEmpty()) {
            return false;
        }
        if (Objects.requireNonNull(value.getContentType()).equals("image/jpeg")) {
            return true;
        } else return Objects.requireNonNull(value.getContentType()).equals("image/png");
    }

}
