package com.example.carwash.service.proxy;

import com.example.carwash.model.entity.Vehicle;
import com.example.carwash.model.view.VehicleView;
import com.example.carwash.service.VehicleServiceImpl;
import com.example.carwash.service.interfaces.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("vehicleServiceProxy")
public class VehicleServiceProxy implements VehicleService {

    private final VehicleServiceImpl vehicleServiceImpl;

    private List<VehicleView> vehicleViews;

    @Autowired
    public VehicleServiceProxy(VehicleServiceImpl vehicleServiceImpl) {
        this.vehicleServiceImpl = vehicleServiceImpl;
    }


    @Override
    public void save(Vehicle vehicle) {
        vehicleServiceImpl.save(vehicle);

        vehicleViews = null;

        Thread thread = new Thread(() -> {
            vehicleViews = vehicleServiceImpl.getAllVehiclesByUserUsername(vehicle.getUser().getUsername());
        });
        thread.start();
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleServiceImpl.findById(id);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicleServiceImpl.delete(vehicle);

        vehicleViews = null;
        Thread thread = new Thread(() -> {
            vehicleViews = vehicleServiceImpl.getAllVehiclesByUserUsername(vehicle.getUser().getUsername());
        });
        thread.start();
    }

    @Override
    public List<VehicleView> getAllVehiclesByUserUsername(String username) {
        if (vehicleViews == null) {
            vehicleViews = vehicleServiceImpl.getAllVehiclesByUserUsername(username);
        }
        return vehicleViews;
    }
}
