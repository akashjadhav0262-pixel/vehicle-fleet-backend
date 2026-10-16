package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.VehicleRequest;
import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleCategory;
import com.akashjadhav.fleetms.model.VehicleStatus;
import com.akashjadhav.fleetms.model.VendorProfile;
import com.akashjadhav.fleetms.repository.VehicleRepository;
import com.akashjadhav.fleetms.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VendorRepository vendorRepository;

    public VehicleService(VehicleRepository vehicleRepository, VendorRepository vendorRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vendorRepository = vendorRepository;
    }

    public Vehicle addVehicle(VehicleRequest request) {
        VendorProfile vendor = vendorRepository.findByUserEmail(request.getVendorEmail())
                .orElseThrow(() -> new RuntimeException("Vendor profile not found for email: " + request.getVendorEmail()));

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setCategory(request.getCategory());
        vehicle.setHourlyRate(request.getHourlyRate());
        vehicle.setDailyRate(request.getDailyRate());
        vehicle.setMonthlyRate(request.getMonthlyRate());
        vehicle.setRequiresDriver(request.isRequiresDriver());
        vehicle.setDriverName(request.getDriverName());
        vehicle.setDriverPhone(request.getDriverPhone());
        vehicle.setVendorProfile(vendor);

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByCategory(VehicleCategory category) {
        return vehicleRepository.findByCategory(category);
    }

    public List<Vehicle> getVehiclesByStatus(VehicleStatus status) {
        return vehicleRepository.findByStatus(status);
    }

    public Vehicle updateVehicleStatus(Long id, VehicleStatus status) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
        vehicle.setStatus(status);
        return vehicleRepository.save(vehicle);
    }
}