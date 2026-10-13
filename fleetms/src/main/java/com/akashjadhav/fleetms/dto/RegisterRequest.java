package com.akashjadhav.fleetms.dto;

import com.akashjadhav.fleetms.model.Role;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role; // CUSTOMER or VENDOR_ADMIN

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}