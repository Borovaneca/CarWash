package com.example.carwash.service;

import com.example.carwash.model.dtos.AppointmentVehicleDTO;
import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.view.VehicleView;
import com.example.carwash.repository.VehicleRepository;
import com.example.carwash.service.interfaces.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
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
    public List<AppointmentVehicleDTO> getAllVehiclesByUserUsername(String username) {
       return vehicleRepository.findByUserUsername(username).
                map(vehicleList -> vehicleList.stream().map(vehicle -> modelMapper.map(vehicle, AppointmentVehicleDTO.class)).toList()).orElse(null);
    }

    @Override
    public List<VehicleView> getVehiclesViewByUsernameAndGetVehicleView(String username) {
        return vehicleRepository.findByUserUsername(username)
                .map(vehicles -> vehicles.stream().map(vehicle -> modelMapper.map(vehicle, VehicleView.class)).toList()).orElse(null);
    }
}
