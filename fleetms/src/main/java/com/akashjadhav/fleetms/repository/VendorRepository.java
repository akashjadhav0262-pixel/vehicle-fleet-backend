package com.akashjadhav.fleetms.repository;

import com.akashjadhav.fleetms.model.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<VendorProfile, Long> {
    Optional<VendorProfile> findByUserId(Long userId);
    Optional<VendorProfile> findByUserEmail(String email);
}