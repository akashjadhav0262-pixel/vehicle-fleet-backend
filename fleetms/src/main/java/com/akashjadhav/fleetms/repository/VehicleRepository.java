package com.akashjadhav.fleetms.repository;

import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.model.VehicleCategory;
import com.akashjadhav.fleetms.model.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByCategory(VehicleCategory category);
    List<Vehicle> findByStatus(VehicleStatus status);
    @Query("SELECT v FROM Vehicle v WHERE v.vendorProfile.id = :vendorId")
    List<Vehicle> findByVendorId(@Param("vendorId") Long vendorId);
}