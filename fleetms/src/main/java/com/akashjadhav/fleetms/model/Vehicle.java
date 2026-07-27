package com.akashjadhav.fleetms.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    @Column(nullable = false)
    private BigDecimal dailyRate;

    public Vehicle() {
    }

    public Vehicle(Long id, String licensePlate, String model, VehicleStatus status, BigDecimal dailyRate) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.status = status;
        this.dailyRate = dailyRate;
    }

    public Vehicle(String licensePlate, String model, VehicleStatus status, BigDecimal dailyRate) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.status = status;
        this.dailyRate = dailyRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }
}