package com.kodilla.car_rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class RentalDto {

    private Long id;
    private LocalDate rentedFrom;
    private LocalDate rentedTo;
    private Long carId;
    private Long userId;
}
