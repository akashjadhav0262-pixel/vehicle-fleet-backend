
package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.VendorProfileRequest;
import com.akashjadhav.fleetms.model.Role;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.model.VendorProfile;
import com.akashjadhav.fleetms.repository.VendorRepository;
import org.springframework.stereotype.Service;
import com.akashjadhav.fleetms.dto.VendorProfileResponse;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public VendorProfile createOrUpdateProfile(
            VendorProfileRequest request, User user) {

        if (user.getRole() != Role.VENDOR_ADMIN) {
            throw new RuntimeException(
                    "Only VENDOR_ADMIN can create a vendor profile!");
        }

        VendorProfile profile = vendorRepository
                .findByUserId(user.getId())
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


    public VendorProfile getMyProfile(User user) {

        if (user.getRole() != Role.VENDOR_ADMIN) {
            throw new RuntimeException(
                    "Only VENDOR_ADMIN can view a vendor profile!");
        }

        return vendorRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Vendor profile not found!"));
    }


    public VendorProfileResponse convertToResponse(VendorProfile profile) {

        VendorProfileResponse response = new VendorProfileResponse();

        response.setId(profile.getId());
        response.setBusinessName(profile.getBusinessName());
        response.setAddress(profile.getAddress());
        response.setCity(profile.getCity());
        response.setPincode(profile.getPincode());
        response.setContactPhone(profile.getContactPhone());
        response.setContactEmail(profile.getContactEmail());
        response.setVerified(profile.isVerified());

        return response;
    }



    public VendorProfile getVendorByEmail(String email) {
        return vendorRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "Vendor profile not found for email: " + email));
    }

    public List<VendorProfile> getAllVendors() {
        return vendorRepository.findAll();
    }
}
