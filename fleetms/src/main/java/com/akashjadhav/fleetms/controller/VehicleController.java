package com.akashjadhav.fleetms.controller;

import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleStatus;
import com.akashjadhav.fleetms.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        Vehicle createdVehicle = vehicleService.addVehicle(vehicle);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vehicle>> getByStatus(@PathVariable VehicleStatus status) {
        return ResponseEntity.ok(vehicleService.getVehiclesByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Vehicle> updateStatus(@PathVariable Long id, @RequestParam VehicleStatus status) {
        return ResponseEntity.ok(vehicleService.updateVehicleStatus(id, status));
    }

    @GetMapping("/{id}/cost")
    public ResponseEntity<BigDecimal> calculateCost(@PathVariable Long id, @RequestParam int days) {
        return ResponseEntity.ok(vehicleService.calculateRentalCost(id, days));
    }
}