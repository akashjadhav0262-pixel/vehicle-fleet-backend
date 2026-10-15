package com.akashjadhav.fleetms.controller;

import com.akashjadhav.fleetms.dto.VendorProfileRequest;
import com.akashjadhav.fleetms.model.VendorProfile;
import com.akashjadhav.fleetms.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/profile")
    public ResponseEntity<VendorProfile> saveVendorProfile(@RequestBody VendorProfileRequest request) {
        return ResponseEntity.ok(vendorService.createOrUpdateProfile(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<VendorProfile> getVendorProfile(@RequestParam String email) {
        return ResponseEntity.ok(vendorService.getVendorByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<VendorProfile>> getAllVendors() {
        return ResponseEntity.ok(vendorService.getAllVendors());
    }
}