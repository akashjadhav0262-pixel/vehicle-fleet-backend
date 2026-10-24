package com.akashjadhav.fleetms.dto;

import java.math.BigDecimal;

public class VendorAnalyticsDTO {

    private Long vendorId;
    private long totalVehicles;
    private long totalBookings;
    private BigDecimal totalRevenue;

    public VendorAnalyticsDTO() {}

    public VendorAnalyticsDTO(Long vendorId, long totalVehicles, long totalBookings, BigDecimal totalRevenue) {
        this.vendorId = vendorId;
        this.totalVehicles = totalVehicles;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public long getTotalVehicles() { return totalVehicles; }
    public void setTotalVehicles(long totalVehicles) { this.totalVehicles = totalVehicles; }

    public long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(long totalBookings) { this.totalBookings = totalBookings; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
}