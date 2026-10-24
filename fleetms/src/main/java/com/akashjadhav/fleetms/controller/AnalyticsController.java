package com.akashjadhav.fleetms.controller;

import com.akashjadhav.fleetms.dto.VendorAnalyticsDTO;
import com.akashjadhav.fleetms.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    // Get dashboard metrics for a specific vendor
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<VendorAnalyticsDTO> getVendorAnalytics(@PathVariable Long vendorId) {
        VendorAnalyticsDTO analytics = analyticsService.getVendorAnalytics(vendorId);
        return ResponseEntity.ok(analytics);
    }
}