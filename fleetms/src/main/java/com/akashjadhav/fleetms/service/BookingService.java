
package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.BookingRequest;
import com.akashjadhav.fleetms.model.Booking;
import com.akashjadhav.fleetms.model.BookingStatus;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.repository.BookingRepository;
import com.akashjadhav.fleetms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public Booking createBooking(BookingRequest request, User customer) {

        // 1. Fetch Vehicle
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found with ID: "
                                + request.getVehicleId()));

        // 2. Validate Rental Duration
        Duration duration = Duration.between(
                request.getStartTime(),
                request.getEndTime()
        );

        long hours = duration.toHours();

        if (hours <= 0) {
            throw new IllegalArgumentException(
                    "End time must be after start time."
            );
        }

        // 3. Calculate Rental Cost
        BigDecimal totalPrice;

        if (hours >= 24) {
            long days = hours / 24;
            long remainingHours = hours % 24;

            BigDecimal dailyCost = vehicle.getDailyRate()
                    .multiply(BigDecimal.valueOf(days));

            BigDecimal hourlyCost = vehicle.getHourlyRate()
                    .multiply(BigDecimal.valueOf(remainingHours));

            totalPrice = dailyCost.add(hourlyCost);

        } else {
            totalPrice = vehicle.getHourlyRate()
                    .multiply(BigDecimal.valueOf(hours));
        }

        // 4. Create Booking
        Booking booking = new Booking();

        booking.setCustomer(customer);
        booking.setVehicle(vehicle);
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with ID: "
                                + bookingId));

        booking.setStatus(status);

        return bookingRepository.save(booking);
    }
}
