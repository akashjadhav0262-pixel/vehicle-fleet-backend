package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleStatus;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleService {
    Vehicle addVehicle(Vehicle vehicle);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesByStatus(VehicleStatus status);
    Vehicle updateVehicleStatus(Long id, VehicleStatus status);
    BigDecimal calculateRentalCost(Long id, int days);
}