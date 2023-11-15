package com.example.carwash.service;

import com.example.carwash.model.dtos.AppointmentVehicleDTO;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleService(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
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

    public List<AppointmentVehicleDTO> getAllVehiclesByUserUsername(String username) {
        Optional<List<Vehicle>> vehicles = vehicleRepository.findByUserUsername(username);
        return vehicles.map(vehicleList -> vehicleList.stream().map(vehicle -> modelMapper.map(vehicle, AppointmentVehicleDTO.class)).toList()).orElse(null);
    }
}
