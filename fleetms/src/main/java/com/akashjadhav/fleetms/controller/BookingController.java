
package com.akashjadhav.fleetms.controller;

import com.akashjadhav.fleetms.dto.BookingRequest;
import com.akashjadhav.fleetms.model.Booking;
import com.akashjadhav.fleetms.model.BookingStatus;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestBody BookingRequest request,
            Authentication authentication) {

        User customer = (User) authentication.getPrincipal();

        Booking savedBooking = bookingService.createBooking(request, customer);

        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/my-bookings")
    public ResponseEntity<List<Booking>> getMyBookings(
            Authentication authentication) {

        User customer = (User) authentication.getPrincipal();

        List<Booking> bookings =
                bookingService.getBookingsByCustomer(customer.getId());

        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(
            @PathVariable Long id,
            @RequestParam BookingStatus status) {

        Booking updatedBooking =
                bookingService.updateBookingStatus(id, status);

        return ResponseEntity.ok(updatedBooking);
    }
}
