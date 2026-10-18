package com.akashjadhav.fleetms.dto;

import java.time.LocalDateTime;

public class BookingRequest {

    private Long customerId;
    private Long vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public BookingRequest() {}

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}