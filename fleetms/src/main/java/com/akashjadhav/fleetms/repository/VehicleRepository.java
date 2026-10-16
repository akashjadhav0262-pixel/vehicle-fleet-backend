package com.akashjadhav.fleetms.repository;

import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleCategory;
import com.akashjadhav.fleetms.model.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCategory(VehicleCategory category);
    List<Vehicle> findByStatus(VehicleStatus status);
    List<Vehicle> findByVendorProfileId(Long vendorId);
}