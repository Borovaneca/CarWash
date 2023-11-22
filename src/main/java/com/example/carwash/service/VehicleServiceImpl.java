package com.example.carwash.service;

import com.example.carwash.mapper.CustomMapper;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.view.VehicleView;
import com.example.carwash.repository.VehicleRepository;
import com.example.carwash.service.interfaces.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomMapper customMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, CustomMapper customMapper) {
        this.vehicleRepository = vehicleRepository;
        this.customMapper = customMapper;
    }


    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

    @Override
    public List<VehicleView> getAllVehiclesByUserUsername(String username) {
       return vehicleRepository.findByUserUsername(username).
                map(vehicleList -> vehicleList.stream().map(customMapper::vehicleToVehicleView).toList()).orElse(null);
    }

}
