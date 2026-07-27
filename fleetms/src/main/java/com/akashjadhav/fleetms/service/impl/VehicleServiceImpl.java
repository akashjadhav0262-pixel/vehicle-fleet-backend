package com.akashjadhav.fleetms.service.impl;

import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleStatus;
import com.akashjadhav.fleetms.repository.VehicleRepository;
import com.akashjadhav.fleetms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> getVehiclesByStatus(VehicleStatus status) {
        return vehicleRepository.findByStatus(status);
    }

    @Override
    public Vehicle updateVehicleStatus(Long id, VehicleStatus status) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
        vehicle.setStatus(status);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public BigDecimal calculateRentalCost(Long id, int days) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
        return vehicle.getDailyRate().multiply(BigDecimal.valueOf(days));
    }
}