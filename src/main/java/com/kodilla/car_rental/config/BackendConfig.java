package com.kodilla.car_rental.config;

import org.springframework.beans.factory.annotation.Value;

public class BackendConfig {

    @Value("${car.api.endpoint}")
    private String carEndpoint;

    @Value("${rental.api.endpoint}")
    private String rentalEndpoint;

    @Value("${user.api.endpoint}")
    private String userEndpoint;

    @Value("${vin.api.endpoint}")
    private String vinApiEndpoint;
}
