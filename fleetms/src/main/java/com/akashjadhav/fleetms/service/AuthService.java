package com.akashjadhav.fleetms.service;

import com.akashjadhav.fleetms.dto.ChangePasswordRequest;
import com.akashjadhav.fleetms.dto.LoginRequest;
import com.akashjadhav.fleetms.dto.RegisterRequest;
import com.akashjadhav.fleetms.model.User;
import com.akashjadhav.fleetms.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                request.getRole()
        );
        userRepository.save(user);
        return "User registered successfully as " + request.getRole();
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password!");
        }

        return "Login successful! Welcome back, " + user.getName() + " [" + user.getRole() + "]";
    }

    public String changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect old password!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully!";
    }

    public String generateResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        // In real-world, you'd send this via email. For Postman testing, we return it.
        return "Password reset token generated: " + token;
    }

    public String resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token!"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear token after use
        userRepository.save(user);

        return "Password has been reset successfully!";
    }
}