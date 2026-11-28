
package com.akashjadhav.fleetms.controller;

import com.akashjadhav.fleetms.dto.VendorProfileRequest;
import com.akashjadhav.fleetms.dto.VendorProfileResponse;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.model.VendorProfile;
import com.akashjadhav.fleetms.service.VendorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/profile")
    public ResponseEntity<VendorProfileResponse> saveVendorProfile(
            @RequestBody VendorProfileRequest request,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        VendorProfile profile =
                vendorService.createOrUpdateProfile(request, currentUser);

        return ResponseEntity.ok(
                vendorService.convertToResponse(profile));
    }

    @GetMapping("/profile")
    public ResponseEntity<VendorProfileResponse> getMyProfile(
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();

        VendorProfile profile =
                vendorService.getMyProfile(currentUser);

        return ResponseEntity.ok(
                vendorService.convertToResponse(profile));
    }
}
