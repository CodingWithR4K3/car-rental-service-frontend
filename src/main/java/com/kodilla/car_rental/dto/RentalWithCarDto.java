package com.kodilla.car_rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class RentalWithCarDto {

    private Long id;
    private LocalDate rentedFrom;
    private LocalDate rentedTo;
    private BigDecimal rentalCost;
    private Long carId;
    private String carBrand;
    private String carModel;
    private String userName;
    private String userLastName;
    private String userEmail;
    private int userPhoneNumber;
}
