
package com.akashjadhav.fleetms.config;

import com.akashjadhav.fleetms.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendError(
                                        HttpServletResponse.SC_UNAUTHORIZED,
                                        "Unauthorized"
                                )
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendError(
                                        HttpServletResponse.SC_FORBIDDEN,
                                        "Forbidden"
                                )
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Public authentication endpoints
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/auth/register",
                                "/api/v1/auth/login",
                                "/api/v1/auth/forgot-password",
                                "/api/v1/auth/reset-password"
                        ).permitAll()

                        // Public vehicle browsing
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/vehicles/**"
                        ).permitAll()

                        // Vendor profile creation
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/vendors/profile"
                        ).hasRole("VENDOR_ADMIN")

                        // Vendor can view their own profile
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/vendors/profile"
                        ).hasRole("VENDOR_ADMIN")

                        // Customer and system admin can create bookings
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/bookings"
                        ).hasAnyRole("CUSTOMER", "SYSTEM_ADMIN")

                        // Customer and system admin can view their bookings
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/bookings/my-bookings"
                        ).hasAnyRole("CUSTOMER", "SYSTEM_ADMIN")

                        // Vendor admin and system admin can view all bookings
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/bookings"
                        ).hasAnyRole("VENDOR_ADMIN", "SYSTEM_ADMIN")

                        // Vendor admin and system admin can update booking status
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/bookings/*/status"
                        ).hasAnyRole("VENDOR_ADMIN", "SYSTEM_ADMIN")

                        // All remaining endpoints require authentication
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
