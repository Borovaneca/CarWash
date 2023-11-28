package com.example.carwash.service.interfaces;

import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.view.VehicleView;

import java.util.List;

public interface VehicleService {
    void save(Vehicle vehicle);

    Vehicle findById(Long id);

    void delete(Vehicle vehicle);

    List<VehicleView> getAllVehiclesByUserUsername(String username);

    void initVehiclesOfLoggInUser(String username);

}
