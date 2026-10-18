package com.akashjadhav.fleetms.repository;

import com.akashjadhav.fleetms.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings made by a specific customer ID
    List<Booking> findByCustomerId(Long customerId);

    // Find all bookings associated with a specific vehicle ID
    List<Booking> findByVehicleId(Long vehicleId);
}