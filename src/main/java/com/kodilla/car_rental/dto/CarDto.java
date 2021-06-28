package com.kodilla.car_rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String vin;
    private int productionYear;
    private String brand;
    private String model;
    private int mileage;
    private String chassisType;
    private String fuelType;
    private double engineCapacity;
    private BigDecimal costPerDay;
    private Status status;
}
