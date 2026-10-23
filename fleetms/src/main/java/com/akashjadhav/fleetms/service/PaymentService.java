package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.PaymentRequest;
import com.akashjadhav.fleetms.model.Booking;
import com.akashjadhav.fleetms.model.BookingStatus;
import com.akashjadhav.fleetms.model.Payment;
import com.akashjadhav.fleetms.model.PaymentStatus;
import com.akashjadhav.fleetms.repository.BookingRepository;
import com.akashjadhav.fleetms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Payment processPayment(PaymentRequest request) {
        // 1. Fetch Booking
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + request.getBookingId()));

        // 2. Validate Payment Amount matches Booking total price
        if (request.getAmount().compareTo(booking.getTotalPrice()) != 0) {
            throw new IllegalArgumentException("Payment amount does not match booking total price.");
        }

        // 3. Create and Save Payment Record (Simulating Gateway Success)
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().toUpperCase().substring(0, 8));
        payment.setStatus(PaymentStatus.SUCCESS);

        Payment savedPayment = paymentRepository.save(payment);

        // 4. Automatically confirm the booking upon successful payment
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return savedPayment;
    }

    public Payment getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found for booking ID: " + bookingId));
    }
}