package com.example.carwash.validation.uniqueServiceName;

import com.example.carwash.model.entity.Service;
import com.example.carwash.repository.ServiceRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueServiceNameValidator implements ConstraintValidator<UniqueServiceName, String> {

    private final ServiceRepository serviceRepository;

    public UniqueServiceNameValidator(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return serviceRepository.findAll().stream().map(Service::getName).noneMatch(name -> name.equals(value));
    }
}
