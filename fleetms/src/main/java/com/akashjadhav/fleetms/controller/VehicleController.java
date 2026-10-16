package com.akashjadhav.fleetms.controller;

import com.akashjadhav.fleetms.dto.VehicleRequest;
import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleCategory;
import com.akashjadhav.fleetms.model.VehicleStatus;
import com.akashjadhav.fleetms.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody VehicleRequest request) {
        return new ResponseEntity<>(vehicleService.addVehicle(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Vehicle>> getVehiclesByCategory(@PathVariable VehicleCategory category) {
        return ResponseEntity.ok(vehicleService.getVehiclesByCategory(category));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Vehicle>> getVehiclesByStatus(@PathVariable VehicleStatus status) {
        return ResponseEntity.ok(vehicleService.getVehiclesByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Vehicle> updateStatus(@PathVariable Long id, @RequestParam VehicleStatus status) {
        return ResponseEntity.ok(vehicleService.updateVehicleStatus(id, status));
    }
}