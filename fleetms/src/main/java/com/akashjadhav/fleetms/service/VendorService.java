package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.VendorProfileRequest;
import com.akashjadhav.fleetms.model.Role;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.model.VendorProfile;
import com.akashjadhav.fleetms.repository.UserRepository;
import com.akashjadhav.fleetms.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    public VendorService(VendorRepository vendorRepository, UserRepository userRepository) {
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
    }

    public VendorProfile createOrUpdateProfile(VendorProfileRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getEmail()));

        if (user.getRole() != Role.VENDOR_ADMIN) {
            throw new RuntimeException("Only users with VENDOR_ADMIN role can create a vendor profile!");
        }

        VendorProfile profile = vendorRepository.findByUserId(user.getId())
                .orElse(new VendorProfile());

        profile.setUser(user);
        profile.setBusinessName(request.getBusinessName());
        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setPincode(request.getPincode());
        profile.setContactPhone(request.getContactPhone());
        profile.setContactEmail(request.getContactEmail());

        return vendorRepository.save(profile);
    }

    public VendorProfile getVendorByEmail(String email) {
        return vendorRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Vendor profile not found for email: " + email));
    }

    public List<VendorProfile> getAllVendors() {
        return vendorRepository.findAll();
    }
}