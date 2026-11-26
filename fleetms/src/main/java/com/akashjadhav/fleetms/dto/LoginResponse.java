
package com.akashjadhav.fleetms.dto;

public class LoginResponse {

    private String token;
    private String tokenType;
    private String email;
    private String name;
    private String role;

    public LoginResponse(
            String token,
            String email,
            String name,
            String role) {

        this.token = token;
        this.tokenType = "Bearer";
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
