package com.example.carwash.validation.validService;

import com.example.carwash.repository.ServiceRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidServiceValidator implements ConstraintValidator<ValidService, String> {

    private final ServiceRepository serviceRepository;

    public ValidServiceValidator(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return serviceRepository.findAll().stream().anyMatch(service -> service.getName().equals(value));
    }
}
