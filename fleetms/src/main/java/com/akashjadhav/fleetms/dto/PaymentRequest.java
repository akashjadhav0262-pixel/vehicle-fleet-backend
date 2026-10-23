package com.akashjadhav.fleetms.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private Long bookingId;
    private BigDecimal amount;
    private String paymentMethod; // e.g., "UPI", "CARD", "NET_BANKING"

    public PaymentRequest() {}

    // Getters and Setters
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}