package com.example.carwash.service.interfaces;

import com.example.carwash.model.dtos.AppointmentVehicleDTO;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.view.VehicleView;

import java.util.List;

public interface VehicleService {
    void save(Vehicle vehicle);

    Vehicle findById(Long id);

    void delete(Vehicle vehicle);

    List<AppointmentVehicleDTO> getAllVehiclesByUserUsername(String username);

    List<VehicleView> getVehiclesViewByUsernameAndGetVehicleView(String username);
}
