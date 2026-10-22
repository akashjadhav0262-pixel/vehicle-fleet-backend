package com.akashjadhav.fleetms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One relationship with Booking
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentMethod; // e.g., "CARD", "UPI", "NET_BANKING"

    @Column(unique = true)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    private LocalDateTime paymentTime = LocalDateTime.now();

    public Payment() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getPaymentTime() { return paymentTime; }
    public void setPaymentTime(LocalDateTime paymentTime) { this.paymentTime = paymentTime; }
}