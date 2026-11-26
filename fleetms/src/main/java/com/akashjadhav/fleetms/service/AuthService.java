
package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.ChangePasswordRequest;
import com.akashjadhav.fleetms.dto.LoginRequest;
import com.akashjadhav.fleetms.dto.LoginResponse;
import com.akashjadhav.fleetms.dto.RegisterRequest;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.repository.UserRepository;
import com.akashjadhav.fleetms.security.JwtService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.akashjadhav.fleetms.model.Role;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered!");
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(),
                Role.CUSTOMER
        );

        userRepository.save(user);

        return "User registered successfully as CUSTOMER";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password!"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password!");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                user.getEmail(),
                user.getName(),
                user.getRole().name()
        );
    }

    public String changePassword(ChangePasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("Incorrect old password!");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return "Password changed successfully!";
    }

    public String generateResetToken(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);
        userRepository.save(user);

        return "Password reset token generated: " + token;
    }

    public String resetPassword(String token, String newPassword) {

        User user = userRepository.findByResetToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid or expired reset token!"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);

        userRepository.save(user);

        return "Password has been reset successfully!";
    }
}
