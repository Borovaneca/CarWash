package com.example.carwash.service;

import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.repository.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }


    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public void delete(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }
}
