package com.kodilla.car_rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {

    private Long id;
    private LocalDate rentedFrom;
    private LocalDate rentedTo;
    private Long carId;
    private Long userId;
}
