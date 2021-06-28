package com.kodilla.car_rental.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private int phoneNumber;
    private LocalDate creationDate;
}
