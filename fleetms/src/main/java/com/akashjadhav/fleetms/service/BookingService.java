
package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.BookingRequest;
import com.akashjadhav.fleetms.model.Booking;
import com.akashjadhav.fleetms.model.BookingStatus;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.model.Vehicle;
import com.akashjadhav.fleetms.repository.BookingRepository;
import com.akashjadhav.fleetms.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found with ID: "
                                + request.getVehicleId()));

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

        User currentUser = getCurrentUser();

        if (hasRole("SYSTEM_ADMIN")) {
            return bookingRepository.findAll();
        }

        if (hasRole("VENDOR_ADMIN")) {
            return bookingRepository.findAll().stream()
                    .filter(booking ->
                            booking.getVehicle()
                                    .getVendorProfile()
                                    .getUser()
                                    .getId()
                                    .equals(currentUser.getId()))
                    .toList();
        }

        throw new AccessDeniedException(
                "You are not authorized to view all bookings."
        );
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking updateBookingStatus(
            Long bookingId,
            BookingStatus status) {

        User currentUser = getCurrentUser();

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with ID: "
                                + bookingId));

        if (hasRole("VENDOR_ADMIN")) {

            Long vehicleOwnerId = booking.getVehicle()
                    .getVendorProfile()
                    .getUser()
                    .getId();

            if (!vehicleOwnerId.equals(currentUser.getId())) {
                throw new AccessDeniedException(
                        "You cannot update another vendor's booking."
                );
            }

        } else if (!hasRole("SYSTEM_ADMIN")) {

            throw new AccessDeniedException(
                    "You are not authorized to update booking status."
            );
        }

        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !(authentication.getPrincipal() instanceof User)) {
            throw new AccessDeniedException("User is not authenticated.");
        }

        return (User) authentication.getPrincipal();
    }

    private boolean hasRole(String role) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
                && authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_" + role));
    }
}
