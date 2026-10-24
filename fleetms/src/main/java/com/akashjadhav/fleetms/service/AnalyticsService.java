package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.VendorAnalyticsDTO;
import com.akashjadhav.fleetms.model.Payment;
import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.repository.BookingRepository;
import com.akashjadhav.fleetms.repository.PaymentRepository;
import com.akashjadhav.fleetms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AnalyticsService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public VendorAnalyticsDTO getVendorAnalytics(Long vendorId) {
        // 1. Get all vehicles belonging to this vendor
        List<Vehicle> vendorVehicles = vehicleRepository.findByVendorId(vendorId);
        long totalVehicles = vendorVehicles.size();

        long totalBookings = 0;
        BigDecimal totalRevenue = BigDecimal.ZERO;

        // 2. Iterate through vehicles to aggregate bookings and revenue
        for (Vehicle vehicle : vendorVehicles) {
            var vehicleBookings = bookingRepository.findByVehicleId(vehicle.getId());
            totalBookings += vehicleBookings.size();

            for (var booking : vehicleBookings) {
                // Check if payment exists and is SUCCESS
                var paymentOpt = paymentRepository.findByBookingId(booking.getId());
                if (paymentOpt.isPresent() && paymentOpt.get().getStatus().name().equals("SUCCESS")) {
                    totalRevenue = totalRevenue.add(paymentOpt.get().getAmount());
                }
            }
        }

        return new VendorAnalyticsDTO(vendorId, totalVehicles, totalBookings, totalRevenue);
    }
}